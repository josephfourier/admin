package com.thinkjoy.enrollment.admin.controller.upload;

import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.ExcelUtil;
import com.thinkjoy.common.util.FileUploadUtil;
import com.thinkjoy.common.util.FileUtil;
import com.thinkjoy.common.util.RandomCodeUtil;
import com.thinkjoy.common.util.xmlutil.JAXBUtil;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.enrollment.admin.controller.upload.service.UploadService;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolExample;
import com.thinkjoy.ucenter.rpc.api.UcenterSchoolService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xufei
 * @date 2017/11/28
 */
@Controller
@RequestMapping("/upload")
@Api(value = "上传管理", description = "上传管理")
public class UploadController extends BaseController {
    private static Logger _log = LoggerFactory.getLogger(UploadController.class);
    private static final String template = "/template/";

    @Autowired
    private UploadService uploadService;
    @Autowired
    private UcenterSchoolService ucenterSchoolService;

    @RequestMapping(value = "refreshImportDatas", method = RequestMethod.POST)
    @ResponseBody
    public Object refreshImportDatas(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> impportMessage = new HashMap();
        impportMessage.put("importTotal", request.getSession().getAttribute("importTotal") != null ? request.getSession().getAttribute("importTotal") : 0);//导入数据总数
        impportMessage.put("importCount", request.getSession().getAttribute("importCount") != null ? request.getSession().getAttribute("importCount") : 0);//已经处理行数
        impportMessage.put("importErrorCount", request.getSession().getAttribute("importErrorCount") != null ? request.getSession().getAttribute("importErrorCount") : 0);//错误个数
        impportMessage.put("importSuccessCount", request.getSession().getAttribute("importSuccessCount") != null ? request.getSession().getAttribute("importSuccessCount") : 0);//导入成功条数
        impportMessage.put("isFinished", request.getSession().getAttribute("isFinished") != null ? request.getSession().getAttribute("isFinished") : "-1");//是否完成,-1是为空
        return impportMessage;
    }

    @RequestMapping(value = "resetImportDatas", method = RequestMethod.POST)
    @ResponseBody
    public Object resetImportDatas(HttpServletRequest request, HttpServletResponse response) {
        //上传结束后，进度重置
        request.getSession().setAttribute("importTotal", 0);//导入数据总数
        request.getSession().setAttribute("importCount", 0);//导入数据总数
        request.getSession().setAttribute("importErrorCount", 0);//错误个数
        request.getSession().setAttribute("importSuccessCount", 0);//导入成功条数
        request.getSession().setAttribute("isFinished", null);//是否导入完成 -1是为空

        return null;
    }

    /**
     * 基础信息导入
     *
     * @param request
     * @param response
     * @param schoolCode
     * @param baseModel
     * @return
     */
    @RequestMapping(value = "uploadDatas", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadDatas(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(required = true, defaultValue = "", value = "schoolCode") String schoolCode,
                              @RequestParam(required = true, defaultValue = "", value = "baseModel") String baseModel) {
        //验证类
        Table tableValid = null;
        if (schoolCode == null) {
            return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "学校编码为空！", "");
        }
        if (baseModel != null) {
            String filePath = templateXml(request, baseModel);
            tableValid = JAXBUtil.formXML(Table.class, filePath);
            if (tableValid == null) {
                return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "数据校验模板不存在！", "");
            }
            if (tableValid.getColNum() == null) {
                return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "数据校验模板没有配置列数！", "");
            }
        } else {
            return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "数据校验模板名称为空！", "");
        }
        //初始化学校
        UcenterSchoolExample ucenterSchoolExample = new UcenterSchoolExample();
        ucenterSchoolExample.createCriteria().andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);

        UcenterSchool ucenterSchool = ucenterSchoolService.selectFirstByExample(ucenterSchoolExample);
        if (null == ucenterSchool) {
            return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "没有该学校或被锁定！", "");
        }

        //获取表头
        List<Map<Integer, String>> excelHead = getExcelHead(request);
        if (excelHead == null || tableValid.getColNum() != excelHead.get(0).size()) {
            return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "导入模板错误！", "");
        }

        List<Map<Integer, String>> uploadDatas = null;
        Map<String, String> errorMap = null;
        try {
            uploadDatas = getData(request, tableValid.getColNum());//FIXME excel中没有数据的地方在这里也会以空字符串存在
            if (tableValid.getMaxRows() != null && uploadDatas.size() > tableValid.getMaxRows()) {
                return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "超过系统设置最大导入行数:" + tableValid.getMaxRows(), "");
            }
            if (uploadDatas != null && uploadDatas.size() > 0) {
                //调用对应的方法
                if (BaseConstants.ImportModel.BASEMODEL_ENROLL_STUDENT.equals(baseModel)) {
                    errorMap = uploadService.importEnrollStudents(uploadDatas, ucenterSchool, request, tableValid);
                } else {
                    return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "数据校验模板名称配置错误！", "");
                }

            } else {
                return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "导入数据为空!", "");
            }
            if (errorMap != null && errorMap.size() > 0) {
                //导入成功后,将导入的文件存到文件服务器中
                String access_url = addErrorMessageToExcel(request, response, errorMap, tableValid.getColNum());
                return new UcenterResult(UcenterResultConstant.IMPORT_ERROR, access_url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new UcenterResult(UcenterResultConstant.IMPORT_EXCEPTION, "数据导入异常!", "");
        }
        return new UcenterResult(UcenterResultConstant.IMPORT_SUCCESS, uploadDatas.size());
    }

    /**
     * 将错误信息添加到excel对应的单元格批注中
     *
     * @param request
     * @param response
     * @param errorMap
     */
    public String addErrorMessageToExcel(HttpServletRequest request, HttpServletResponse response, Map<String, String> errorMap, int cellNum) {
        MultipartFile uploadFile = getUploadFile(request);

        if (uploadFile == null) {
            return null;
        }

        //生成批次号
        String s = RandomCodeUtil.generateCharCode(4);
        String fileName = "" + System.currentTimeMillis() + s + "-" + uploadFile.getOriginalFilename();
        String fileDir = request.getSession().getServletContext().getRealPath("/") + "/tempupload";

        File _fileDir = new File(fileDir);
        if (!_fileDir.exists()) {
            _fileDir.mkdirs();
        }

        //临时文件路径
        String realPath = fileDir + File.separator + fileName;

        //临时存储文件
        File file = new File(realPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //multipartfile 转换成 file
        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileName = fileName.toLowerCase();
        if (fileName.endsWith(ExcelUtil.EXCEL_2003)) {
            ExcelUtil.addErrorMessageToXls(file, errorMap, cellNum);
        }
        if (fileName.endsWith(ExcelUtil.EXCEL_2007)) {
            ExcelUtil.addErrorMessageToXlsx(file, errorMap, cellNum);
        }

        //文件上传服务器
        String access_url = FileUploadUtil.saveFileToDist(file, fileName);

        //删除临时文件
        if (file.delete()) {
            _log.warn("删除临时文件成功!", file.getName());
        }

        return access_url;
    }

    /**
     * 获取上传的文件
     *
     * @param request
     * @return
     */
    private MultipartFile getUploadFile(HttpServletRequest request) {
        // 设置上下方文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form是否有enctype="multipart/form-data"
        if (!multipartResolver.isMultipart(request)) {
            return null;
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multiRequest.getFileNames();
        while (iter.hasNext()) {
            // 由CommonsMultipartFile继承而来,拥有上面的方法.
            MultipartFile file = multiRequest.getFile(iter.next());
            if (file != null) {
                return file;
            }
        }
        return null;
    }

    /**
     * 从excel读取指定列的数据
     *
     * @param request
     * @param cellNum
     * @return
     */
    private List<Map<Integer, String>> getData(HttpServletRequest request, int cellNum) {
        MultipartFile file = getUploadFile(request);
        if (null == file) {
            return null;
        }
        List<Map<Integer, String>> data = null;
        try {
            data = ExcelUtil.poiRead(file, cellNum);
        } catch (Exception e) {
        }
        return data;
    }

    /**
     * 下载导入模板xml
     *
     * @param request
     * @param name
     * @return
     */
    public String templateXml(HttpServletRequest request, String name) {
        return FileUtil.getRealPath(request, template + name + ".xml");
    }

    private List<Map<Integer, String>> getExcelHead(HttpServletRequest request) {
        MultipartFile file = getUploadFile(request);
        if (null == file) {
            return null;
        }
        List<Map<Integer, String>> data = null;
        try {
            data = ExcelUtil.poiReadHead(file);
        } catch (Exception e) {
        }
        return data;

    }
}

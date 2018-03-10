package com.thinkjoy.admin.controller.upload;


import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.DownloadUtil;
import com.thinkjoy.common.util.FileUtil;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by xufei on 2017/11/28.
 */
@Controller
@RequestMapping("/export")
@Api(value ="下载导出管理",description = "下载导出管理")
public class ExportController extends BaseController {
    private static Logger _log= LoggerFactory.getLogger(ExportController.class);
    private static final String template = "/template/";
    /**
     * 下载导入模板
     *
     * @param response
     * @param request
     * @param name
     * @throws IOException
     */
    @RequestMapping(value = "/template/{name}")
    public void template(HttpServletResponse response,HttpServletRequest request, @PathVariable("name") String name)
            throws IOException {

        String filePath = FileUtil.getRealPath(request, template + name
                + ".xlsx");

        try {
            DownloadUtil.download(response, request, name + ".xlsx",
                    FileUtils.openInputStream(new File(filePath)));
        } catch (IOException e) {
            DownloadUtil.download(response, request, "找不到目标文件.txt",
                    IOUtils.toInputStream("找不到目标文件", "UTF-8"));
        }

    }
}

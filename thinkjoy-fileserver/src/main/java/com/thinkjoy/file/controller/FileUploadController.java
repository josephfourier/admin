package com.thinkjoy.file.controller;

import com.thinkjoy.file.util.DateUtil;
import com.thinkjoy.file.util.RandomCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by xufei on
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {
    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Value("#{configProperties['save_url']}")
    private String saveUrl;

    @Value("#{configProperties['access_url']}")
    private String accessUrl;

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uplaod(MultipartFile file, String fileName, HttpServletRequest request) {
        // 设置上下方文
        logger.info("{"+DateUtil.getNowTimeStamp()+"}收到请求:" + file.getOriginalFilename());
        logger.info("{"+DateUtil.getNowTimeStamp()+"}文件保存地址:" + saveUrl);
        logger.info("{"+DateUtil.getNowTimeStamp()+"}文件访问地址:" + accessUrl);

        String results = null;
        if (file != null) {
            if (StringUtils.isBlank(saveUrl)) {
                return results;
            }
            try {
                // 文件目录地址
                File directoryFile = new File(saveUrl);
                // 先建立文件目录
                if (!directoryFile.exists()) {
                    directoryFile.mkdirs();
                }
                // 文件不存在则创建文件
                //
                fileName = fileName.substring(fileName.lastIndexOf("."));
                String _fileName = "/upload/" + DateUtil.getTimeStamp() + RandomCodeUtil.generateNumCode(6) + fileName;
                String filePath = saveUrl + _fileName;
                File _file = new File(filePath);
                if (_file.exists()) {
                    _file.createNewFile();
                }

                file.transferTo(_file);
                results = accessUrl + _fileName;

            } catch (IllegalStateException e) {
                e.printStackTrace();
                logger.error("文件上传出错", e);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("文件上传出错", e);
            }


        }else{
            logger.info("{"+DateUtil.getNowTimeStamp()+"}文件为null");
        }
        return results;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getuplaod(HttpServletRequest request) {
        return "index";
    }
}

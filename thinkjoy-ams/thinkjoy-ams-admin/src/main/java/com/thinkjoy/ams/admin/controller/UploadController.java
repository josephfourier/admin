package com.thinkjoy.ams.admin.controller;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xufei on 2017/7/19.
 */
@Controller
@RequestMapping("/upload")
@Api(value = "文件上传管理", description = "文件上传管理")
public class UploadController extends BaseController {
    private final static Logger _log = LoggerFactory.getLogger(UploadController.class);

    /**
     * 头像文件上传，文件名称默认file
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "图片上传")
    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
    @ResponseBody
    public String imageUpload(HttpServletRequest request,HttpServletResponse response){
        return FileUploadUtil.saveFileToDist(request);
    }

}

package com.thinkjoy.enrollment.admin.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.thinkjoy.ams.dao.model.AmsApp;
import com.thinkjoy.ams.dao.model.AmsApppermission;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.common.web.ServletUtil;
import com.thinkjoy.enrollment.admin.util.MatrixToImageWriter;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolExample;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.rpc.api.UcenterSchoolService;
import com.thinkjoy.ucenter.rpc.api.UcenterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台controller
 *
 * @author wangcheng
 * @date 2017/11/06
 */
@Controller
@RequestMapping("/manage")
@Api(value = "后台管理", description = "后台管理")
public class ManageController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private AmsAppService amsAppService;

    @Autowired
    private UcenterUserService ucenterUserService;

	@Autowired
	private UcenterSchoolService ucenterSchoolService;

    @ApiOperation(value = "后台首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap modelMap) {

        SecurityUtils.getSubject().isPermitted("admin");

        //从reqest的session中获取这些信息
        HttpServletRequest httpServletRequest = WebUtils.toHttp(ServletUtil.getRequest());
        HttpSession session = httpServletRequest.getSession();

        String relationCode = (String) session.getAttribute("relationCode");
        String userId = (String) session.getAttribute("userId");

        if ("null".equals(relationCode) ||
                "null".equals(userId) ||
                StringUtils.isBlank(relationCode) ||
                StringUtils.isBlank(userId)) {
            _log.error("子系统登录参数异常");
            throw new RuntimeException("子系统登录参数异常!");
        }

        String appId = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.appID");
        if (StringUtils.isBlank(appId)) {
            _log.error("zk中没有配置thinkjoy.web.appID");
            throw new BusindessException("zk中没有配置thinkjoy.web.appID!");
        }

        //获取当前登录用户
        UcenterUser ucenterUser = ucenterUserService.selectByPrimaryKey(Integer.valueOf(userId));

        AmsApp curApp = amsAppService.getIsPersonalByClientId(appId);

        //首先判断缓存中有没有角色与权限
        List<AmsApppermission> cachedPermissions = (List<AmsApppermission>) session.getAttribute("permissions");
		UcenterSchoolExample ucenterSchoolExample=new UcenterSchoolExample();
		UcenterSchoolExample.Criteria criteria=ucenterSchoolExample.createCriteria();
		criteria.andSchoolCodeEqualTo(relationCode);
		UcenterSchool ucenterSchool=this.ucenterSchoolService.selectFirstByExample(ucenterSchoolExample);

        //过滤掉系统级权限
        modelMap.put("appPermissions", cachedPermissions);
        modelMap.put("amsApp", curApp);
        modelMap.put("ucenterUser", ucenterUser);
		modelMap.put("ucenterSchool", ucenterSchool);
        //存入request的session,避免多次访问redis
        request.getSession().setAttribute("relationCode", relationCode);

        return "/manage/index.jsp";
    }

	@ApiOperation(value = "招生系统首页")
	@RequiresPermissions("enroll:manage:read")
	@RequestMapping(value = "/enrollment/index", method = RequestMethod.GET)
	public String enrollindex(HttpServletRequest request, ModelMap modelMap,HttpServletResponse resp) {
		SecurityUtils.getSubject().isPermitted("admin");
		//从reqest的session中获取这些信息
		HttpServletRequest httpServletRequest = WebUtils.toHttp(ServletUtil.getRequest());
		HttpSession session = httpServletRequest.getSession();

		String relationCode = (String) session.getAttribute("relationCode");
		modelMap.put("schoolcode",relationCode);


		//String address="http://enroll.zhijiaoyun.net/enrollBm/bm/mobile?schoolCode="+relationCode;

		/*
		* 新加入口包含报名及查询
		* */
		String address = "http://enroll.zhijiaoyun.net/enrollBm/bm/index?schoolCode="+relationCode;

		//this.placeQrOrder(address, resp);

		modelMap.put("address", address);

		resp.setHeader("Cache-Control", "no-store");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 0);

		address= URLDecoder.decode(address);
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(address, BarcodeFormat.QR_CODE, 150, 150, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}

		try {
			ServletContext servletContext = request.getSession().getServletContext();
			String filePath = servletContext.getRealPath("/")+"/resources/thinkjoy-admin/images/qrcode/"+relationCode+".png";
			File file=new File(filePath);
			if(!file.exists()){
				MatrixToImageWriter.writeToFile(bitMatrix, "png",file );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/manage/enrollindex/index.jsp";
	}

	//生成二维码
	@RequestMapping(value = "/placeQrOrder", method = RequestMethod.GET)
	public String placeQrOrder(String address,HttpServletResponse resp)  {

		resp.setHeader("Cache-Control", "no-store");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 0);

		address= URLDecoder.decode(address);
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter()
					.encode(address, BarcodeFormat.QR_CODE, 300, 300, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}

		try {
			MatrixToImageWriter.writeToStream(bitMatrix, "png", resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//return Urls.VIEWS + "pay-index";
		return null;
	}


}
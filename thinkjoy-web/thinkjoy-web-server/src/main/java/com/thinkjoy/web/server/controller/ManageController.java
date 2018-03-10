package com.thinkjoy.web.server.controller;

import com.thinkjoy.ams.dao.model.AmsApp;
import com.thinkjoy.ams.dao.model.AmsAppExample;
import com.thinkjoy.ams.dao.model.OauthAccessToken;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.oauth.client.util.SerializableUtil;
import com.thinkjoy.ucenter.dao.model.UcenterFacultyExample;
import com.thinkjoy.ucenter.dao.model.UcenterStudentExample;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherExample;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterUserIdentityDto;
import com.thinkjoy.ucenter.rpc.api.*;
import com.thinkjoy.upms.dao.model.UpmsLogExample;
import com.thinkjoy.upms.rpc.api.UpmsLogService;
import com.thinkjoy.web.common.constant.WebResult;
import com.thinkjoy.web.common.constant.WebResultConstant;
import com.thinkjoy.web.common.exception.BusindessException;
import com.thinkjoy.web.common.exception.NoUserTypeException;
import com.thinkjoy.web.dao.model.WebUserAppcollections;
import com.thinkjoy.web.dao.model.WebUserAppcollectionsExample;
import com.thinkjoy.web.rpc.api.WebUserAppcollectionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 后台controller
 * Created by jingqinghua on 2017/01/19.
 */
@Controller
@RequestMapping("/manage")
@Api(value = "首页管理", description = "首页管理")
public class ManageController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(com.thinkjoy.web.server.controller.ManageController.class);

    /**
     * 全局会话key
     */
    private final static String THINKJOY_OAUTH_SERVER_SESSION_ID = "thinkjoy-oauth-server-session-id";

    @Autowired
    private UcenterUserIdentityService ucenterUserIdentityService;

    @Autowired
    private AmsAppService amsAppService;

    @Autowired
    private WebUserAppcollectionsService webUserAppcollectionsService;

	@Autowired
	private UcenterUserService ucenterUserService;

	@Autowired
	private UcenterFacultyService ucenterFacultyService;

	@Autowired
	private UcenterStudentService ucenterStudentService;

	@Autowired
	private UcenterTeacherService ucenterTeacherService;

	@Autowired
	private UpmsLogService upmsLogService;

    @ApiOperation(value = "前台首页")
    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {

        StringBuilder params = new StringBuilder();
        //根据用户名获取当前用户身份信息,显示到弹出框中
        // TODO: 17/11/22  需加上学校编码信息进行身份查询
        List<UcenterUserIdentityDto> userIdentites = ucenterUserIdentityService.getUserIdentitesByUserLoginName(UserUtil.getCurrentUser());


        // userType不为空,则表示请求为选身份操作
        String userType = request.getParameter("userType");

        if (StringUtils.isBlank(userType)) {
            //为空则从session中获取userType
            userType = (String) SecurityUtils.getSubject().getSession().getAttribute("userType");
        }

        if (StringUtils.isNotBlank(userType)) {
            boolean flag = false;
            //存入当前用户session中
            SecurityUtils.getSubject().getSession().setAttribute("userType", userType);

            UcenterUserIdentityDto remove = null;

            if (CollectionUtils.isNotEmpty(userIdentites)) {
                for (UcenterUserIdentityDto d : userIdentites) {
                    if (d.getUsertypeId() == Integer.parseInt(userType)) {
                        flag = true;
                        model.addAttribute("userIdentity", d);
                        //多身份标志,用于前台切换身份功能是否显示
                        model.addAttribute("isMulti", true);

                        //切换身份功能显示当前身份
                        remove = d;

                        //选完身份后根据身份所对应的组织机构,加载项目,根据项目id加载所属应用列表
                        List<AmsApp> apps = getApps(d);

                        model.addAttribute("apps", apps);
                        model.addAttribute("appClass", getAppClass(apps));

                        //我的收藏begin---
                        List<AmsApp> cApps = getCollectionApps(d);
                        model.addAttribute("cApps", cApps);
                        model.addAttribute("cAppClass", getAppClass(cApps));
                        //我的收藏end---

                        //用户当前身份
                        model.addAttribute("cUserType", userType);


                        // 首页图标url拼接参数
                        OauthAccessToken oauthAccessToken = updateToken(d);

                        params.append("?").append("token=").append(oauthAccessToken.getTokenId())
                                .append("&").append("userType=").append(d.getUsertypeId())
                                .append("&").append("userId=").append(d.getUserId())
                                .append("&").append("relationCode=").append(d.getRelationCode())
                                .append("&").append("phone=").append(d.getPhone())
                                .append("&").append("username=").append(d.getPhone());

                        model.addAttribute("params", params.toString());

						model.addAttribute("pv",getStatisticsByCurrentSchoolcode(d.getRelationCode()));
						//找到就退出循环
						break;
					}
				}

				userIdentites.remove(remove);

				model.addAttribute("userIdentites", userIdentites);

				if (!flag) {
					throw new NoUserTypeException("无此身份类型!");
				}
                return "/home.jsp";
            }
        }

        //用户单身份,直接显示
        if (CollectionUtils.isNotEmpty(userIdentites) && userIdentites.size() == 1) {
            model.addAttribute("userIdentity", userIdentites.get(0));
            //是否多身份?
            model.addAttribute("isMulti", false);

            //选完身份后根据身份所对应的组织机构,加载项目,根据项目id加载所属应用列表
            List<AmsApp> apps = getApps(userIdentites.get(0));

            model.addAttribute("apps", apps);
            model.addAttribute("appClass", getAppClass(apps));

            //我的收藏begin---
            List<AmsApp> cApps = getCollectionApps(userIdentites.get(0));
            model.addAttribute("cApps", cApps);
            model.addAttribute("cAppClass", getAppClass(cApps));
            //我的收藏end---

            //用户当前身份
            model.addAttribute("cUserType", userIdentites.get(0).getUsertypeId());

            // 首页图标url拼接参数,并更新token
            OauthAccessToken oauthAccessToken = updateToken(userIdentites.get(0));

            params.append("?").append("token=").append(oauthAccessToken.getTokenId())
                    .append("&").append("userType=").append(userIdentites.get(0).getUsertypeId())
                    .append("&").append("userId=").append(userIdentites.get(0).getUserId())
                    .append("&").append("relationCode=").append(userIdentites.get(0).getRelationCode())
                    .append("&").append("phone=").append(userIdentites.get(0).getPhone())
                    .append("&").append("username=").append(userIdentites.get(0).getPhone());

            model.addAttribute("params", params.toString());


        } else if (userIdentites.size() > 1) {//多身份,必须选择一个
            return "redirect:/manage/switchAuth";
        } else {
            throw new BusindessException("请联系管理员给该账户分配身份!");
        }
		model.addAttribute("pv",getStatisticsByCurrentSchoolcode(userIdentites.get(0).getRelationCode()));
        return "/home.jsp";
    }

    public List<String> getAppClass(List<AmsApp> apps) {
        if (CollectionUtils.isEmpty(apps)) {
            return new ArrayList<>();
        }
        List<String> cAppsClass = new ArrayList<>();
        for (AmsApp a : apps) {
            if (!cAppsClass.contains(a.getAppClass())) {
                cAppsClass.add(a.getAppClass());
            }
        }
        return cAppsClass;
    }

    /**
     * 用户当前选择的身份
     *
     * @param d
     * @return
     */
    public OauthAccessToken updateToken(UcenterUserIdentityDto d) {

        String sid = THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + SecurityUtils.getSubject()
                .getSession().getId();
        OauthAccessToken oauthAccessToken = SerializableUtil
                .protostuff_deserialize(RedisUtil.get(sid), OauthAccessToken.class);

        oauthAccessToken.setRelationCode(d.getRelationCode());//更新学校
        oauthAccessToken.setUsertypeId(d.getUsertypeId());//更新用户类型
        oauthAccessToken.setUserId(d.getUserId());//更新用户id
        oauthAccessToken.setPerPersonalization(d.getPerpersonalization());//更新个性化应用设置
        RedisUtil.set(sid, SerializableUtil.protostuff_serialize(oauthAccessToken), oauthAccessToken.getTokenExpiredSeconds());
        return oauthAccessToken;
    }


    public List<AmsApp> getCollectionApps(UcenterUserIdentityDto u) {
        WebUserAppcollectionsExample webUserAppcollectionsExample = new WebUserAppcollectionsExample();
        webUserAppcollectionsExample.createCriteria()
                .andUserIdEqualTo(u.getUserId())
                .andUsertypeIdEqualTo(u.getUsertypeId())
                .andRelationCodeEqualTo(u.getRelationCode());

        List<WebUserAppcollections> webUserAppcollectionses = webUserAppcollectionsService
                .selectByExample(webUserAppcollectionsExample);

        List<Integer> appIds = new ArrayList<>();

        for (WebUserAppcollections w : webUserAppcollectionses) {
            if (w.getAppId() != null) {
                appIds.add(w.getAppId());
            }
        }

        if (CollectionUtils.isEmpty(appIds)) {
            return new ArrayList<>();
        }

        AmsAppExample amsAppExample = new AmsAppExample();
        amsAppExample.createCriteria().andAppIdIn(appIds);

        List<AmsApp> amsApps = amsAppService.selectByExample(amsAppExample);
        return amsApps == null ? new ArrayList<AmsApp>() : amsApps;
    }

    /**
     * 选择或切换用户身份
     */
    @ApiOperation(value = "切换/选择身份")
    @RequestMapping("/switchAuth")
    public String changeIdentity(HttpServletRequest request, Model model) {

        //TODO 这里需要按学校编码和用户登录名查询
        List<UcenterUserIdentityDto> userIdentites = ucenterUserIdentityService
                .getUserIdentitesByUserLoginName(UserUtil.getCurrentUser());

        //用户身份信息
        model.addAttribute("userIdentities", userIdentites);

        return "/selectAuth.jsp";
    }


    protected List<AmsApp> getApps(UcenterUserIdentityDto dto) {
        List<AmsApp> apps;

        HashMap<String, String> param = new HashMap<>();
        param.put("relationCode", dto.getRelationCode());
        param.put("usertypeId", dto.getUsertypeId().toString());
        param.put("isPersonalization", dto.getPersonalization());

        if (dto.getPersonalization().equals(BaseConstants.PerPersonal.YES)) {
            param.put("isPersonalization", BaseConstants.PerPersonal.NO);
            param.put("userId", String.valueOf(dto.getUserId()));
            apps = amsAppService.getUserAppByAgencyOrSchoolCode(param);
        } else {
            apps = amsAppService.getAppsByAgencyOrSchoolCode(param);
        }

        if (apps == null) {
            apps = Collections.emptyList();
        }

        return apps;
    }

	@ApiOperation(value = "修改密码")
	@ResponseBody
	@RequestMapping(value = "/editpwd", method = RequestMethod.POST)
	public Object editpwd(HttpServletRequest request){
		String originalPassword = request.getParameter("originalPassword");//旧密码
		String newPassword = request.getParameter("newPassword");//新密码
		String confirmPassword = request.getParameter("confirmPassword");//确认新密码
		//Integer userId= Integer.valueOf(request.getParameter("userId"));
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		//获取当前登录的管理员用户
		UcenterUser ucenterUser =ucenterUserService.selectuserbeanMap(username);
		int count=0;
		if(ucenterUser!=null){
			String password=ucenterUser.getPassword();
			AESUtil.AESEncode(originalPassword);
			if(!password.equals(AESUtil.AESEncode(originalPassword))){
				return new WebResult(WebResultConstant.OLDPASSWORD,count);
			}
			if(newPassword.equals(confirmPassword)){
				ucenterUser.setPassword(AESUtil.AESEncode(confirmPassword));
				count = ucenterUserService.updateByPrimaryKeySelective(ucenterUser);
				return new WebResult(WebResultConstant.USUCCESS,count);
			}else{
				return new WebResult(WebResultConstant.DIFFPASSWORD,count);
			}
		}else{
			return new WebResult(WebResultConstant.Failure,count);
		}
	}
	@ApiOperation(value = "检查原始密码")
	@ResponseBody
	@RequestMapping(value = "/checkOriginalPassword", method = RequestMethod.POST)
	public String checkOriginalPassword(@RequestParam(value = "originalPassword", required = false) String originalPassword) {
		if (null != originalPassword && StringUtils.isEmpty(originalPassword)) return "false";

		String username = (String) SecurityUtils.getSubject().getPrincipal();

       UcenterUser ucenterUser = ucenterUserService.selectuserbeanMap(username);

        if (null != ucenterUser && ucenterUser.getPassword().equals(AESUtil.AESEncode(originalPassword))) {
            return "true";
        }
        return "false";
	}

	//統計學校信息
    public Map getStatisticsByCurrentSchoolcode(String schoolCode) {
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		UcenterFacultyExample.Criteria criteria=ucenterFacultyExample.createCriteria();

		UcenterStudentExample ucenterStudentExample = new UcenterStudentExample();
		UcenterStudentExample.Criteria criteria1 = ucenterStudentExample.createCriteria();

		UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();
		UcenterTeacherExample.Criteria criteria2 = ucenterTeacherExample.createCriteria();

		UpmsLogExample upmsLogExample = new UpmsLogExample();
		UpmsLogExample.Criteria criteria3 = upmsLogExample.createCriteria();

        Map map=new HashMap();
		Integer facultyNum=0;
		Integer teacherNum=0;
		Integer studentNum=0;
		Integer peopleNum=0;
		long todayNum=0L;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		Date today=null;
		Date dt2=null;
		Date dt0=null;
		try {
			today=df.parse(df.format(new Date()));// new Date()为获取当前系统时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_YEAR,-1);
		Date dt1=rightNow.getTime();
		String stime=df.format(dt1)+" 00:00:00";
		String etime=df.format(dt1)+" 23:59:59";
//		String stime="2017-12-18 00:00:00";
//		String etime="2017-12-18 23:59:59";
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		try {
			dt0=df1.parse(stime);
		    dt2=df1.parse(etime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 开始时间
//		long startTime =Long.parseLong(stime);
		long startTime =dt0.getTime();
		// 结束时间
//		long endTime =Long.parseLong(etime);
		long endTime =dt2.getTime();
		if (StringUtils.isNotBlank(schoolCode)) {
			criteria.andSchoolCodeEqualTo(schoolCode);
			criteria1.andSchoolCodeEqualTo(schoolCode);//目前无法区分是否在校，取全部学生，等作为离校系统在做修改
			criteria2.andSchoolCodeEqualTo(schoolCode);
			criteria3.andStartTimeBetween(startTime,endTime);
			facultyNum=ucenterFacultyService.countByExample(ucenterFacultyExample);
			teacherNum=ucenterTeacherService.countByExample(ucenterTeacherExample);
			studentNum=this.ucenterStudentService.countByExample(ucenterStudentExample);
			peopleNum=teacherNum+studentNum;
			todayNum=upmsLogService.selectpeopleNum(schoolCode,startTime,endTime);
		}
		map.put("facultyNum", facultyNum);
		map.put("peopleNum", peopleNum);
		map.put("todayNum", todayNum);
        return map;
    }
}
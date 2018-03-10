package com.thinkjoy.enrollment.admin.controller.mobile;

import com.alibaba.fastjson.JSONArray;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.base.EnrollResult;
import com.thinkjoy.common.base.EnrollResultConstant;
import com.thinkjoy.common.smssend.SmsSend;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.common.xftpay.HttpUtils;
import com.thinkjoy.common.xftpay.XFTPayCommonUtil;
import com.thinkjoy.common.xftpay.XMLUtil;
import com.thinkjoy.enrollment.dao.model.*;
import com.thinkjoy.enrollment.rpc.api.*;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gengsongbo on 2017/11/3.
 */
@Controller
@RequestMapping("/enrollBm/bm")
@Api(value ="招生管理",description = "招生管理")
class EnrollBmController extends BaseController<EnrollStudent, EnrollStudentService> {
	private static Logger _log = (Logger) LoggerFactory.getLogger(EnrollBmController.class);

	@Autowired
	private EnrollStudentService enrollStudentService;
	@Autowired
	private EnrollBatchService enrollBatchService;
	@Autowired
	private EnrollTeacherService enrollTeacherService;
	@Autowired
	private UcenterDictValuesService ucenterDictValuesService;
	@Autowired
	private UcenterAreaService ucenterAreaService;
	@Autowired
	private UcenterSpecialtyService ucenterSpecialtyService;
	@Autowired
	private EnrollPlanService enrollPlanService;
	@Autowired
	private UcenterFacultyService ucenterFacultyService;
	@Autowired
	private EnrollChargedetailService enrollChargedetailService;
	@Autowired
	private UcenterSchoolService ucenterSchoolService;

	@Autowired
	private EnrollOrderService enrollOrderService;

	@Override
	protected EnrollStudentService getService() {
		return enrollStudentService;
	}

	@ApiOperation("移动招生入口")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap,
						HttpServletRequest request,
						@RequestParam(value = "schoolCode", defaultValue = "0", required = false) String schoolCode){

		modelMap.put("schoolCode", schoolCode);
		return "/manage/enrollBm/index.jsp";
	}


	@ApiOperation("招生mobile表单页面")
	@RequestMapping(value = "/mobile", method = RequestMethod.GET)
	public String mobile(ModelMap modelMap,HttpServletRequest request){

		String backUrl = request.getHeader("referer");

		String schoolCode =request.getParameter("schoolCode");
		Calendar date = Calendar.getInstance();
		String batchYear = String.valueOf(date.get(Calendar.YEAR));
		modelMap.put("batchYear", batchYear);
		modelMap.put("schoolCode", schoolCode);
		modelMap.put("backUrl", backUrl);

		setdata(modelMap, schoolCode);

		return "/manage/enrollBm/mobile.jsp";
	}

	@ApiOperation(value = "新增学生mobile页面")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Object create(EnrollStudent enrollStudent,
						 HttpServletRequest request){
		ComplexResult result = FluentValidator.checkAll()
				.on(enrollStudent.getStudentName(), new LengthValidator(1, 128, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new EnrollResult(EnrollResultConstant.INVALID_LENGTH, result.getErrors());
		}
		EnrollBatchExample enrollbatchExample = new EnrollBatchExample();
		EnrollBatchExample.Criteria criteria = enrollbatchExample.createCriteria();

		String username = enrollStudent.getStudentName();
		long time = System.currentTimeMillis();
		int year=Integer.parseInt(DateUtil.getYear());
		String yeardate = String.valueOf(year+3)+"-07-01";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=null;
		try {
			date1 = sf.parse(yeardate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		criteria.andBatchYearEqualTo(year).andSchoolCodeEqualTo(enrollStudent.getSchoolCode());
		EnrollBatch enrollBatch=this.enrollBatchService.selectFirstByExample(enrollbatchExample);
		EnrollFamily enrollFamily=new EnrollFamily();
		String relation=request.getParameter("relation");
		String name=request.getParameter("name");
		String relationtel=request.getParameter("relationtel");


		enrollStudent.setBatchId(enrollBatch.getBatchId());
		enrollStudent.setBatchName(enrollBatch.getBatchName());
		enrollStudent.setDomicile(enrollStudent.getOrigin());
		enrollStudent.setFromplace(enrollStudent.getOrigin());
		enrollStudent.setPolitics(BaseConstants.politicsStatus.qz);
		enrollStudent.setFamilyPhone(relationtel);
		enrollStudent.setStudentType(BaseConstants.targetStatus.czby);
		enrollStudent.setGradTime(date1);


		enrollStudent.setCreator(username);
		enrollStudent.setCtime(time);
		enrollStudent.setYear(year);
		enrollStudent.setBatchYear(year);

		enrollStudent.setFeeStatus(BaseConstants.FeeStatus.WJF);
		enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.YXWSY);

		enrollFamily.setRelation(relation);
		enrollFamily.setName(name);
		enrollFamily.setRelationtel(relationtel);
		enrollFamily.setCtime(time);
		enrollFamily.setCreator(username);
		int count1=enrollStudentService.insertSelective(enrollStudent,enrollFamily);
		return new EnrollResult(EnrollResultConstant.SUCCESS,count1);
	}

	@ApiOperation("招生pc首页")
		 @RequestMapping(value = "/pc", method = RequestMethod.GET)
		 public String pc(ModelMap modelMap,HttpServletRequest request){
		 String schoolCode =request.getParameter("schoolCode");

		 return "/manage/enrollBm/pc.jsp";
	}

	@ApiOperation("招生pc首页")
	@RequestMapping(value = "/serachIndex", method = RequestMethod.GET)
	public String serachIndex(ModelMap modelMap,HttpServletRequest request){

		String schoolCode =request.getParameter("schoolCode");
		return "/manage/enrollBm/serachIndex.jsp";
	}

	@ApiOperation("招生pc首页")
	@RequestMapping(value = "/serachResult", method = RequestMethod.GET)
	public String serachResult(ModelMap modelMap,HttpServletRequest request){

		String schoolCode =request.getParameter("schoolCode");
		String examNum =request.getParameter("schoolCode");
		String idcard =request.getParameter("schoolCode");

		return "/manage/enrollBm/serachResult.jsp";
	}
	/**
	 * 根据校内专业编码查询专业信息
	 * @param specialtyCode
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("查询校内专业编码查询专业信息")
	@ResponseBody
	@RequestMapping(value="/specialty",method = RequestMethod.POST)
	public Object ucenterSpecialty(String specialtyCode,String schoolcode) {
		JSONArray array = new JSONArray();
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
//		ucenterSpecialtyExample.createCriteria().andSpecialtyCodeEqualTo(specialtyCode).andSchoolCodeEqualTo(schoolcode).andYearEqualTo(Integer.parseInt(DateUtil.getYear()));
		ucenterSpecialtyExample.createCriteria().andSpecialtyCodeEqualTo(specialtyCode).andSchoolCodeEqualTo(schoolcode);
		UcenterSpecialty ucenterSpecialty = ucenterSpecialtyService.selectFirstByExample(ucenterSpecialtyExample);
		array.add(ucenterSpecialty);
		return  array;
	}

	@ApiOperation(value = "行政区划列表")
	@RequestMapping(value = "/areaList", method = RequestMethod.GET)
	@ResponseBody
	public Object list(
			@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
			@RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
			@RequestParam(required = false, defaultValue = "", value = "search") String search,
			@RequestParam(required = false, defaultValue = "", value = "areaCode") String areaCode,
			@RequestParam(value = "areaType") String areaType,
			@RequestParam(required = false, defaultValue = "", value = "status") String status,
			@RequestParam(required = false, value = "sort") String sort,
			@RequestParam(required = false, value = "order") String order) {
		UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
		UcenterAreaExample.Criteria criteria=ucenterAreaExample.createCriteria();
		criteria.andStatusEqualTo(UcenterConstant.Status.NORMAL);
		if(!"".equals(areaCode)&&!"0".equals(areaCode)){
			criteria.andPcodeEqualTo(areaCode);
		}
		if(!"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			ucenterAreaExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(search)) {
			ucenterAreaExample.or().andAreaNameLike("%" + search + "%");
		}
		if (StringUtils.isNotBlank(areaType)) {
			criteria.andAreaTypeEqualTo(areaType);
		}

		List<UcenterArea> rows=ucenterAreaService.selectByExampleForOffsetPage(ucenterAreaExample, offset, limit);
		long total = ucenterAreaService.countByExample(ucenterAreaExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}
	/**
	 * 查询字典数据
	 */
	public void setdata(ModelMap modelMap,String schoolcode) {
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		UcenterSpecialtyExample.Criteria criteria = ucenterSpecialtyExample.createCriteria();
		if (StringUtils.isNotBlank(schoolcode)) {
//			criteria.andSchoolCodeEqualTo(schoolcode).andYearEqualTo(Integer.parseInt(DateUtil.getYear()));
			criteria.andSchoolCodeEqualTo(schoolcode);
		}
		//获取该学校专业列表
		List<UcenterSpecialty> specialtyList = ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);
		modelMap.put("specialtyList", specialtyList);

		UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
		ucenterAreaExample.createCriteria()
				.andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
		//行政区划选择
		List<UcenterArea> ucenterAreas= ucenterAreaService.selectByExample(ucenterAreaExample);
		modelMap.put("ucenterAreas", ucenterAreas);

		UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
		//民族
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.NATION);
		List<UcenterDictValues> nationDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("nationDicts", nationDicts);
		ucenterDictValuesExample.clear();
		//毕业类别
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.TARGET);
		List<UcenterDictValues> targetDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("targetDicts", targetDicts);
		ucenterDictValuesExample.clear();
		//户籍性质
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.DOMICILETYPE);
		List<UcenterDictValues> domicileDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("domicileDicts", domicileDicts);
		ucenterDictValuesExample.clear();
		//政治面貌
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.POLITICS);
		List<UcenterDictValues> politicsDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("politicsDicts", politicsDicts);
		ucenterDictValuesExample.clear();
		//是否是贫困生
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ISPOOR);
		List<UcenterDictValues> ispoorDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("ispoorDicts", ispoorDicts);
		ucenterDictValuesExample.clear();
		//是否住校
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ISLIVESCHOOL);
		List<UcenterDictValues> isliveschoolDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("isliveschoolDicts", isliveschoolDicts);
		ucenterDictValuesExample.clear();
		//性别
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.SEX);
		List<UcenterDictValues> sexDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("sexDicts", sexDicts);
		ucenterDictValuesExample.clear();
		//缴费状态
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.FEESTATUS);
		List<UcenterDictValues> feestatusDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("feestatusDicts", feestatusDicts);
		ucenterDictValuesExample.clear();
		//录取状态
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ENROLLSTATUS);
		List<UcenterDictValues> enrollstatusDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("enrollstatusDicts", enrollstatusDicts);
		ucenterDictValuesExample.clear();

		//录取志愿
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ENROLLWILL);
		List<UcenterDictValues> lqzyDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("lqzyDicts", lqzyDicts);
		ucenterDictValuesExample.clear();

		EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
		EnrollTeacherExample enrollTeacherExample = new EnrollTeacherExample();
		List<EnrollBatch> batchList=null;
		List<EnrollTeacher> teacherList=null;
		int year=Integer.parseInt(DateUtil.getYear());
		if (StringUtils.isNotBlank(schoolcode)) {
			enrollBatchExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andBatchYearEqualTo(year);
			//招生批次
			batchList = enrollBatchService.selectByExample(enrollBatchExample);
			enrollTeacherExample.createCriteria().andSchoolCodeEqualTo(schoolcode);
			//招生老师
			teacherList = enrollTeacherService.selectByExample(enrollTeacherExample);
		}
		modelMap.put("batchList",batchList);
		//modelMap.put("teacherList",teacherList);
	}
	/**
	 * 年度信息
	 */
	public void setdata1(ModelMap modelMap,String schoolcode) {
		EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		List<EnrollBatch> batchList=null;
		List<UcenterFaculty> facultylist=null;
		int year=Integer.parseInt(DateUtil.getYear());
		if (StringUtils.isNotBlank(schoolcode)) {
			enrollBatchExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andBatchYearEqualTo(year);
			ucenterFacultyExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andYearEqualTo(year);
			//招生批次
			batchList = enrollBatchService.selectByExample(enrollBatchExample);
			facultylist=ucenterFacultyService.selectByExample(ucenterFacultyExample);
		}
		modelMap.put("batchList",batchList);
		modelMap.put("facultylist",facultylist);
		Integer year1=0;
		Integer byear=0;
		List list = new ArrayList();
		Calendar date = Calendar.getInstance();
		String yeardate = String.valueOf(date.get(Calendar.YEAR));
		try {
			year1 = Integer.valueOf(yeardate).intValue();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for(int i=0;i<5;i++){
			byear=year1-i;
			list.add(byear);
		}
		modelMap.put("list", list);
	}



	@ApiOperation("招生支付提交")
	@ResponseBody
	@RequestMapping(value = "/xft_pay", method = RequestMethod.GET)
	public String xft_pay(ModelMap modelMap,HttpServletRequest request,HttpServletResponse resp)throws JDOMException, IOException{
		String schoolCode =request.getParameter("schoolCode");

		String detailId =request.getParameter("detailId");

		String studentId =request.getParameter("studentId");


		EnrollChargedetail enrollChargedetail=enrollChargedetailService.selectByPrimaryKey(Integer.parseInt(detailId));
		EnrollStudent enrollStudent=enrollStudentService.selectByPrimaryKey(Integer.parseInt(studentId));

		UcenterSchoolExample ucenterSchoolExample=new UcenterSchoolExample();
		ucenterSchoolExample.createCriteria().andSchoolCodeEqualTo(schoolCode);
		UcenterSchool ucenterSchool=ucenterSchoolService.selectFirstByExample(ucenterSchoolExample);

		//测试账号
//		String mch_id="00000515";//校付通子商户号，由校付通提供
//		String mch_key="202CAB908F911145E0530401A8C0270E";//校付通子商户号密钥，由校付通提供

		//安陆市中等职业技术学校
		String mch_id="00000515";//校付通子商户号，由校付通提供
		String mch_key="202CAB908F911145E0530401A8C0270E";//校付通子商户号密钥，由校付通提供

		String order_url=PropertiesUtil.getInstance().getProperties().getProperty("order_url");
		String pay_url=PropertiesUtil.getInstance().getProperties().getProperty("pay_url");
		StringBuilder id = new StringBuilder();
		id.append(System.currentTimeMillis());
		String orderId=schoolCode+"-"+id.toString();//校付通子商户订单号，第三方内部订单号要唯一
		Double money=enrollChargedetail.getMoney();
		String moneyFee=String.format("%.0f", money*100);
		String body=enrollStudent.getStudentName()+":"+enrollChargedetail.getItemName()+":"+money+"元";//校付通子商户订单描述

		String xmlP=applyParXml(orderId,moneyFee,body,mch_id,mch_key,ucenterSchool.getSchoolName());
		String xmlR = HttpUtils.post(order_url, xmlP, HttpUtils.UTF8);
		System.out.println("申请结果"+xmlR);
		@SuppressWarnings("unchecked")
		Map<String, String> rm= XMLUtil.doXMLParse(xmlR);
		//验证返回结果是否合法
		if(XFTPayCommonUtil.isTenpaySign(rm, mch_key,HttpUtils.UTF8_NAME)){

			//保存订单信息
			EnrollOrder enrollOrder=new EnrollOrder();
			enrollOrder.setStudentId(enrollStudent.getStudentId());
			enrollOrder.setStudentName(enrollStudent.getStudentName());
			enrollOrder.setIdcard(enrollStudent.getIdcard());
			enrollOrder.setDetailName(enrollChargedetail.getDetailName());
			enrollOrder.setOrderId(orderId);
			enrollOrder.setOrderTime(new Date());
			enrollOrder.setOrderAmount(money);
			enrollOrder.setOrderStatus("2");//缴费状态(1-未缴费，2-预缴费，3-已缴费，4-待退费，5-已退费), 默认未缴费
			enrollOrder.setIsPayBack("0");//是否回调，默认：0  ；回调：1（回调时候根据订单号修改状态1，同时pay_status：1 已缴费）
			enrollOrder.setPayWay("微信");
			enrollOrder.setCreator(enrollStudent.getStudentName());
			enrollOrder.setCtime(System.currentTimeMillis());
			enrollOrder.setStatus("1");// 状态：    0.锁定1.正常(默认)
			enrollOrder.setYear(DateUtil.getCurrentYear());
			enrollOrder.setSchoolCode(schoolCode);
			enrollOrderService.insert(enrollOrder);
			//保存订单信息end

			//生成支付链接
			SortedMap<Object,Object> rs = new TreeMap<Object,Object>();
			//获取统一下单返回结果中的token_id
			rs.put("token_id", rm.get("token_id"));
			//生成支付签名
			String sign = XFTPayCommonUtil.createSign("UTF-8",rs, mch_key);// 生成签名
			//拼装支付链接
			String getUrl=pay_url+"?token_id="+rm.get("token_id")+"&sign="+sign;

			System.out.println("支付链接:" + getUrl);
//			// 指定允许其他域名访问
//			resp.setHeader('Access-Control-Allow-Origin:*');
//		// 响应类型
//			resp.setHeader('Access-Control-Allow-Methods:POST');
//		// 响应头设置
//			resp.setHeader('Access-Control-Allow-Headers:x-requested-with,content-type');
//			resp.addHeader("Access-Control-Allow-Origin", "*");
//			resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//			resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
//			resp.addHeader("Access-Control-Max-Age", "1800");
//			resp.setStatus(307);
//			//resp.setHeader("Location", "redirect_url");

			//注意：此处输出校付通支付界面URL，可以直接java跳转或者前台 javascript跳转
			//也可以把该链接生成二维码图片，展示到前端给用户扫描
			return getUrl;
		}
			return "fail";
	}

		/** 构建支付请求xml
	 * @param */
	private static String applyParXml(String orderId, String fee, String body, String mch_id, String mch_key,String schoolName) {
//		String notify_url=XftPayUtil.getNotifyUrl();//第三方应用异步调用URL，由第三方应用确定
//		String callback_url=XftPayUtil.getPageUrl();//第三方应用前台支付后返回URL，由第三方应用确定
		String notify_url= PropertiesUtil.getInstance().getProperties().getProperty("notify_url");//第三方应用异步调用URL，由第三方应用确定
		String callback_url=PropertiesUtil.getInstance().getProperties().getProperty("page_url");//第三方应用前台支付后返回URL，由第三方应用确定
		//发送给微信服务器的参数是xml格式的string，微信返回来的信息也是xml格式的string。
		SortedMap<Object,Object> pm = new TreeMap<Object,Object>();
		pm.put("service", "pay.weixin.jspay");// 接口类型
		pm.put("version", "1.0");// 版本号
		pm.put("charset", "UTF-8");// 字符编码
		pm.put("sign_type", "MD5");// 签名加密方式
		pm.put("mch_id", mch_id);// 村付通商户号
		pm.put("out_trade_no", orderId);// 客户内部订单号
		pm.put("body", body);// 商品描述
		pm.put("help_buy", schoolName);// 代付识别，如果开启代付需传入相关参数
		pm.put("total_fee", fee);// 支付金额
		//pm.put("user_Id", "oskcxuB_I93PxNoXdYkajzg5OiU4");// open_id
		pm.put("notify_url", notify_url);// 回调地址（采用动力加统一订单系统，到接口完成）
		pm.put("callback_url", callback_url);// 支付完成跳转
		pm.put("nonce_str", XFTPayCommonUtil.CreateNoncestr());
		String sign = XFTPayCommonUtil.createSign("UTF-8",pm, mch_key);// 生成签名
		pm.put("sign", sign);
		return XFTPayCommonUtil.getRequestXml(pm);// xml格式参数文本
	}


	@RequestMapping(value = "/notify",method = RequestMethod.POST)
	public void notifyPost(HttpServletRequest request, HttpServletResponse response) {
		_log.info("xmlN post=====");
		try {
			notifyFinal(request,response);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void notifyFinal(HttpServletRequest request,HttpServletResponse response) throws JDOMException, IOException {

		String xmlN = null;
		try {
			xmlN = getPostBody(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String xmlN="<xml><notify_id>O4MlR08kZja24Ocs</notify_id><sign><![CDATA[BD428EC9E7C79FC3470C0BE9B5E10ACC]]></sign><total_fee>1</total_fee><trade_no>80000006071440916568024779</trade_no><trade_status>99</trade_status><trade_time>20150830143908</trade_time><trade_type>JSAPI</trade_type><transaction_id>1004611009201508300745976885</transaction_id><xft_payid>80000000011440916568200200</xft_payid></xml>";
		if(StringUtils.isEmpty(xmlN)){
			_log.info("xmlN is null =====");
		}else{
			_log.info("xmlN is  not null ====="+xmlN);
		}
		String enc = HttpUtils.UTF8_NAME;
		if (null != xmlN && !xmlN.isEmpty()) {
			//判断签名
			@SuppressWarnings("unchecked")
			Map<String, String> nm = XMLUtil.doXMLParse(xmlN);// 解析XML
			String mch_key="202CAB908F911145E0530401A8C0270E";//校付通子商户号密钥，由校付通提供
			if(XFTPayCommonUtil.isTenpaySign(nm,mch_key,enc)){
				String result_code = nm.get("trade_status");
				String trade_no = nm.get("trade_no");//商户订单编号
				String xft_payid = nm.get("xft_payid");//校付通支付
				String total_fee=nm.get("total_fee");//订单金额
				if("99".equals(result_code)){//支付完成


					//更新订单信息
					EnrollOrderExample enrollOrderExample=new EnrollOrderExample();
					enrollOrderExample.createCriteria().andOrderIdEqualTo(trade_no);
					EnrollOrder enrollOrder=enrollOrderService.selectFirstByExample(enrollOrderExample);
					if(enrollOrder!=null){
						enrollOrder.setIsPayBack("1");//是否回调，默认：0  ；回调：1（回调时候根据订单号修改状态1，）
						enrollOrder.setOrderStatus(BaseConstants.FeeStatus.YJJF);///缴费状态(1-未缴费，2-预缴费，3-已缴费，4-待退费，5-已退费), 默认未缴费
						enrollOrderService.updateByPrimaryKeySelective(enrollOrder);
					}
					//更新学生缴费信息

					EnrollStudent enrollStudent =enrollStudentService.selectByPrimaryKey(enrollOrder.getStudentId());
					enrollStudent.setFeeStatus(BaseConstants.FeeStatus.YJJF);//缴费状态(1-未缴费，2-预缴费，3-已缴费，4-待退费，5-已退费)
					enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.YLQ);  //录取状态（1-未录取，2-预录取，3-已录取，4-已录取被删除)
					enrollStudentService.updateByPrimaryKeySelective(enrollStudent);

					//smssned
					SmsSend smsSend=new SmsSend();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String content=enrollStudent.getStudentName()+"同学您好,"+sdf.format(new Date())+"您通过职教云招生系统支付了"+enrollOrder.getOrderAmount()+"元,感谢使用！您可以登录职教云平台www.zhijiaoyun.net了解更多入学信息";
					System.out.println("content:"+content);
					_log.info("content:"+content);
					_log.warn("content:"+content);
					try {
						smsSend.sms_send(enrollStudent.getPhone(), content);
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					}

				}
				HttpUtils.print(response,"Success");
			}else{
				HttpUtils.print(response,"通知签名验证失败");
			}
		}
	}

	public String getPostBody(HttpServletRequest request) throws  Exception{
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen;
			try {
				readlen = request.getInputStream().read(buffer, i, contentLength - i);
			} catch (IOException e) {
				throw new Exception();
			}
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		String charEncoding = request.getCharacterEncoding();
		if (StringUtils.isBlank(charEncoding)) {
			charEncoding = "UTF-8";
		}
		String result;
		try {
			result = new String(buffer, charEncoding);
		} catch (UnsupportedEncodingException e) {

			throw new Exception();
		}
		if (StringUtils.isNotBlank(result) && result.charAt(0) != 0) {
			return result;
		}
		// 不标准的post请求时,值在Map里
		Map<?, ?> parameterMap = request.getParameterMap();
		Set<?> keySet = parameterMap.keySet();
		for (Iterator<?> iterator = keySet.iterator(); iterator.hasNext();) {
			Object key = (Object) iterator.next();
			if (key instanceof String) {
				result = (String) key;
				Object value = parameterMap.get(key);
				if (false == (value instanceof String) || StringUtils.isBlank((String) value)) {
					return result;
				}
			}

		}
		return null;
	}



	@ApiOperation("招生结果查询")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String query(ModelMap modelMap,HttpServletRequest request){
		String schoolCode =request.getParameter("schoolCode");

		UcenterSchoolExample ucenterSchoolExample=new UcenterSchoolExample();
		ucenterSchoolExample.createCriteria().andSchoolCodeEqualTo(schoolCode);
		UcenterSchool ucenterSchool=ucenterSchoolService.selectFirstByExample(ucenterSchoolExample);

		modelMap.put("ucenterSchool",ucenterSchool);
		return "/manage/enrollBm/query.jsp";
	}


	@ApiOperation("招生结果查询")
	@RequestMapping(value = "/queryResult", method = RequestMethod.GET)
	public String queryResult(ModelMap modelMap,HttpServletRequest request) {

		String schoolCode = request.getParameter("schoolCode");
		String idcard = request.getParameter("idcard");
		EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
		EnrollChargedetailExample.Criteria criteria = enrollChargedetailExample.createCriteria();
		criteria.andYearEqualTo(Integer.parseInt(DateUtil.getYear())).andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
		EnrollChargedetail enrollChargedetail=enrollChargedetailService.selectFirstByExample(enrollChargedetailExample);

		EnrollStudentExample enrollStudentExample = new EnrollStudentExample();
		enrollStudentExample.createCriteria().andSchoolCodeEqualTo(schoolCode).andIdcardEqualTo(idcard).andStatusEqualTo(BaseConstants.Status.NORMAL);
		EnrollStudent enrollStudent = enrollStudentService.selectFirstByExample(enrollStudentExample);
		modelMap.put("enrollStudent", enrollStudent);
		modelMap.put("enrollChargedetail", enrollChargedetail);

        String target = null;
        if (enrollStudent != null) {
            String status = enrollStudent.getEnrollStatus();

            if (status.equals(BaseConstants.EnrollStatus.YXWSY)) target = "check.jsp";

            if (status.equals(BaseConstants.EnrollStatus.WLQ)) target = "reject.jsp";

            if (status.equals(BaseConstants.EnrollStatus.YLQ) && enrollStudent.getFeeStatus().equals(BaseConstants.FeeStatus.YJF))  target = "yulu.jsp";

			if (status.equals(BaseConstants.EnrollStatus.YLQ) && enrollStudent.getFeeStatus().equals(BaseConstants.FeeStatus.YJJF))  target = "payed.jsp";

			if (status.equals(BaseConstants.EnrollStatus.YJLQ)) target = "yiluqu.jsp";

            /* 已缴费但未点录取操作，暂用录取被删除状态 */
            if (status.equals(BaseConstants.EnrollStatus.SCLQ)) target = "payed.jsp";

        }
            /*if (enrollStudent.getEnrollStatus().equals(BaseConstants.EnrollStatus.YXWSY)) {
				return "/manage/enrollBm/check.jsp";
			} else if (enrollStudent.getEnrollStatus().equals(BaseConstants.EnrollStatus.WLQ)) {
				return "/manage/enrollBm/reject.jsp";

			} else if (enrollStudent.getEnrollStatus().equals(BaseConstants.EnrollStatus.YLQ)&&enrollStudent.getFeeStatus().equals(BaseConstants.FeeStatus.YJF)) {

				return "/manage/enrollBm/yulu.jsp";
			} else if (enrollStudent.getEnrollStatus().equals(BaseConstants.EnrollStatus.YJLQ)) {
				return "/manage/enrollBm/yiluqu.jsp";
			}
		}else{
			return "/manage/enrollBm/yiluqu.jsp";
		}*/

		return "/manage/enrollBm/" + target;
	}


	@ApiOperation("检查身份证存在")
	@RequestMapping(value = "/checkIdcard", method = RequestMethod.POST)
	@ResponseBody
	public String checkIdCard(@RequestParam(name = "schoolCode", required = false, defaultValue = "0") String schoolCode,
							  @RequestParam(name = "idcard", required = false, defaultValue = "0") String idcard,
							  @RequestParam(name = "reverse", required = false, defaultValue = "0") int reverse) {

		EnrollStudentExample enrollStudentExample = new EnrollStudentExample();
		enrollStudentExample.createCriteria().andSchoolCodeEqualTo(schoolCode).andIdcardEqualTo(idcard).andStatusEqualTo(BaseConstants.Status.NORMAL);
		EnrollStudent enrollStudent = enrollStudentService.selectFirstByExample(enrollStudentExample);
		if (reverse == 0)
			return null == enrollStudent ? "false" : "true";
		return null == enrollStudent ? "true" : "false";
	}


	@ApiOperation("招生mobile表单页面")
	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public String temp(ModelMap modelMap,HttpServletRequest request){

		return "/manage/enrollBm/template.jsp";
	}

}

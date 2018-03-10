package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.common.smssend.SmsSend;
import com.thinkjoy.common.util.StringUtil;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.enrollment.dao.mapper.EnrollFamilyMapper;
import com.thinkjoy.enrollment.dao.mapper.EnrollSpecialtychangeLogMapper;
import com.thinkjoy.enrollment.dao.mapper.EnrollStudentMapper;
import com.thinkjoy.enrollment.dao.model.*;
import com.thinkjoy.enrollment.dao.model.enrollDto.EnrollStudentDto;
import com.thinkjoy.enrollment.rpc.api.EnrollStudentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* EnrollStudentService实现
* Created by user on 2017/11/2.
*/
@Service
@Transactional
@BaseService
public class EnrollStudentServiceImpl extends BaseServiceImpl<EnrollStudentMapper, EnrollStudent, EnrollStudentExample> implements EnrollStudentService
{

	private static Logger _log = LoggerFactory.getLogger(EnrollStudentServiceImpl.class);

    @Autowired
    EnrollStudentMapper enrollStudentMapper;
	@Autowired
	EnrollFamilyMapper enrollFamilyMapper;
	@Autowired
	EnrollSpecialtychangeLogMapper enrollSpecialtychangeLogMapper;
	private String templateUrl;
	private String noticeJs;
	private String noticePath;
	private String phantomjs;

	@Override
	public int updateByPrimaryKeySelective(EnrollStudent enrollStudent, EnrollSpecialtychangeLog spechangelog) {
		int count=enrollStudentMapper.updateByPrimaryKeySelective(enrollStudent);
		if(count!=0 && spechangelog.getStudentId()!=null ){
			enrollSpecialtychangeLogMapper.insertSelective(spechangelog);
		}
		return count;
	}

	@Override
	public int luquAndcreateNotice(EnrollStudent enrollStudent) {


		_log.info("luquAndcreateNotice   start ============0000000000!");
		_log.warn("luquAndcreateNotice   start ============0000000000!");
		System.out.print("luquAndcreateNotice   start ============0000000000");

//		String templateUrl="http://localhost:8080/file/upload/template.jsp";                        //模板地址
//		String noticePath="/Users/jingqinghua/Downloads/apache-tomcat-7.0.59/webapps/file/upload/"+enrollStudent.getSchoolCode()+"_"+enrollStudent.getStudentId()+".png";//生成通知书文件地址(最好在招生系统webapp/file/upload/下面
//		String noticeJs="/Users/jingqinghua/Desktop/tools/test.js";                                 //模板JS
//		String phantomjs="/Users/jingqinghua/Desktop/tools/phantomjs-2.1.1-macosx/bin/phantomjs";   //文件安装目录  分windows版本,macOs,linux版本



		String templateUrl="http://file.zhijiaoyun.net/enroll/template.jsp";                        //模板地址
		String noticePath="/opt/zjy/static/"+enrollStudent.getSchoolCode()+"_"+enrollStudent.getStudentId()+".png";//生成通知书文件地址(最好在招生系统webapp/file/upload/下面
		String noticeJs="/usr/local/test.js";                                 //模板JS
		String phantomjs="phantomjs";   //文件安装目录  分windows版本,macOs,linux版本



		_log.info("enrollStudent.setNoticeUrl(   info -start ============*********!");
		_log.warn("enrollStudent.setNoticeUrl    warn -start============********!");
		enrollStudent.setNoticeUrl("http://notice.zhijiaoyun.net/"+enrollStudent.getSchoolCode()+"_"+enrollStudent.getStudentId()+".png");
		_log.info("enrollStudent.setNoticeUrl    info-end ============********!");
		_log.warn("enrollStudent.setNoticeUrl    warn-end ============********!");

		int count=enrollStudentMapper.updateNoticePathByPrimary(enrollStudent);

		String xuesheng=enrollStudent.getStudentName()!=""?enrollStudent.getStudentName():"恺撒";
		String xueyuan="信息工程学院";
		String zhuanye=enrollStudent.getSpecialtyName()!=""?enrollStudent.getSpecialtyName():"计算机科学与技术";
		String xuezhi=enrollStudent.getSchoolSystem();

		String axuexiao="恭喜您已经被安陆中等职业技术学院";
		String fxuexiao="专业录取，您可登陆职教云平台www.zhijiaoyun.net了解更多入学信息，账号为您的手机号码，初始密码为身份证后六位。";
		String mobile=enrollStudent.getPhone();
		String content=axuexiao+enrollStudent.getSpecialtyName()+fxuexiao;

		if(xuezhi!=""){
			if(xuezhi=="101"){
				xuezhi="三年";
			}else{
				xuezhi="五年";
			}
		}else{
			xuezhi="五年";
		}
		String baodaoriqi="2017-09-01";
		templateUrl = templateUrl + "?xuesheng=" + URLEncoder.encode(xuesheng) + "&xueyuan=" + URLEncoder.encode(xueyuan) + "&zhuanye=" +URLEncoder.encode(zhuanye)+"&xuezhi="+URLEncoder.encode(xuezhi)+"&baodaoriqi="+URLEncoder.encode(baodaoriqi);
		try {
			_log.info("generateNotice   start ============11111111111111!");
			generateNotice(templateUrl,noticeJs,noticePath,phantomjs);
			_log.info("generateNotice   start ============3333333333333!");
		} catch (IOException e) {
			e.printStackTrace();
			_log.info("generateNotice error!");
			_log.error("generateNotice error!");
		}

		try {
			SmsSend.sms_send(mobile, content);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int yluquAndcreateNotice(EnrollStudent enrollStudent) {
		int count=enrollStudentMapper.updateByPrimaryKeySelective(enrollStudent);
		String axuexiao="恭喜您已经被安陆中等职业技术学院";
		String fxuexiao="专业预录取，学校将根据预交费用的先后顺序予以录取，录满即停，请您尽快登陆报名查询地址进行交费。";
		String mobile=enrollStudent.getPhone();
		String content=axuexiao+enrollStudent.getSpecialtyName()+fxuexiao;
		try {
			SmsSend.sms_send(mobile, content);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int insertSelective(EnrollStudent enrollStudent, EnrollFamily enrollFamily) {
		int scount=enrollStudentMapper.insertSelective(enrollStudent);
		 enrollFamily.setStudentId(enrollStudent.getStudentId());
		int count=this.enrollFamilyMapper.insertSelective(enrollFamily);
		return count;
	}

	@Override
	public List<EnrollStudentDto> selectfacultyMap(String schoolCode, String batchYear,String enrollStatus) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("batchYear", batchYear);
		map.put("enrollStatus", enrollStatus);
		List<EnrollStudentDto> enrollStudentList=enrollStudentMapper.selectfacultyMap(map);
		return enrollStudentList;
	}

	@Override
	public List<EnrollStudentDto> selectspecialtyMap(String schoolCode, String batchYear, String facultyCode,String enrollStatus) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("batchYear", batchYear);
		map.put("facultyCode", facultyCode);
		map.put("enrollStatus", enrollStatus);
		List<EnrollStudentDto> enrollStudentList=enrollStudentMapper.selectspecialtyMap(map);
		return enrollStudentList;
	}

	@Override
	public List<EnrollStudentDto> selectteacherMap(String schoolCode, String batchYear, String batchId,String enrollStatus) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("batchYear", batchYear);
		map.put("batchId", batchId);
		map.put("enrollStatus", enrollStatus);
		List<EnrollStudentDto> enrollStudentList=enrollStudentMapper.selectteacherMap(map);
		return enrollStudentList;
	}

	@Override
	public List<EnrollStudentDto> selectfromplaceMap(String schoolCode, String batchYear,String enrollStatus) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("batchYear", batchYear);
		map.put("enrollStatus", enrollStatus);
		List<EnrollStudentDto> enrollStudentList=enrollStudentMapper.selectfromplaceMap(map);

		return enrollStudentList;
	}

	@Override
	public List<EnrollStudentDto> selectMbatchMap(String schoolCode, String batchYear, String sex) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("batchYear", batchYear);
		map.put("sex", sex);
		List<EnrollStudentDto> enrollStudentList=enrollStudentMapper.selectMbatchMap(map);
		return enrollStudentList;
	}

	@Override
	public EnrollStudentDto selectWbatchMap(String schoolCode, String batchYear, Integer batchId, String sex, String enrollStatus) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("batchYear", batchYear);
		map.put("batchId", batchId);
		map.put("sex", sex);
		map.put("enrollStatus", enrollStatus);
		EnrollStudentDto studentDtobean=enrollStudentMapper.selectWbatchMap(map);
		return studentDtobean;
	}

	public void generateNotice(String templateUrl,String noticeJs,String noticePath,String phantomjs) throws IOException {
		this.templateUrl = templateUrl;
		this.noticeJs = noticeJs;
		this.noticePath = noticePath;
		this.phantomjs = phantomjs;

		Runtime rt = Runtime.getRuntime();
		Process process = null;
		try {

			_log.info("generateNotice   process ============22222222222222!");
			//参数说明===phantomjs安装路径 noticeJs:模板Js路径 templateUrl:模板路径  参数4:图片输出路径
			process = rt.exec(phantomjs+" "+noticeJs+" "+templateUrl+" "+noticePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = process.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		StringBuffer sbf = new StringBuffer();
		String tmp = "";
		try {
			while ((tmp = br.readLine()) != null && !tmp.contains("completed")) {
				sbf.append(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			process.destroy();
			is.close();
		}
	}


	@Override
	public int deleteStudent(String ids) {

		if (StringUtils.isBlank(ids)) {
			return 0;
		}

		String[] idArray = ids.split("-");
		List<Integer> idList = new ArrayList<>();
		for (String idStr : idArray) {
			idList.add(Integer.parseInt(idStr));
		}

		int i1 = 0;
		try {
			//删除家庭关系
			EnrollFamilyExample enrollFamilyExample = new EnrollFamilyExample();
			enrollFamilyExample.createCriteria()
					.andStudentIdIn(idList);
			int i = enrollFamilyMapper.deleteByExample(enrollFamilyExample);

			//删除学生表
			EnrollStudentExample enrollStudentExample = new EnrollStudentExample();
			enrollStudentExample.createCriteria()
					.andStudentIdIn(idList);
			i1 = this.enrollStudentMapper.deleteByExample(enrollStudentExample);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof InvocationTargetException) {
				if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {
					//String res = handleException(e);
					throw new RuntimeException("请先删除关联数据!");
				}
			}

			if (e instanceof DataIntegrityViolationException) {
				throw new RuntimeException("请先删除关联数据!");
			}
		}

		return i1;
	}


	@Override
	public Map<String, String> importEnrollStudent(EnrollStudent enrollStudent, Map<String, String> errorMap, Table tableValid, int rowNum, long errorNum) {
        if (errorMap==null){
            errorMap=new HashMap<>();
        }
        //验证学生信息是否重复 身份证号,电话号
        EnrollStudentExample enrollStudentExample=new EnrollStudentExample();
        enrollStudentExample.or().andIdcardEqualTo(enrollStudent.getIdcard());
        enrollStudentExample.or().andPhoneEqualTo(enrollStudent.getPhone());
        List<EnrollStudent> enrollStudents=selectByExample(enrollStudentExample);
        if(CollectionUtils.isNotEmpty(enrollStudents)){
            if(enrollStudents.size()==1){
                if(enrollStudents.get(0).getPhone().equals(enrollStudent.getPhone())){
                    errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "手机号码已存在");
                }
                if(enrollStudents.get(0).getIdcard().equals(enrollStudent.getIdcard())){
                    errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("idcard")), "身份证号已存在");
                }
            }else{
                errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "手机号码，身份证号出现重复");
            }
        }

        //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环
        if(errorMap!=null&&errorMap.size()>errorNum){
            return errorMap;
        }

		int i = enrollStudentMapper.insertSelective(enrollStudent);

		return errorMap;
	}



}
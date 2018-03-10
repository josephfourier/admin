package com.thinkjoy.admin.controller.upload.service;

import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.util.*;
import com.thinkjoy.common.util.xmlutil.Column;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xufei on 2017/11/30.
 */
@Component
public class UploadService {
    private static Logger _log= LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private UcenterDictValuesService ucenterDictValuesService;
    @Autowired
    private UcenterFacultyService ucenterFacultyService;
    @Autowired
    private UcenterSpecialtyService ucenterSpecialtyService;
    @Autowired
    private UcenterClassService ucenterClassService;
    @Autowired
    private UcenterStudentService ucenterStudentService;
    @Autowired
    private UcenterDepartmentService ucenterDepartmentService;
    @Autowired
    private UcenterTeacherService ucenterTeacherClassService;


    /**
     * 学生信息导入
     *
     *  规则要求：导入学生数据，手机号作为账号，密码为身份证后六位。编辑学生信息时候－帐号表信息与身份表信息同步
     *  数据分类：1、普通字段，2、字典字段，3、特殊字段
     *  一、不能为空验证 ，长度验证，格式验证
     *     1-不为空验证（notEmpty），2-(字符)长度校验（equalLength（等于），maxLength(最大)，minLength(最小)），3-格式校验（regEx（正则））
     *  二、普通字段值:通过反射机制实现给对象赋值，初始化
     *  三、字典字段:标记需要字典转换的字段,统一处理
     *  四、特殊字段:单独处理处理
     *  五、设置操作信息
     *  六、入库
     * @param datas
     * @param ucenterSchool
     * @return
     */
    public Map<String, String> importUcenterStudents(List<Map<Integer, String>> datas,UcenterSchool ucenterSchool,HttpServletRequest request,Table tableValid) {
        //初始化进度
        request.getSession().setAttribute("importTotal", datas.size());//导入数据总数
        request.getSession().setAttribute("importCount", 0);//导入数据总数
        request.getSession().setAttribute("importErrorCount", 0);//错误个数
        request.getSession().setAttribute("importSuccessCount", 0);//导入成功条数
        request.getSession().setAttribute("isFinished", false);//是否导入完成：否

        long time = System.currentTimeMillis();
        int year=DateUtil.getCurrentYear();

        //初始化字典表,多个字典表
        List<UcenterDictValues> ucenterDictValues= getDictByCode(tableValid);
        //初始化院系列表
        List<UcenterFaculty> ucenterFaculties=getUcenterFacultyList(ucenterSchool.getSchoolCode());
        //初始化专业列表
        List<UcenterSpecialty> ucenterSpecialties=getUcenterSpecialtyList(ucenterSchool.getSchoolCode());
        //初始化班级列表
        List<UcenterClass> ucenterClasses=getUcenterClassList(ucenterSchool.getSchoolCode());

        Map<String, String> errorMap = new HashMap<String, String>();
        int rowNum = 0;
        int successCount = 1;
        for(Map<Integer,String> data:datas){
            rowNum++;
            long errorNum=errorMap.size();
            //记录执行情况
            request.getSession().setAttribute("importCount",rowNum);//已处理行
            request.getSession().setAttribute("importErrorCount",errorNum);//处理每条数据前，记录一次错误数，因为可能中途返回
            //业务开始
            try{
                //一、不能为空验证,长度验证，格式验证
                dataVerification(data, errorMap, rowNum, tableValid);
                //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环
                if(errorMap.size()>errorNum){
                    continue;
                }

                //二、给对象赋值
                UcenterStudent ucenterStudent= ReflectGetValue.getInstance(data, tableValid, errorMap, rowNum, UcenterStudent.class);
                //三、字典数据转换
                setDictValueKeyBydata(ucenterDictValues,tableValid, data, errorMap, rowNum, ucenterStudent);

                // 四、特殊字段处理
                // 院系， 专业， 班级  名称->code(转码前，需要验证条件是否正确)
                if(StringUtils.isNotBlank(ucenterStudent.getFacultyName())&&ucenterStudent.getEnterYear()!=null){
                    UcenterFaculty ucenterFaculty=getOneUcenterFaculty(ucenterFaculties,ucenterStudent.getFacultyName(),ucenterStudent.getEnterYear());
                    if(ucenterFaculty!=null){
                        ucenterStudent.setFacultyCode(ucenterFaculty.getFacultyCode());
                    }else{
                        errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("facultyName")), BaseConstants.ImportError.PROPERTY_FACULTYNAME);
                    }

                }
                if(StringUtils.isNotBlank(ucenterStudent.getFacultyName())&&StringUtils.isNotBlank(ucenterStudent.getSpecialtyName())&&ucenterStudent.getEnterYear()!=null){
                    UcenterSpecialty ucenterSpecialty=getOneUcenterSpecialty(ucenterSpecialties,ucenterStudent.getFacultyName(),ucenterStudent.getSpecialtyName(),ucenterStudent.getEnterYear());
                    if(ucenterSpecialty!=null){
                        ucenterStudent.setSpecialtyCode(ucenterSpecialty.getSpecialtyCode());
                    }else{
                        errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("specialtyName")), BaseConstants.ImportError.PROPERTY_SPECIALTYNAME);
                    }
                }
                if(StringUtils.isNotBlank(ucenterStudent.getFacultyName())
                        &&StringUtils.isNotBlank(ucenterStudent.getSpecialtyName())
                        &&StringUtils.isNotBlank(ucenterStudent.getClassName())
                        &&ucenterStudent.getEnterYear()!=null){
                    UcenterClass ucenterClass=getOneUcenterClass(ucenterClasses,ucenterStudent.getFacultyName(),ucenterStudent.getSpecialtyName(),ucenterStudent.getClassName(),ucenterStudent.getEnterYear());
                    if(ucenterClass!=null){
                        ucenterStudent.setClassId(ucenterClass.getClassId());
                    } else {
                        errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("className")), BaseConstants.ImportError.PROPERTY_CLASSNAME);
                    }
                }

                //性别  sex
                String sex=data.get(tableValid.getPositonBycolName("sex"));
                if(StringUtils.isNotBlank(sex)){
                    ucenterStudent.setSex(DictUtil.sex.MAN.getName().equals(sex) ? DictUtil.sex.MAN.getIndex() : DictUtil.sex.WOMAN.getIndex());
                }
                //是否为贫困生   isPoor
                String  isPoor=data.get(tableValid.getPositonBycolName("isPoor"));
                if(StringUtils.isNotBlank(isPoor)){
                    ucenterStudent.setIsPoor(DictUtil.BooleanEnmu.YES.getName().equals(isPoor) ? DictUtil.BooleanEnmu.YES.getIndex() : DictUtil.BooleanEnmu.NO.getIndex());
                }
                // 是否住校  isLiveschool
                String isLiveschool=data.get(tableValid.getPositonBycolName("isLiveschool"));
                if(StringUtils.isNotBlank(isLiveschool)) {
                    ucenterStudent.setIsLiveschool(DictUtil.BooleanEnmu.YES.getName().equals(isLiveschool) ? DictUtil.BooleanEnmu.YES.getIndex() : DictUtil.BooleanEnmu.NO.getIndex());
                }

                //五、设置操作信息
                ucenterStudent.setCreator(UserUtil.getCurrentUser());
                ucenterStudent.setCtime(time);
                ucenterStudent.setYear(year);
                ucenterStudent.setStatus(BaseConstants.Status.NORMAL);
                ucenterStudent.setSchoolCode(ucenterSchool.getSchoolCode());

                //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环   ,重复性验证后再返回
//                if(errorMap.size()>errorNum){
//                    continue;
//                }
                //六、入库 学生信息保存，用户生成操作
                errorMap=ucenterStudentService.importUcenterStudent(ucenterStudent, ucenterSchool,errorMap,tableValid,rowNum,errorNum);
           }catch (Exception e){
               e.printStackTrace();
               _log.error("学生信息入库错误：" + e.toString());
               errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getColNum()), BaseConstants.ImportError.DATA_IMPORTEXCEPTION);
           }
            if(errorMap.size()==errorNum){
                request.getSession().setAttribute("importSuccessCount", (successCount++));//导入成功条数
            }
            request.getSession().setAttribute("importErrorCount",errorMap.size());//处理每条数据后，记录一次错误数，因为可能中途返回
        }

        request.getSession().setAttribute("importErrorCount",errorMap.size());//执行完记录一次错误数，因为中途返回，丢失错误记录数
        request.getSession().setAttribute("isFinished", true);
        return errorMap;
    }

    /**
     * 老师信息导入
     * @param datas
     * @param ucenterSchool
     * @param request
     * @param tableValid
     * @return
     */
    public Map<String, String> importUcenterTeacher(List<Map<Integer, String>> datas,UcenterSchool ucenterSchool,HttpServletRequest request,Table tableValid) {
        //初始化进度
        request.getSession().setAttribute("importTotal", datas.size());//导入数据总数
        request.getSession().setAttribute("importCount", 0);//导入数据总数
        request.getSession().setAttribute("importErrorCount", 0);//错误个数
        request.getSession().setAttribute("importSuccessCount", 0);//导入成功条数
        request.getSession().setAttribute("isFinished", false);//是否导入完成：否

        long time = System.currentTimeMillis();
        int year=DateUtil.getCurrentYear();

        //初始化字典表,多个字典表
        List<UcenterDictValues> ucenterDictValues= getDictByCode(tableValid);
        //初始化部门表
        List<UcenterDepartment> ucenterDepartments=getUcenterDepartmentList(ucenterSchool.getSchoolCode());
        //初始化班级列表
//        List<UcenterClass> ucenterClasses=getUcenterClassList(ucenterSchool.getSchoolCode());

        Map<String, String> errorMap = new HashMap<String, String>();
        int rowNum = 0;
        int successCount = 1;
        for(Map<Integer,String> data:datas){
            rowNum++;
            long errorNum=errorMap.size();
            //记录执行情况
            request.getSession().setAttribute("importCount",rowNum);//已处理行
            request.getSession().setAttribute("importErrorCount",errorNum);//处理每条数据前，记录一次错误数，因为可能中途返回
            //业务开始
            try{
                //一、不能为空验证,格式验证
                dataVerification(data, errorMap, rowNum, tableValid);
                //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环
                if(errorMap.size()>errorNum){
                    continue;
                }

                //二、给对象赋值
                UcenterTeacher ucenterTeacher= ReflectGetValue.getInstance(data, tableValid, errorMap, rowNum, UcenterTeacher.class);
                //三、字典数据转换
                setDictValueKeyBydata(ucenterDictValues,tableValid, data, errorMap, rowNum, ucenterTeacher);

                //四、特殊字段处理
                //TODO
                //性别  sex
                String sex=data.get(tableValid.getPositonBycolName("sex"));
                if(StringUtils.isNotBlank(sex)){
                    ucenterTeacher.setSex(DictUtil.sex.MAN.getName().equals(sex) ? DictUtil.sex.MAN.getIndex() : DictUtil.sex.WOMAN.getIndex());
                }
                //部门
//                UcenterDepartment ucenterDepartment= getUcenterDepartment(ucenterDepartments, ucenterTeacher.getDepartmentName());
//                if(ucenterDepartment!=null){
//                    ucenterTeacher.setDepartmentId(ucenterDepartment.getDepartmentId());
//                }else{
//                    errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("departmentName")), BaseConstants.ImportError.PROPERTY_DEPARTMENT);
//                }


                String departmentNames=data.get(tableValid.getPositonBycolName("departmentName"));
                List<UcenterDepartment> ucenterDepartmentList=null;
                if(StringUtils.isNotBlank(departmentNames)){
                    ucenterDepartmentList=getDepIdsByDepNames(ucenterDepartments, departmentNames,errorMap,rowNum,tableValid);
                }

                //班级
//                String classNames=data.get(tableValid.getPositonBycolName("className"));
                List<UcenterClass> ucenterClassList=null;
//                if(StringUtils.isNotBlank(classNames)){
//                    ucenterClassList=getClassIdsByClassNames(ucenterClasses, classNames,errorMap,rowNum,tableValid);
//                }

                //五、设置操作信息
                ucenterTeacher.setCreator(UserUtil.getCurrentUser());
                ucenterTeacher.setCtime(time);
                ucenterTeacher.setStatus(BaseConstants.Status.NORMAL);
                ucenterTeacher.setSchoolCode(ucenterSchool.getSchoolCode());

                //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环  ,重复性验证后再返回
//                if(errorMap.size()>errorNum){
//                    continue;
//                }

                //六、入库，老师信息保存，用户生成操作
                errorMap=ucenterTeacherClassService.importUcenterTeacher(ucenterTeacher,ucenterClassList,ucenterDepartmentList, ucenterSchool,errorMap, tableValid, rowNum,errorNum);
            }catch (Exception e){
                e.printStackTrace();
                _log.error("老师信息入库错误：" + e.toString());
                errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getColNum()), BaseConstants.ImportError.DATA_IMPORTEXCEPTION);
            }
            if(errorMap.size()==errorNum){
                request.getSession().setAttribute("importSuccessCount", (successCount++));//导入成功条数
            }
            request.getSession().setAttribute("importErrorCount",errorMap.size());//处理每条数据后，记录一次错误数，因为可能中途返回
        }

        request.getSession().setAttribute("importErrorCount",errorMap.size());//执行完记录一次错误数，因为中途返回，丢失错误记录数
        request.getSession().setAttribute("isFinished", true);
        return errorMap;
    }



    /**
     * 数据校验
     * @param data
     * @param errorMap
     * @param rowNum
     * @param tableValid
     */
    public void  dataVerification(Map<Integer,String> data,Map<String, String> errorMap,int rowNum,Table tableValid){
          List<Column> columns=tableValid.getColumns();//所有字段
           for (Column column:columns){
            Map<String,String> validation=column.getValidations();
            Map<String,String> errorMessages=column.getErrorMessages();
               if(validation!=null&&validation.size()>0){
                   Set<String> keys=validation.keySet();
                   Integer colNum=column.getPosition();
                   for(String key:keys){
                       String value=validation.get(key);
                       String errorMessage=null;
                       if(errorMessages!=null&&errorMessages.size()>0){
                           errorMessage=errorMessages.get(key);
                       }
                       if(BaseConstants.ValidationType.NOTEMPTY.equals(key)){//单元格为空判断
                           if (StringUtils.isBlank(data.get(colNum))){
                               errorMap.put(StringUtil.generatorXY(rowNum,colNum),errorMessage!=null?errorMessage:BaseConstants.ImportError.PROPERTY_REQUIRED);
                               continue;
                           }
                       }else if(BaseConstants.ValidationType.EQUALLENGTH.equals(key)){//等于长度(字符)
                           if(StringUtils.isNotBlank(data.get(colNum))){
                               if(data.get(colNum).length()!=Integer.valueOf(value)){
                                   errorMap.put(StringUtil.generatorXY(rowNum,colNum), errorMessage!=null?errorMessage:BaseConstants.ImportError.PROPERTY_LENGTH+"，不等于"+value);
                               }
                           }
                       }else if(BaseConstants.ValidationType.MAXLENGTH.equals(key)){//最大长度(字符)
                           if(StringUtils.isNotBlank(data.get(colNum))){
                               if(data.get(colNum).length()>=Integer.valueOf(value)){
                                   errorMap.put(StringUtil.generatorXY(rowNum,colNum), errorMessage!=null?errorMessage:BaseConstants.ImportError.PROPERTY_LENGTH+"，不能大于"+value);
                               }
                           }
                       }else if(BaseConstants.ValidationType.MINLENGTH.equals(key)){//最小长度(字符)
                           if(StringUtils.isNotBlank(data.get(colNum))){
                               if(data.get(colNum).length()<=Integer.valueOf(value)){
                                   errorMap.put(StringUtil.generatorXY(rowNum,colNum), errorMessage!=null?errorMessage:BaseConstants.ImportError.PROPERTY_LENGTH+"，不能小于"+value);
                               }
                           }
                       }else if(BaseConstants.ValidationType.REGEX.equals(key)){//正则匹配
                           if(StringUtils.isNotBlank(data.get(colNum))){
                               Pattern p = Pattern.compile(value) ;    // 实例化Pattern类
                               Matcher m = p.matcher(data.get(colNum)) ;    // 实例化Matcher类
                               if(!m.matches()){        // 进行验证的匹配，使用正则
                                   errorMap.put(StringUtil.generatorXY(rowNum, colNum), errorMessage!=null?errorMessage:BaseConstants.ImportError.DATA_FORMAT);
                               }
                           }
                       }else{
                           _log.warn("不支持的验证:"+key+":"+value);
                       }
                   }
               }
        }

    }

    /**
     * 通过 Table，查询需要的字典列表
     * @param tableValid
     * @return
     */
    public List<UcenterDictValues> getDictByCode(Table tableValid){
        // 将Map value 转化为List
        UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
        ucenterDictValuesExample.createCriteria().andDictCodeIn(tableValid.getDictCodes()).andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<UcenterDictValues> ucenterDictValues =ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
        return ucenterDictValues;
    }

    /**
     * 通过字典,字典值查询值key
     * @param ucenterDictValues
     * @param dictCode
     * @param value
     * @return
     */
    public UcenterDictValues getkeyByValue(List<UcenterDictValues> ucenterDictValues,String dictCode,String value){
        UcenterDictValues dictValue=null;
        if(ucenterDictValues.size()>0){
            for(int i=0;i<ucenterDictValues.size();i++){
                if(ucenterDictValues.get(i).getValueName().equals(value)&&ucenterDictValues.get(i).getDictCode().equals(dictCode)){
                    dictValue=ucenterDictValues.get(i);
                    break;
                }
            }
        }
        return dictValue;
    }

    /**
     * 字典转换
     *  @param ucenterDictValues
     * @param tableValid
     * @param data
     * @param errorMap
     * @param rowNum
     * @param obj
     */
    public void setDictValueKeyBydata(List<UcenterDictValues> ucenterDictValues,Table tableValid,Map<Integer,String> data,Map<String, String> errorMap,int rowNum,Object obj){
        List<Column> columns=tableValid.getColumnsByColClass(BaseConstants.colClass.COL_DICTTYPE);//字典字段
        if(columns.size()>0&&null!=obj){
            Field[] fields =  obj.getClass().getDeclaredFields();
            Method[] methods =  obj.getClass().getDeclaredMethods();

            for(Field field:fields){
                Integer colnum=null;
                String dictCode=null;
                for(Column column:columns){
                    if(field.getName().equals(column.getName())){
                        colnum=column.getPosition();
                        dictCode=column.getDictCode();
                        break;
                    }
                }
                if(colnum!=null){
                    String value=data.get(colnum);
                    if(StringUtils.isNotBlank(value)){
                        UcenterDictValues dictValue=getkeyByValue(ucenterDictValues, dictCode, value);
                        if(dictValue!=null&&dictValue.getValueKey()!=null){
                            // 属性set方法名
                            String fieldSetName = ReflectGetValue.setMethodName(field.getName());
                            if (ReflectGetValue.checkSetMethod(methods, fieldSetName)) {
                                Method fieldSetMet = null;// set方法
                                try {
                                    fieldSetMet = obj.getClass().getMethod(fieldSetName, field.getType());
                                    if (ReflectGetValue.validNullAndEmptyString(dictValue.getValueKey())) {
                                        String fieldType = field.getType().getSimpleName();
                                        if ("String".equals(fieldType)) {//字典key都是String
                                            fieldSetMet.invoke(obj, dictValue.getValueKey());
                                        } else {
                                            _log.warn("not supper type" + fieldType);
                                            errorMap.put(StringUtil.generatorXY(rowNum, colnum), "不支持数据类型:"+fieldType);
                                        }
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                    errorMap.put(StringUtil.generatorXY(rowNum,colnum), "字典转换异常");
                                }
                            } else {
                                _log.warn("not this set method : " + fieldSetName);
                                errorMap.put(StringUtil.generatorXY(rowNum, colnum), "没有set方法:"+fieldSetName);
                            }
                        }else{
                            errorMap.put(StringUtil.generatorXY(rowNum,colnum), BaseConstants.ImportError.DICT_ERROR);
                        }
                    }
                } else {
//                    _log.info("dict do not need to be dealt with this field : " + field.getName());
                }
            }
        }
    }
    /**
     * 查询一个学校的所有院系信息
     * @param schoolCode
     * @return
     */
    public List<UcenterFaculty> getUcenterFacultyList(String schoolCode){
        UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
        UcenterFacultyExample.Criteria criteria=ucenterFacultyExample.createCriteria();
        criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
        return ucenterFacultyService.selectByExample(ucenterFacultyExample);
    }

    /**
     * 查询一个学校的所有专业信息
     * @param schoolCode
     * @return
     */
    public List<UcenterSpecialty> getUcenterSpecialtyList(String schoolCode){
        UcenterSpecialtyExample ucenterSpecialtyExample= new UcenterSpecialtyExample();
        UcenterSpecialtyExample.Criteria criteria=ucenterSpecialtyExample.createCriteria();
        criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
        return ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);
    }
    /**
     * 查询一个学校的所有班级信息
     * @param schoolCode
     * @return
     */
    public List<UcenterClass> getUcenterClassList(String schoolCode){
        UcenterClassExample ucenterClassExample = new UcenterClassExample();
        UcenterClassExample.Criteria criteria = ucenterClassExample.createCriteria();
        criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
       return ucenterClassService.selectByExample(ucenterClassExample);
    }

    /**
     * 院系列表中，查询指定的院系
     * @param ucenterFaculties
     * @param facultyName
     * @param enterYear
     * @return
     */
    public UcenterFaculty getOneUcenterFaculty(List<UcenterFaculty> ucenterFaculties,String facultyName,Integer enterYear){
        UcenterFaculty ucenterFaculty=null;
        if(ucenterFaculties!=null&&ucenterFaculties.size()>0&&enterYear!=null){
            for (int i=0;i<ucenterFaculties.size();i++){
//                if(ucenterFaculties.get(i).getYear().intValue()==enterYear.intValue()
                if(ucenterFaculties.get(i).getFacultyName().equals(facultyName)){
                    ucenterFaculty = ucenterFaculties.get(i);
                    break;
                }
            }
        }
        return ucenterFaculty;
    }

    /**
     * 专业列表中，查询指定的专业
     * @param ucenterSpecialties
     * @param facultyName
     * @param specialtyName
     * @param enterYear
     * @return
     */
    public UcenterSpecialty getOneUcenterSpecialty(List<UcenterSpecialty> ucenterSpecialties,String facultyName,String specialtyName,Integer enterYear){
        UcenterSpecialty ucenterSpecialty=null;
        if(ucenterSpecialties!=null&&ucenterSpecialties.size()>0&&enterYear!=null){
            for (int i=0;i<ucenterSpecialties.size();i++){
//                if(ucenterSpecialties.get(i).getYear().intValue()==enterYear.intValue()
                if(ucenterSpecialties.get(i).getFacultyName().equals(facultyName)
                        &&ucenterSpecialties.get(i).getSpecialtyName().equals(specialtyName)){
                    ucenterSpecialty = ucenterSpecialties.get(i);
                    break;
                }
            }
        }

        return ucenterSpecialty;
    }

    /**
     * 班级列表中，查询出指定的班级
     * @param ucenterClasses
     * @param facultyName
     * @param specialtyName
     * @param className
     * @param enterYear
     * @return
     */
    public UcenterClass getOneUcenterClass(List<UcenterClass> ucenterClasses,String facultyName,String specialtyName,String className,Integer enterYear){
        UcenterClass ucenterClass=null;
        if(ucenterClasses!=null&&ucenterClasses.size()>0&&enterYear!=null){
            for (int i=0;i<ucenterClasses.size();i++){
//                if(ucenterClasses.get(i).getYear().intValue()==enterYear.intValue()
                if(ucenterClasses.get(i).getFacultyName().equals(facultyName)
                        &&ucenterClasses.get(i).getSpecialtyName().equals(specialtyName)
                        &&ucenterClasses.get(i).getClassName().equals(className)){
                    ucenterClass = ucenterClasses.get(i);
                    break;
                }
            }
        }
        return ucenterClass;
    }

    /**
     * 查询一个学校的部门列表
     * @param schoolCode
     * @return
     */
    public List<UcenterDepartment> getUcenterDepartmentList(String schoolCode){
        UcenterDepartmentExample ucenterDepartmentExample=new UcenterDepartmentExample();
        ucenterDepartmentExample.createCriteria().andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
        return ucenterDepartmentService.selectByExample(ucenterDepartmentExample);
    }

    /**
     * 在部门列表中，查找部门信息
     * @param ucenterDepartments
     * @param departmentName
     * @return
     */
    public UcenterDepartment getUcenterDepartment(List<UcenterDepartment> ucenterDepartments,String departmentName){
        UcenterDepartment ucenterDepartment=null;
        if(ucenterDepartments!=null&&ucenterDepartments.size()>0&&departmentName!=null){
            for(int i=0;i<ucenterDepartments.size();i++){
                if(ucenterDepartments.get(i).getDepartmentName().equals(departmentName)){
                    ucenterDepartment=ucenterDepartments.get(i);
                    break;
                }
            }
        }
        return ucenterDepartment;
    }

    /**
     * 班级列表中，查询出指定的班级,
     * @param ucenterClasses
     * @param classNames
     * @return
     */
    public List<UcenterClass> getClassIdsByClassNames(List<UcenterClass> ucenterClasses,String classNames,Map<String, String> errorMap,int rowNum,Table tableValid){
        List<UcenterClass> ucenterClassList=new ArrayList<>();
        if(ucenterClasses!=null&&ucenterClasses.size()>0&&classNames!=null){
            String[] className=classNames.split(",");
            StringBuffer stringBuffer=new StringBuffer();
            for (int i=0;i<className.length;i++){
                UcenterClass ucenterClass=null;
                for (int j=0;j<ucenterClasses.size();j++){
                    if(className[i].trim().equals(ucenterClasses.get(j).getClassName())){
                        ucenterClass = ucenterClasses.get(j);
                    }
                }
                if(ucenterClass!=null){
                    ucenterClassList.add(ucenterClass);
                }else{
                    stringBuffer.append(","+className[i]);
                }
            }
            if(stringBuffer!=null&&stringBuffer.length()>0){
                errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("className")), "学校没有对应班级:"+stringBuffer.toString().substring(1));
            }
        }
        return ucenterClassList;
    }


    public List<UcenterDepartment> getDepIdsByDepNames(List<UcenterDepartment> ucenterDepartments,String departmentNames,Map<String, String> errorMap,int rowNum,Table tableValid){
        List<UcenterDepartment> ucenterDepartmentList=new ArrayList<>();
        if(ucenterDepartments!=null&&ucenterDepartments.size()>0&&departmentNames!=null){
            String departmentName[]=departmentNames.split(",");
            StringBuffer stringBuffer=new StringBuffer();
            for (int i=0;i<departmentName.length;i++){
                UcenterDepartment ucenterDepartment=null;
                for (int j=0;j<ucenterDepartments.size();j++){
                    if(departmentName[i].trim().equals(ucenterDepartments.get(j).getDepartmentName())){
                        ucenterDepartment = ucenterDepartments.get(j);
                    }
                }
                if(ucenterDepartment!=null){
                    ucenterDepartmentList.add(ucenterDepartment);
                }else{
                    stringBuffer.append(","+departmentName[i]);
                }
            }
            if(stringBuffer!=null&&stringBuffer.length()>0){
                errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("departmentName")), "学校没有对应部门:"+stringBuffer.toString().substring(1));
            }
        }
        return ucenterDepartmentList;
    }

}

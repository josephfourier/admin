package com.thinkjoy.enrollment.admin.controller.upload.service;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.util.*;
import com.thinkjoy.common.util.xmlutil.Column;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.enrollment.dao.model.*;
import com.thinkjoy.enrollment.rpc.api.EnrollBatchService;
import com.thinkjoy.enrollment.rpc.api.EnrollStudentService;
import com.thinkjoy.enrollment.rpc.api.EnrollTeacherService;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xufei
 * @date 2017/11/30
 */
@Component
public class UploadService {
    private static Logger _log = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private UcenterDictValuesService ucenterDictValuesService;
    @Autowired
    private UcenterFacultyService ucenterFacultyService;
    @Autowired
    private UcenterSpecialtyService ucenterSpecialtyService;
    @Autowired
    private UcenterClassService ucenterClassService;
    @Autowired
    private UcenterDepartmentService ucenterDepartmentService;
    @Autowired
    private UcenterAreaService ucenterAreaService;
    @Autowired
    private EnrollBatchService enrollBatchService;
    @Autowired
    private EnrollStudentService enrollStudentService;
    @Autowired
    private EnrollTeacherService enrollTeacherService;

    /**
     * 招生管理--学生信息导入
     * <p/>
     * 规则要求：导入学生数据，手机号作为账号，密码为身份证后六位。编辑学生信息时候－帐号表信息与身份表信息同步
     * 数据分类：1、普通字段，2、字典字段，3、特殊字段
     * 一、不能为空验证 ，长度验证，格式验证
     * 1-不为空验证（notEmpty），2-(字符)长度校验（equalLength（等于），maxLength(最大)，minLength(最小)），3-格式校验（regEx（正则））
     * 二、普通字段值:通过反射机制实现给对象赋值，初始化
     * 三、字典字段:标记需要字典转换的字段,统一处理
     * 四、特殊字段:单独处理处理
     * 五、设置操作信息
     * 六、入库
     *
     * @param datas
     * @param ucenterSchool
     * @return
     */
    public Map<String, String> importEnrollStudents(List<Map<Integer, String>> datas, UcenterSchool ucenterSchool, HttpServletRequest request, Table tableValid) {
        //初始化进度
        request.getSession().setAttribute("importTotal", datas.size());//导入数据总数
        request.getSession().setAttribute("importCount", 0);//导入数据总数
        request.getSession().setAttribute("importErrorCount", 0);//错误个数
        request.getSession().setAttribute("importSuccessCount", 0);//导入成功条数
        request.getSession().setAttribute("isFinished", false);//是否导入完成：否

        long time = System.currentTimeMillis();
        int year = DateUtil.getCurrentYear();

        //初始化字典表,多个字典表
        List<UcenterDictValues> ucenterDictValues = getDictByCode(tableValid);
        //初始化专业列表
        List<UcenterSpecialty> ucenterSpecialties = getUcenterSpecialtyList(ucenterSchool.getSchoolCode());
        //初始化行政区划
        ImmutableListMultimap<String, UcenterArea> ucenterAreaMap = getUcenterAreaMap();
        //初始化招生老师
        ImmutableListMultimap<String, EnrollTeacher> enrollTeacherMap = getEnrollTeacherMap(ucenterSchool.getSchoolCode());
        //初始化招生批次
        ImmutableListMultimap<String, EnrollBatch> enrollBatchMap = getEnrollBatchMap(ucenterSchool.getSchoolCode());

        Map<String, String> errorMap = new HashMap<String, String>();
        int rowNum = 0;
        int successCount = 1;
        for (Map<Integer, String> data : datas) {
            rowNum++;
            long errorNum = errorMap.size();
            //记录执行情况
            request.getSession().setAttribute("importCount", rowNum);//已处理行
            request.getSession().setAttribute("importErrorCount", errorNum);//处理每条数据前，记录一次错误数，因为可能中途返回
            //业务开始
            try {
                //一、不能为空验证,长度验证，格式验证
                dataVerification(data, errorMap, rowNum, tableValid);
                //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环
                if (errorMap.size() > errorNum) {
                    continue;
                }
                //二、给对象赋值
                EnrollStudent enrollStudent = ReflectGetValue.getInstance(data, tableValid, errorMap, rowNum, EnrollStudent.class);
                //三、字典数据转换
                setDictValueKeyBydata(ucenterDictValues, tableValid, data, errorMap, rowNum, enrollStudent);

                // 四、特殊字段处理
                //①籍贯,户口所在地,生源地,通讯地址(以"-"进行分隔)
                convert(ucenterAreaMap, tableValid, data, errorMap, rowNum, enrollStudent);

                //②第一志愿,第二志愿,录取专业
                //第一志愿
                if (StringUtils.isNotBlank(enrollStudent.getFirstVol()) && enrollStudent.getBatchYear() != null) {
                    UcenterSpecialty ucenterSpecialty = getOneUcenterSpecialty(ucenterSpecialties, enrollStudent.getFirstVol());
                    if (ucenterSpecialty != null) {
                        //第一志愿校内编码
                        if (StringUtils.isNotBlank(ucenterSpecialty.getSchoolCode())) {
                            enrollStudent.setFirstVolcode(ucenterSpecialty.getSpecialtyCode());
                        } else {
                            errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("firstVol")), BaseConstants.ImportError.PROPERTY_SPECIALTYCODE);
                        }

                        if (StringUtils.isNotBlank(ucenterSpecialty.getTrdrName())) {
                            //培养方向
                            enrollStudent.setFirstTrdrname(ucenterSpecialty.getTrdrName());
                        } else {
                            errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("firstVol")), BaseConstants.ImportError.PROPERTY_SPECIALTY_TRDRNAME);
                        }

                        //第一志愿学制是字典的值名称
                        UcenterDictValues dict_xz = getValueNameByValueKsy(ucenterDictValues, BaseConstants.Dict.XZ, ucenterSpecialty.getSchoolSystem());
                        if (dict_xz != null) {
                            enrollStudent.setFirstSchsys(dict_xz.getValueName());
                        } else {
                            errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("firstVol")), BaseConstants.ImportError.PROPERTY_SPECIALTY_SCHSYS);
                        }

                        if (StringUtils.isNotBlank(ucenterSpecialty.getFacultyCode())) {
                            //院系编码
                            enrollStudent.setFirstFacultycode(ucenterSpecialty.getFacultyCode());
                        } else {
                            errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("firstVol")), BaseConstants.ImportError.PROPERTY_SPECIALTY_FACULTYCODE);
                        }

                        if (StringUtils.isNotBlank(ucenterSpecialty.getFacultyName())) {
                            //院系名称
                            enrollStudent.setFirstFacultyname(ucenterSpecialty.getFacultyName());
                        } else {
                            errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("firstVol")), BaseConstants.ImportError.PROPERTY_SPECIALTY_FACULTYNAME);
                        }

                        //录取专业设置
                        if (StringUtils.isNotBlank(enrollStudent.getEnrollVol())) {
                            if (enrollStudent.getEnrollVol().equals("1")){
                                setEnrollVol(enrollStudent, ucenterSpecialty);
                            }else {
                                if (StringUtils.isBlank(enrollStudent.getSecondVol())){
                                    setEnrollVol(enrollStudent, ucenterSpecialty);
                                }
                            }
                        }else {
                            setEnrollVol(enrollStudent, ucenterSpecialty);
                        }
                    } else {
                        errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("firstVol")), BaseConstants.ImportError.PROPERTY_SPECIALTYNAME);
                    }
                }
                //第二志愿
                if (StringUtils.isNotBlank(enrollStudent.getSecondVol()) && enrollStudent.getBatchYear() != null) {
                    UcenterSpecialty ucenterSpecialty = getOneUcenterSpecialty(ucenterSpecialties, enrollStudent.getSecondVol());
                    if (ucenterSpecialty != null) {
                        //第二志愿校内编码
                        enrollStudent.setSecondVolcode(ucenterSpecialty.getSpecialtyCode());
                        //培养方向
                        enrollStudent.setSecondTrdrname(ucenterSpecialty.getTrdrName());
                        //第二志愿学制是字典的值名称
                        UcenterDictValues dict_xz = getValueNameByValueKsy(ucenterDictValues, BaseConstants.Dict.XZ, ucenterSpecialty.getSchoolSystem());
                        if (dict_xz != null) {
                            enrollStudent.setSecondSchsys(dict_xz.getValueName());
                        }
                        //院系编码
                        enrollStudent.setSecondFacultycode(ucenterSpecialty.getFacultyCode());
                        //院系名称
                        enrollStudent.setSecondFacultname(ucenterSpecialty.getFacultyName());
                        //录取专业设置
                        if (StringUtils.isNotBlank(enrollStudent.getEnrollVol())) {
                            if (enrollStudent.getEnrollVol().equals("2")){
                                setEnrollVol(enrollStudent, ucenterSpecialty);
                            }
                        }
                    } else {
                        errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("secondVol")), BaseConstants.ImportError.PROPERTY_SPECIALTYNAME);
                    }
                }

                //③相关id或code设置:招生批次名称,招生人员名称
                //招生批次
                if (StringUtils.isNotBlank(enrollStudent.getBatchName())) {
                    EnrollBatch enrollBatch = null;
                    if (enrollBatchMap != null) {
                        //招生批次名称可能两年重复
                        ImmutableList<EnrollBatch> enrollBatches = enrollBatchMap.get(enrollStudent.getBatchName());
                        for (EnrollBatch batch : enrollBatches) {
                            if (batch.getBatchYear().equals(enrollStudent.getBatchYear())) {
                                enrollBatch = batch;
                            }
                        }
                    }
                    if (enrollBatch != null) {
                        enrollStudent.setBatchId(enrollBatch.getBatchId());
                    } else {
                        errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("batchName")), BaseConstants.ImportError.PROPERTY_BATCHNAME);
                    }
                }
                //招生人员
                if (StringUtils.isNotBlank(enrollStudent.getTeacherName())) {
                    EnrollTeacher enrollTeacher = null;
                    if (enrollTeacherMap != null) {
                        //这里某个招生人员可能负责多个招生批次
                        ImmutableList<EnrollTeacher> enrollTeachers = enrollTeacherMap.get(enrollStudent.getTeacherName());
                        for (EnrollTeacher teacher : enrollTeachers) {
                            if (teacher.getBatchId().equals(enrollStudent.getBatchId())) {
                                enrollTeacher = teacher;
                            }
                        }
                    }
                    if (enrollTeacher != null) {
                        enrollStudent.setEnrollteacherId(enrollTeacher.getEnrollteacherId());
                    } else {
                        errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("teacherName")), BaseConstants.ImportError.PROPERTY_TEACHERNAME);
                    }
                }

                //五、设置操作信息
                //缴费信息
                enrollStudent.setFeeStatus(BaseConstants.FeeStatus.WJF);
                //录取设置
                enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.YXWSY);
                enrollStudent.setCreator(UserUtil.getCurrentUser());
                enrollStudent.setCtime(time);
                enrollStudent.setYear(year);
                enrollStudent.setStatus(BaseConstants.Status.NORMAL);
                enrollStudent.setSchoolCode(ucenterSchool.getSchoolCode());

                //六、入库 学生信息保存，用户生成操作
                errorMap = enrollStudentService.importEnrollStudent(enrollStudent, errorMap, tableValid, rowNum, errorNum);
            } catch (Exception e) {
                e.printStackTrace();
                _log.error("学生信息入库错误：" + e.toString());
                errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getColNum()), BaseConstants.ImportError.DATA_IMPORTEXCEPTION);
            }
            if (errorMap.size() == errorNum) {
                request.getSession().setAttribute("importSuccessCount", (successCount++));//导入成功条数
            }
            request.getSession().setAttribute("importErrorCount", errorMap.size());//处理每条数据后，记录一次错误数，因为可能中途返回
        }

        request.getSession().setAttribute("importErrorCount", errorMap.size());//执行完记录一次错误数，因为中途返回，丢失错误记录数
        request.getSession().setAttribute("isFinished", true);
        return errorMap;
    }


    /**
     * 设置录取专业,根据录取志愿字段设置不同的志愿
     * @param enrollStudent
     * @param ucenterSpecialty
     */
    public void setEnrollVol(EnrollStudent enrollStudent, UcenterSpecialty ucenterSpecialty) {
        //录取专业默认是第一专业
        enrollStudent.setSpecialtyCode(ucenterSpecialty.getSpecialtyCode());
        enrollStudent.setSpecialtyName(ucenterSpecialty.getSpecialtyName());
        //培养方向
        enrollStudent.setTrdrName(ucenterSpecialty.getTrdrName());
        //录取学制默认是字典编码
        enrollStudent.setSchoolSystem(ucenterSpecialty.getSchoolSystem());
        //院系编码
        enrollStudent.setFacultyCode(ucenterSpecialty.getFacultyCode());
        //院系名称
        enrollStudent.setFacultyName(ucenterSpecialty.getFacultyName());
    }


    /**
     * 数据校验
     *
     * @param data
     * @param errorMap
     * @param rowNum
     * @param tableValid
     */
    public void dataVerification(Map<Integer, String> data, Map<String, String> errorMap, int rowNum, Table tableValid) {
        List<Column> columns = tableValid.getColumns();//所有字段
        for (Column column : columns) {
            Map<String, String> validation = column.getValidations();
            Map<String, String> errorMessages = column.getErrorMessages();
            if (validation != null && validation.size() > 0) {
                Set<String> keys = validation.keySet();
                Integer colNum = column.getPosition();
                for (String key : keys) {
                    String value = validation.get(key);
                    String errorMessage = null;
                    if (errorMessages != null && errorMessages.size() > 0) {
                        errorMessage = errorMessages.get(key);
                    }
                    if (BaseConstants.ValidationType.NOTEMPTY.equals(key)) {//单元格为空判断
                        if (StringUtils.isBlank(data.get(colNum))) {
                            errorMap.put(StringUtil.generatorXY(rowNum, colNum), errorMessage != null ? errorMessage : BaseConstants.ImportError.PROPERTY_REQUIRED);
                            continue;
                        }
                    } else if (BaseConstants.ValidationType.EQUALLENGTH.equals(key)) {//等于长度(字符)
                        if (StringUtils.isNotBlank(data.get(colNum))) {
                            if (data.get(colNum).length() != Integer.valueOf(value)) {
                                errorMap.put(StringUtil.generatorXY(rowNum, colNum), errorMessage != null ? errorMessage : BaseConstants.ImportError.PROPERTY_LENGTH + "，不等于" + value);
                            }
                        }
                    } else if (BaseConstants.ValidationType.MAXLENGTH.equals(key)) {//最大长度(字符)
                        if (StringUtils.isNotBlank(data.get(colNum))) {
                            if (data.get(colNum).length() >= Integer.valueOf(value)) {
                                errorMap.put(StringUtil.generatorXY(rowNum, colNum), errorMessage != null ? errorMessage : BaseConstants.ImportError.PROPERTY_LENGTH + "，不能大于" + value);
                            }
                        }
                    } else if (BaseConstants.ValidationType.MINLENGTH.equals(key)) {//最小长度(字符)
                        if (StringUtils.isNotBlank(data.get(colNum))) {
                            if (data.get(colNum).length() <= Integer.valueOf(value)) {
                                errorMap.put(StringUtil.generatorXY(rowNum, colNum), errorMessage != null ? errorMessage : BaseConstants.ImportError.PROPERTY_LENGTH + "，不能小于" + value);
                            }
                        }
                    } else if (BaseConstants.ValidationType.REGEX.equals(key)) {//正则匹配
                        if (StringUtils.isNotBlank(data.get(colNum))) {
                            Pattern p = Pattern.compile(value);    // 实例化Pattern类
                            Matcher m = p.matcher(data.get(colNum));    // 实例化Matcher类
                            if (!m.matches()) {        // 进行验证的匹配，使用正则
                                errorMap.put(StringUtil.generatorXY(rowNum, colNum), errorMessage != null ? errorMessage : BaseConstants.ImportError.DATA_FORMAT);
                            }
                        }
                    } else {
                        _log.warn("不支持的验证:" + key + ":" + value);
                    }
                }
            }
        }

    }

    /**
     * 通过 Table，查询需要的字典列表
     *
     * @param tableValid
     * @return
     */
    public List<UcenterDictValues> getDictByCode(Table tableValid) {
        // 将Map value 转化为List
        List<String> dictCodes = tableValid.getDictCodes();
        addOtherDict(dictCodes);
        UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
        ucenterDictValuesExample.createCriteria().andDictCodeIn(dictCodes).andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<UcenterDictValues> ucenterDictValues = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
        return ucenterDictValues;
    }


    /**
     * 增加xml中没有的字典
     *
     * @param dictCodes
     */
    private void addOtherDict(List<String> dictCodes) {
        if (CollectionUtils.isNotEmpty(dictCodes)) {
            dictCodes.add("DICT_XZ");
        }
    }

    /**
     * 通过字典,字典值查询值key
     *
     * @param ucenterDictValues
     * @param dictCode
     * @param value
     * @return
     */
    public UcenterDictValues getkeyByValue(List<UcenterDictValues> ucenterDictValues, String dictCode, String value) {
        UcenterDictValues dictValue = null;
        if (ucenterDictValues.size() > 0) {
            for (int i = 0; i < ucenterDictValues.size(); i++) {
                if (ucenterDictValues.get(i).getValueName().equals(value) && ucenterDictValues.get(i).getDictCode().equals(dictCode)) {
                    dictValue = ucenterDictValues.get(i);
                    break;
                }
            }
        }
        return dictValue;
    }

    /**
     * 通过字典,字典值查询值key
     *
     * @param ucenterDictValues
     * @param dictCode
     * @param valueKey
     * @return
     */
    public UcenterDictValues getValueNameByValueKsy(List<UcenterDictValues> ucenterDictValues, String dictCode, String valueKey) {
        UcenterDictValues dictValue = null;
        if (ucenterDictValues.size() > 0) {
            for (int i = 0; i < ucenterDictValues.size(); i++) {
                if (ucenterDictValues.get(i).getValueKey().equals(valueKey) && ucenterDictValues.get(i).getDictCode().equals(dictCode)) {
                    dictValue = ucenterDictValues.get(i);
                    break;
                }
            }
        }
        return dictValue;
    }

    /**
     * @param ucenterAreaMap
     * @param tableValid
     * @param data
     * @param errorMap
     * @param rowNum
     * @param obj
     */
    public void convert(ImmutableListMultimap<String, UcenterArea> ucenterAreaMap,
                        Table tableValid,
                        Map<Integer, String> data,
                        Map<String, String> errorMap,
                        int rowNum,
                        Object obj) {
        try {
            List<Column> assemble = tableValid.getColumnsByColClass(BaseConstants.colClass.COL_ASSEMBLETYPE);
            if (CollectionUtils.isNotEmpty(assemble) && CollectionUtils.isNotEmpty(assemble) && obj != null) {
                //自省操作javabean
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

                for (Column column : assemble) {
                    for (PropertyDescriptor p : propertyDescriptors) {
                        if (p.getName().equals(column.getName())) {
                            doConvertLocation(p, ucenterAreaMap, column, data, errorMap, rowNum, obj);
                        }
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
            errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getColNum()), "系统异常");
        }
    }

    /**
     * 招生学生地址信息解析
     *
     * @param p
     * @param ucenterAreaMap
     * @param column
     * @param data
     * @param errorMap
     * @param rowNum
     * @param obj
     */
    public void doConvertLocation(PropertyDescriptor p,
                                  ImmutableListMultimap<String, UcenterArea> ucenterAreaMap,
                                  Column column,
                                  Map<Integer, String> data,
                                  Map<String, String> errorMap,
                                  int rowNum,
                                  Object obj) {
        try {
            Iterable<String> split = Splitter.on("-").trimResults().split(data.get(column.getPosition()));
            Iterator<String> iterator = split.iterator();
            ArrayList arrayList = new ArrayList();

            while (iterator.hasNext()) {
                ImmutableList<UcenterArea> ucenterArea = ucenterAreaMap.get(iterator.next());
                if (CollectionUtils.isNotEmpty(ucenterArea)) {
                    String areaCode = ucenterArea.get(0).getAreaCode();//县/市辖区/省直辖行政单位为重复名称,默认取第一个
                    arrayList.add(areaCode);
                } else {
                    errorMap.put(StringUtil.generatorXY(rowNum, column.getPosition()), "无此行政区划信息");
                }
            }
            String join = Joiner.on(',').join(arrayList);
            p.getWriteMethod().invoke(obj, join);
        } catch (Exception e) {
            e.printStackTrace();
            errorMap.put(StringUtil.generatorXY(rowNum, column.getPosition()), "学生地址相关信息转换异常");
        }

    }

    /**
     * 获取该学校的招生老师
     *
     * @param schoolCode
     * @return
     */
    public ImmutableListMultimap<String, EnrollTeacher> getEnrollTeacherMap(String schoolCode) {
        EnrollTeacherExample enrollTeacherExample = new EnrollTeacherExample();
        enrollTeacherExample.createCriteria()
                .andSchoolCodeEqualTo(schoolCode)
                .andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<EnrollTeacher> enrollTeachers = enrollTeacherService.selectByExample(enrollTeacherExample);

        return list2map(enrollTeachers, EnrollTeacher.class, "teacherName");
    }

    /**
     * 获取该学校的批次
     *
     * @param schoolCode
     * @return
     */
    public ImmutableListMultimap<String, EnrollBatch> getEnrollBatchMap(String schoolCode) {
        EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
        enrollBatchExample.createCriteria()
                .andSchoolCodeEqualTo(schoolCode)
                .andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<EnrollBatch> enrollBatches = enrollBatchService.selectByExample(enrollBatchExample);
        return list2map(enrollBatches, EnrollBatch.class, "batchName");
    }

    /**
     * 行政区划list转map,key可重复
     *
     * @param list
     * @return
     */
    public ImmutableListMultimap<String, UcenterArea> list2map(List<UcenterArea> list) {
        ImmutableListMultimap<String, UcenterArea> list2map = null;
        if (CollectionUtils.isNotEmpty(list)) {
            list2map = Multimaps.index(list, new Function<UcenterArea, String>() {
                @Override
                public String apply(UcenterArea ucenterArea) {
                    return ucenterArea.getAreaName();
                }
            });
        }
        return list2map;
    }

    /**
     * list转uniquemap,key唯一
     *
     * @param list
     * @return
     */
    public <T> ImmutableMap<String, T> list2umap(List<T> list, final Class<T> cls, String pro) {

        ImmutableMap<String, T> list2umap = null;
        try {
            final Method methodByPro = getMethodByPro(cls, pro);

            if (CollectionUtils.isNotEmpty(list) && methodByPro != null) {
                list2umap = Maps.uniqueIndex(list, new Function<T, String>() {
                    @Override
                    public String apply(T obj) {
                        try {
                            return (String) methodByPro.invoke(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
            }

        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list2umap;
    }

    /**
     * list转map,key可重复
     *
     * @param list
     * @return
     */
    public <T> ImmutableListMultimap<String, T> list2map(List<T> list, final Class<T> cls, String pro) {

        ImmutableListMultimap<String, T> list2umap = null;
        try {
            final Method methodByPro = getMethodByPro(cls, pro);

            if (CollectionUtils.isNotEmpty(list) && methodByPro != null) {
                list2umap = Multimaps.index(list, new Function<T, String>() {
                    @Override
                    public String apply(T obj) {
                        try {
                            return (String) methodByPro.invoke(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list2umap;
    }


    public Method getMethodByPro(Class cls, String pro) throws IntrospectionException {
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(cls).getPropertyDescriptors();
        for (PropertyDescriptor p : propertyDescriptors) {
            if (p.getName().equals(pro)) {
                return p.getReadMethod();
            }
        }
        return null;
    }


    /**
     * 字典转换
     *
     * @param ucenterDictValues
     * @param tableValid
     * @param data
     * @param errorMap
     * @param rowNum
     * @param obj
     */
    public void setDictValueKeyBydata(List<UcenterDictValues> ucenterDictValues, Table tableValid, Map<Integer, String> data, Map<String, String> errorMap, int rowNum, Object obj) {
        List<Column> columns = tableValid.getColumnsByColClass(BaseConstants.colClass.COL_DICTTYPE);//字典字段
        if (columns.size() > 0 && null != obj) {
            Field[] fields = obj.getClass().getDeclaredFields();
            Method[] methods = obj.getClass().getDeclaredMethods();

            for (Field field : fields) {
                Integer colnum = null;
                String dictCode = null;
                for (Column column : columns) {
                    if (field.getName().equals(column.getName())) {
                        colnum = column.getPosition();
                        dictCode = column.getDictCode();
                        break;
                    }
                }
                if (colnum != null) {
                    String value = data.get(colnum);
                    if (StringUtils.isNotBlank(value)) {
                        UcenterDictValues dictValue = getkeyByValue(ucenterDictValues, dictCode, value);
                        if (dictValue != null && dictValue.getValueKey() != null) {
                            // 属性set方法名
                            String fieldSetName = ReflectGetValue.setMethodName(field.getName());
                            if (ReflectGetValue.checkSetMethod(methods, fieldSetName)) {
                                Method fieldSetMet = null;// set方法
                                try {
                                    fieldSetMet = obj.getClass().getMethod(fieldSetName, field.getType());
                                    if (ReflectGetValue.validNullAndEmptyString(dictValue.getValueKey())) {
                                        String fieldType = field.getType().getSimpleName();
                                        if ("String".equals(fieldType)) {//仅招生中修改的逻辑:字典key可以是String或者Boolean
                                            fieldSetMet.invoke(obj, dictValue.getValueKey());
                                        } else if ("Boolean".equals(fieldType)) {
                                            fieldSetMet.invoke(obj, coverBoolean(value));
                                        } else {
                                            _log.warn("not supper type" + fieldType);
                                            errorMap.put(StringUtil.generatorXY(rowNum, colnum), "不支持数据类型:" + fieldType);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    errorMap.put(StringUtil.generatorXY(rowNum, colnum), "字典转换异常");
                                }
                            } else {
                                _log.warn("not this set method : " + fieldSetName);
                                errorMap.put(StringUtil.generatorXY(rowNum, colnum), "没有set方法:" + fieldSetName);
                            }
                        } else {
                            errorMap.put(StringUtil.generatorXY(rowNum, colnum), BaseConstants.ImportError.DICT_ERROR);
                        }
                    }
                } else {
//                    _log.info("dict do not need to be dealt with this field : " + field.getName());
                }
            }
        }
    }

    public Boolean coverBoolean(String value) {
        return DictUtil.BooleanEnmu.YES.equals(value) ? DictUtil.BooleanEnmu.YES.getIndex() : DictUtil.BooleanEnmu.NO.getIndex();
    }

    /**
     * 查询一个学校的所有院系信息
     *
     * @param schoolCode
     * @return
     */
    public List<UcenterFaculty> getUcenterFacultyList(String schoolCode) {
        UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
        UcenterFacultyExample.Criteria criteria = ucenterFacultyExample.createCriteria();
        criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
        return ucenterFacultyService.selectByExample(ucenterFacultyExample);
    }

    /**
     * 查询一个学校的所有专业信息
     *
     * @param schoolCode
     * @return
     */
    public List<UcenterSpecialty> getUcenterSpecialtyList(String schoolCode) {
        UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
        UcenterSpecialtyExample.Criteria criteria = ucenterSpecialtyExample.createCriteria();
        criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
        return ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);
    }

    /**
     * 查询一个学校的所有班级信息
     *
     * @param schoolCode
     * @return
     */
    public List<UcenterClass> getUcenterClassList(String schoolCode) {
        UcenterClassExample ucenterClassExample = new UcenterClassExample();
        UcenterClassExample.Criteria criteria = ucenterClassExample.createCriteria();
        criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
        return ucenterClassService.selectByExample(ucenterClassExample);
    }

    /**
     * 获取行政区划map
     * key为areaName
     * value为UcenterArea
     *
     * @return
     */
    public ImmutableListMultimap<String, UcenterArea> getUcenterAreaMap() {
        UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
        UcenterAreaExample.Criteria criteria = ucenterAreaExample.createCriteria();
        criteria.andAreaTypeNotEqualTo(BaseConstants.AreaType.COUNTY)
                .andStatusEqualTo(BaseConstants.Status.NORMAL);
        return list2map(ucenterAreaService.selectByExample(ucenterAreaExample));
    }

    /**
     * 院系列表中，查询指定的院系
     *
     * @param ucenterFaculties
     * @param facultyName
     * @return
     */
    public UcenterFaculty getOneUcenterFaculty(List<UcenterFaculty> ucenterFaculties, String facultyName) {
        UcenterFaculty ucenterFaculty = null;
        if (ucenterFaculties != null && ucenterFaculties.size() > 0) {
            for (int i = 0; i < ucenterFaculties.size(); i++) {
                if (ucenterFaculties.get(i).getFacultyName().equals(facultyName)) {
                    ucenterFaculty = ucenterFaculties.get(i);
                    break;
                }
            }
        }
        return ucenterFaculty;
    }

    /**
     * 专业列表中，查询指定的专业
     *
     * @param ucenterSpecialties
     * @param facultyName
     * @param specialtyName
     * @return
     */
    public UcenterSpecialty getOneUcenterSpecialty(List<UcenterSpecialty> ucenterSpecialties, String facultyName, String specialtyName) {
        UcenterSpecialty ucenterSpecialty = null;
        if (ucenterSpecialties != null && ucenterSpecialties.size() > 0) {
            for (int i = 0; i < ucenterSpecialties.size(); i++) {
                if (ucenterSpecialties.get(i).getFacultyName().equals(facultyName)
                        && ucenterSpecialties.get(i).getSpecialtyName().equals(specialtyName)) {
                    ucenterSpecialty = ucenterSpecialties.get(i);
                    break;
                }
            }
        }
        return ucenterSpecialty;
    }

    /**
     * 专业列表中，查询指定的专业
     *
     * @param ucenterSpecialties
     * @param specialtyName
     * @return
     */
    public UcenterSpecialty getOneUcenterSpecialty(List<UcenterSpecialty> ucenterSpecialties, String specialtyName) {
        UcenterSpecialty ucenterSpecialty = null;
        if (ucenterSpecialties != null && ucenterSpecialties.size() > 0 ) {
            for (int i = 0; i < ucenterSpecialties.size(); i++) {
                if (ucenterSpecialties.get(i).getSpecialtyName().equals(specialtyName)) {
                    ucenterSpecialty = ucenterSpecialties.get(i);
                    break;
                }
            }
        }
        return ucenterSpecialty;
    }

    /**
     * 班级列表中，查询出指定的班级
     *
     * @param ucenterClasses
     * @param facultyName
     * @param specialtyName
     * @param className
     * @param enterYear
     * @return
     */
    public UcenterClass getOneUcenterClass(List<UcenterClass> ucenterClasses, String facultyName, String specialtyName, String className, Integer enterYear) {
        UcenterClass ucenterClass = null;
        if (ucenterClasses != null && ucenterClasses.size() > 0 && enterYear != null) {
            for (int i = 0; i < ucenterClasses.size(); i++) {
                if (ucenterClasses.get(i).getYear().intValue() == enterYear.intValue()
                        && ucenterClasses.get(i).getFacultyName().equals(facultyName)
                        && ucenterClasses.get(i).getSpecialtyName().equals(specialtyName)
                        && ucenterClasses.get(i).getClassName().equals(className)) {
                    ucenterClass = ucenterClasses.get(i);
                    break;
                }
            }
        }
        return ucenterClass;
    }

    /**
     * 查询一个学校的部门列表
     *
     * @param schoolCode
     * @return
     */
    public List<UcenterDepartment> getUcenterDepartmentList(String schoolCode) {
        UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
        ucenterDepartmentExample.createCriteria().andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
        return ucenterDepartmentService.selectByExample(ucenterDepartmentExample);
    }

    /**
     * 在部门列表中，查找部门信息
     *
     * @param ucenterDepartments
     * @param departmentName
     * @return
     */
    public UcenterDepartment getUcenterDepartment(List<UcenterDepartment> ucenterDepartments, String departmentName) {
        UcenterDepartment ucenterDepartment = null;
        if (ucenterDepartments != null && ucenterDepartments.size() > 0 && departmentName != null) {
            for (int i = 0; i < ucenterDepartments.size(); i++) {
                if (ucenterDepartments.get(i).getDepartmentName().equals(departmentName)) {
                    ucenterDepartment = ucenterDepartments.get(i);
                    break;
                }
            }
        }
        return ucenterDepartment;
    }

    /**
     * 班级列表中，查询出指定的班级,
     *
     * @param ucenterClasses
     * @param classNames
     * @return
     */
    public List<UcenterClass> getClassIdsByClassNames(List<UcenterClass> ucenterClasses, String classNames, Map<String, String> errorMap, int rowNum, Table tableValid) {
        List<UcenterClass> ucenterClassList = new ArrayList<>();
        if (ucenterClasses != null && ucenterClasses.size() > 0 && classNames != null) {
            String[] className = classNames.split(",");
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < className.length; i++) {
                UcenterClass ucenterClass = null;
                for (int j = 0; j < ucenterClasses.size(); j++) {
                    if (className[i].trim().equals(ucenterClasses.get(j).getClassName())) {
                        ucenterClass = ucenterClasses.get(j);
                    }
                }
                if (ucenterClass != null) {
                    ucenterClassList.add(ucenterClass);
                } else {
                    stringBuffer.append("," + className[i]);
                }
            }
            if (stringBuffer != null && stringBuffer.length() > 0) {
                errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("className")), "学校没有对应班级:" + stringBuffer.toString().substring(1));
            }
        }
        return ucenterClassList;
    }

}

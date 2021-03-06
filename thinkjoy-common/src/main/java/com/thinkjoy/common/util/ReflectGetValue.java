package com.thinkjoy.common.util;

import com.google.common.collect.Maps;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.util.xmlutil.Column;
import com.thinkjoy.common.util.xmlutil.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 利用反射通过bean之间的对比对属性进行修改/更新等
 * Created by xufei on 2017/12/4.
 */
public class ReflectGetValue {
    private static Logger _log= LoggerFactory.getLogger(ReflectGetValue.class);


    /**
     * 复制source -> target (并过滤值为0/0.0/""等置null,以免影响推送)
     * 属性的复制建议采用：Dozer
     * @param source
     * @param destinationClass
     * @param ignoreZero 是否过滤属性值为null/0/""
     * @param <T>
     * @return
     */
    public static <T> T copyBean(Object source, Class<T> destinationClass, Boolean ignoreZero) {
        Map<String, String> valMap = bean2Map(source);
        T t = null;
        try {
            t = copyFieldValue(destinationClass, valMap, ignoreZero);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 从source(old 实体)对应属性更新到target(从数据库获取的实体)
     * 并判断新老对象属性值是否全是一致(即是否需要更新到数据库)
     * @param source
     * @param target
     * true 需要更新,false 不需要更新
     */
    public static boolean updateBean(Object source, Object target) {
        Map<String, String> valMap = bean2Map(source);
        return updateFieldValue(target, valMap);
    }

    /**
     * target 通过与 source 带有自定义的annotation的属性进行比较，只筛选更新的属性保存到数据库,其他为更新的设置NULL
     *
     * @param source
     * @param target
     */
    public static void compareAndUpdateProperty(Object source, Object target, Class annotation) {
        Map<String, String> valMap = bean2Map(source);
        setNoUpdateFieldToNull(target, valMap, annotation);
    }

    /**
     * 将相应的bean转化为map(属性->value)
     *
     * @param source
     * @return
     */
    public static Map<String, String> bean2Map(Object source) {
        Class<?> cls = source.getClass();
        Map<String, String> valueMap = Maps.newHashMap();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                String result = getFieldValue(source, field);
                valueMap.put(field.getName(), result);
            } catch (Exception e) {
                continue;
            }
        }
        return valueMap;
    }

    /**
     * 将实体source有更新的属性更新到对应的实体target
     *
     * @param target
     * @param valMap
     */
    private static void setNoUpdateFieldToNull(Object target,
                                               Map<String, String> valMap, Class annotation) {
        Class<?> cls = target.getClass();

        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                if(isAppointAnnotation(target, field, annotation)){
                    String o = valMap.get(field.getName());
                    String n = getFieldValue(target, field);
                    if (validNullAndEmptyString(o) && o.equals(n))
                        setFieldValue(target, field, null);
                }
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 将实体source有更新的属性更新到对应的实体target
     * 并判断新老对象值是否是一致(即是否需要更新到数据库)
     * @param target
     * @param valMap
     */
    private static boolean updateFieldValue(Object target,
                                            Map<String, String> valMap) {
        boolean flag = false;

        Class<?> cls = target.getClass();

        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                String o = valMap.get(field.getName());
                String n = getFieldValue(target, field);
                if (validNullAndEmptyString(o) && !o.equals(n)){
                    setFieldValue(target, field, o);
                    flag = true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return flag;
    }

    /**
     * set属性的值到Bean
     * @param cls
     * @param valMap
     * @param ignoreZero 是否过滤属性值为0
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> T copyFieldValue(Class<T> cls, Map<String, String> valMap, Boolean ignoreZero) throws Exception{

        Field[] fields = cls.getDeclaredFields();

        T t = cls.newInstance();

        for (Field field : fields) {
            try {
                String value = valMap.get(field.getName());
                if(ignoreZero && !judgeValue(value))
                    setFieldValue(t, field, value);
            } catch (Exception e) {
                continue;
            }
        }

        return t;
    }

    /**
     * 根据字段获取对应的值
     *
     * @param bean
     * @param field
     * @return
     * @throws Exception
     */
    public static String getFieldValue(Object bean, Field field)
            throws Exception {
        Class<?> cls = bean.getClass();
        String fieldType = field.getType().getSimpleName();// 属性类型
        Method[] methods = cls.getDeclaredMethods();
        String fieldGetName = getMethodName(field.getName());
        String result = null;
        // 判断是否有该属性的get方法,没有返回null
        if (checkGetMethod(methods, fieldGetName)) {
            Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
            Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
            if ("Date".equals(fieldType)) {
                result = fmtDate((Date) fieldVal);
            } else {
                if (null != fieldVal) {
                    result = String.valueOf(fieldVal);
                }
            }
        }
        return result;
    }

    /**
     * 根据属性设置值,仅支持基本类型
     *
     * @param bean
     * @param field
     * @param value
     * @throws Exception
     */
    public static void setFieldValue(Object bean, Field field, String value)
            throws Exception {
        Class<?> cls = bean.getClass();
        Method[] methods = cls.getDeclaredMethods();
        // 属性set方法名
        String fieldSetName = setMethodName(field.getName());
        if (checkSetMethod(methods, fieldSetName)) {
            Method fieldSetMet = cls.getMethod(fieldSetName, field.getType());// set方法
            if (validNullAndEmptyString(value)) {
                String fieldType = field.getType().getSimpleName();
                if ("String".equals(fieldType)) {
                    fieldSetMet.invoke(bean, value);
                } else if ("Date".equals(fieldType)) {
                    Date temp = parseDate(value);
                    fieldSetMet.invoke(bean, temp);
                } else if ("Short".equalsIgnoreCase(fieldType)) {
                    Short temp = Short.parseShort(value);
                    fieldSetMet.invoke(bean, temp);
                } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
                    Integer temp = Integer.parseInt(value);
                    fieldSetMet.invoke(bean, temp);
                } else if ("Float".equalsIgnoreCase(fieldType)) {
                    Float temp = Float.parseFloat(value);
                    fieldSetMet.invoke(bean, temp);
                } else if ("Long".equalsIgnoreCase(fieldType)) {
                    Long temp = Long.parseLong(value);
                    fieldSetMet.invoke(bean, temp);
                } else if ("Double".equalsIgnoreCase(fieldType)) {
                    Double temp = Double.parseDouble(value);
                    fieldSetMet.invoke(bean, temp);
                } else if ("Boolean".equalsIgnoreCase(fieldType)) {
                    Boolean temp = Boolean.parseBoolean(value);
                    fieldSetMet.invoke(bean, temp);
                } else {
                    System.out.println("not supper type" + fieldType);
                }
            } else {//value=null或者""
                fieldSetMet.invoke(bean, value);
            }
        } else {
            System.out.println("not this set method : " + fieldSetName);
        }
    }

    /**
     * 创建实例并初始化
     * @param data
     * @param tableValid
     * @param errorMap
     * @param rowNum
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Map<Integer,String> data,Table tableValid,Map<String, String> errorMap,int rowNum,Class<T> cls) {
        List<Column> columns=tableValid.getColumnsByColClass(BaseConstants.colClass.COL_COMMON);//取出普通字段
        Field[] fields = cls.getDeclaredFields();
        Method[] methods = cls.getDeclaredMethods();
        T t=null;
        try {
            t = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            _log.error("不能实例化"+cls.getName()+"类");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            _log.error("安全权限异常，一般来说，是由于java在反射时调用了private方法所导致的");
        }

        for(Field field:fields){
            Integer colnum=null;
            for (Column column:columns){
                if(field.getName().equals(column.getName())){
                    colnum=column.getPosition();
                    break;
                }
            }
            if(colnum!=null){
                String value=data.get(colnum);
                // 属性set方法名
                String fieldSetName = setMethodName(field.getName());
                if (checkSetMethod(methods, fieldSetName)) {
                    Method fieldSetMet = null;// set方法
                    try {
                        fieldSetMet = cls.getMethod(fieldSetName, field.getType());
                        if (validNullAndEmptyString(value)) {
                            String fieldType = field.getType().getSimpleName();
                            if ("String".equals(fieldType)) {
                                fieldSetMet.invoke(t, value);
                            } else if ("Date".equals(fieldType)) {
                                Date temp = parseDate(value);
                                fieldSetMet.invoke(t, temp);
                            } else if ("Short".equalsIgnoreCase(fieldType)) {
                                Short temp = Short.parseShort(value);
                                fieldSetMet.invoke(t, temp);
                            } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
                                Integer temp = Integer.parseInt(value);
                                fieldSetMet.invoke(t, temp);
                            } else if ("Float".equalsIgnoreCase(fieldType)) {
                                Float temp = Float.parseFloat(value);
                                fieldSetMet.invoke(t, temp);
                            } else if ("Long".equalsIgnoreCase(fieldType)) {
                                Long temp = Long.parseLong(value);
                                fieldSetMet.invoke(t, temp);
                            } else if ("Double".equalsIgnoreCase(fieldType)) {
                                Double temp = Double.parseDouble(value);
                                fieldSetMet.invoke(t, temp);
                            } else if ("Boolean".equalsIgnoreCase(fieldType)) {
                                Boolean temp = Boolean.parseBoolean(value);
                                fieldSetMet.invoke(t, temp);
                            } else {
                                _log.warn("not supper type" + fieldType);
                            }
                        } else {//value=null或者""

                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        errorMap.put(StringUtil.generatorXY(rowNum,colnum), BaseConstants.ImportError.DATA_FORMAT);
                    }
                } else {
                    _log.warn("not this set method : " + fieldSetName);
                }
            } else {
//                _log.warn("not this field : " + field.getName());
            }
        }

        return t;
    }
    /**
     *　判断实体属性值是否全部为null
     * @param bean
     * @return
     */
    public static void zero2Null(Object bean){
        Boolean flag = true;
        try {
            Class<?> srcClass = bean.getClass();
            Field[] fields = srcClass.getDeclaredFields();
            // 比较属性值
            for (Field field : fields) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *　判断实体属性值是否全部为null
     * @param bean
     * @return
     */
    public static boolean isNullSelectProperties(Object bean, Class annotation){
        Boolean flag = true;
        try {
            Class<?> srcClass = bean.getClass();
            Field[] fields = srcClass.getDeclaredFields();
            // 比较属性值
            for (Field field : fields) {
                if(isAppointAnnotation(bean, field, annotation)){
                    // 原属性值
                    Object so = getFieldValue(bean, field);
                    boolean j = judgeValue(so == null?"":so.toString());
                    if(!j){
                        flag = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断某个字段的get方法是否有指定的annotation
     * @param bean
     * @param field
     * @param annotation
     * @return
     * @throws Exception
     */
    public static boolean isAppointAnnotation(Object bean, Field field, Class annotation) throws Exception{
        if(annotation == null) return false;
        Class<?> cls = bean.getClass();
        Method[] methods = cls.getDeclaredMethods();
        String fieldGetName = getMethodName(field.getName());
        // 判断是否有该属性的get方法,没有返回null
        if (checkGetMethod(methods, fieldGetName)) {
            Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
            Annotation clazz = fieldGetMet.getAnnotation(annotation);
            if(clazz != null)
                return true;
        }
        return false;
    }

    /**
     * 判断是否存在某属性的 set方法
     *
     * @param methods
     * @param fieldSetMet
     * @return
     */
    public static boolean checkSetMethod(Method[] methods, String fieldSetMet) {
        for (Method met : methods)
            if (fieldSetMet.equals(met.getName()))
                return true;
        return false;
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param methods
     * @param fieldGetMet
     * @return
     */
    public static boolean checkGetMethod(Method[] methods, String fieldGetMet) {
        for (Method met : methods)
            if (fieldGetMet.equals(met.getName()))
                return true;
        return false;
    }

    /**
     * 拼接某属性的 get方法
     *
     * @param fieldName
     * @return
     */
    public static String getMethodName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName
     * @return
     */
    public static String setMethodName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

    /**
     * 判断字段名是否包含id,将其过滤
     * @param fieldName
     * @return
     */
    public static boolean isContainId(String fieldName){
        return (fieldName.contains("id") || fieldName.contains("Id"));
    }

    /**
     * 验证null/""
     * @param value
     * @return
     */
    public static boolean validNullAndEmptyString(String value) {
        return (null != value && !"".equals(value));
    }

    /**
     * 格式化string为Date
     *
     * @param datestr
     * @return date
     */
    public static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            String fmtstr = null;
            if (datestr.indexOf(':') > 0) {
                fmtstr = "yyyy-MM-dd HH:mm:ss";
            } else {
                fmtstr = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);
            return sdf.parse(datestr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期转化为String
     *
     * @param date
     * @return date string
     */
    public static String fmtDate(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.US);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 判断数据类型
     * @param tarValue
     * @return
     */
    private static Boolean judgeValue(String tarValue){
        return ("".equals(tarValue) || "0".equals(tarValue) || "0.0".equals(tarValue));
    }

    public static void main(String args[]) {
//        HealthManage hm = new HealthManage();
//        hm.setPatientId(4942);
//        hm.setCalories(25.6f);
//        hm.setBloodSugar("220");
//        hm.setSpeed(120f);
//
//        HealthIndex hi = new HealthIndex();
//        hi.setBloodSugar("230");
//        hi.setBMI(2.6f);
//        hi.setSpeed(120f);
//
//        BeanRefUtil.compareAndUpdateProperty(hm,hi, Validate.class);
//
//        System.out.println(hi.getSpeed());
    }
}

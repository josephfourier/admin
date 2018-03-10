package com.thinkjoy.common.util.xmlutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JaxbTest {
    public static void main(String[] args) throws Exception {
        Table table=new Table();
        table.setCnName("学生基本信息表");
        table.setName("ucenter_student");
        List<Column> columnList=new ArrayList<Column>();

        //1
        Column column1=new Column();
        column1.setPosition(1);
        column1.setChName("学生名称");
        column1.setName("studentName");
        column1.setDataType("String");
        column1.setDataClass("commonType");

        Map<String,String> map1=new HashMap<String, String>();
        map1.put("notEmpty", "");
        map1.put("regEx", "^\\d{4}-\\d{2}-\\d{2}$");
        column1.setValidations(map1);
        Map<String,String> errormap1=new HashMap<String, String>();
        errormap1.put("notEmpty", "学生姓名不能为空");
        errormap1.put("regEx", "日期格式错误：yyyy-MM-dd");
//        column1.setErrorMessages(errormap1);
        columnList.add(column1);

        //2
        Column column2=new Column();
        column2.setPosition(2);
        column2.setChName("性别");
        column2.setName("sex");
        column2.setDataType("String");
        column2.setDataClass("otherType");

        Map<String,String> map2=new HashMap<String, String>();
        map2.put("notEmpty", "");
        column2.setValidations(map2);
        columnList.add(column2);
        //3
        Column column3=new Column();
        column3.setPosition(3);
        column3.setChName("民族");
        column3.setName("nation");
        column3.setDataType("String");
        column3.setDictCode("DICT_NATION");
        column3.setDataClass("dictType");

        Map<String,String> map3=new HashMap<String, String>();
        map3.put("notEmpty", "");
        column3.setValidations(map3);
        columnList.add(column3);

        table.setColumns(columnList);

//        Column context1 = JAXBUtil.formXML(Column.class , xml);
        String xml = JAXBUtil.toXML(table);
        System.out.println(xml);
    }
}

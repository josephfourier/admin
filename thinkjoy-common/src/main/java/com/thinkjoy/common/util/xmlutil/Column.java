package com.thinkjoy.common.util.xmlutil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by xufei on 2017/11/27.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Column  implements Serializable {
    @XmlAttribute(name = "position")
    private Integer position;//位置（列号）
    @XmlAttribute(name = "chname")
    private String chName;// 字段中文名称
    @XmlAttribute(name = "name")
    private String name;// 字段名称
    @XmlAttribute(name = "datatype")
    private String dataType;//字段类型（Integer,String，Long,Double...）
    @XmlAttribute(name = "dataclass")
    private String dataClass;//字段分类：1、普通字段，2、字典字段，3、其他字段
    @XmlAttribute(name = "dictcode")
    private String dictCode;//字典编码
    @XmlJavaTypeAdapter(MapAdapter.class)
    @XmlElement(name = "validation")
    private Map<String,String> validations;//验证列表：key:value  key:验证类型，value：验证值
    @XmlJavaTypeAdapter(MapAdapter.class)
    @XmlElement(name = "errormessage")
    private Map<String,String> errorMessages;//错误提示列表：key:value  key:验证类型，value：提示信息   与验证类型完全对应（，如果不配置，使用默认值）

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDataClass() {
        return dataClass;
    }

    public void setDataClass(String dataClass) {
        this.dataClass = dataClass;
    }

    public Map<String, String> getValidations() {
        return validations;
    }

    public void setValidations(Map<String, String> validations) {
        this.validations = validations;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}

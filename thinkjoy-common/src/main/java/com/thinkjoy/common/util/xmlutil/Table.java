package com.thinkjoy.common.util.xmlutil;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xufei
 * @date 2017/12/13
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="table")
public class Table implements Serializable {
    @XmlAttribute(name = "cnname")
    private String cnName;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "colnum")
    private Integer colNum;
    @XmlAttribute(name = "maxrows")
    private Long maxRows;
    @XmlElement(name = "column")
    private List<Column> columns;

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public Long getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(Long maxRows) {
        this.maxRows = maxRows;
    }

    /**
     * 根据数据类型 查询所有字段列表
     * @param dataClass
     * @return
     */
    public List<Column> getColumnsByColClass(String dataClass){
        List<Column> column1=new ArrayList<>();
        for(int i=0;i<columns.size();i++){
            if(columns.get(i).getDataClass().equals(dataClass)){
                column1.add(columns.get(i));
            }
        }
        return column1;
    }

    /**
     * 查出所有字典编码列表
     * @return
     */
    public List<String> getDictCodes(){
        List<String> dictCodes=new ArrayList<>();
        for(int i=0;i<columns.size();i++){
            if(columns.get(i).getDictCode()!=null){
                dictCodes.add(columns.get(i).getDictCode());
            }
        }
        return dictCodes;
    }


    /**
     * 根据字段名称查字段信息
     * @return
     */
    public Column getColumnBycolName(String colName){
        for(int i=0;i<columns.size();i++){
            if(columns.get(i).getName().equals(colName)){
                return columns.get(i);
            }
        }
        return null;
    }

    public Integer getPositonBycolName(String colName){
        for(int i=0;i<columns.size();i++){
            if(columns.get(i).getName().equals(colName)){
                return columns.get(i).getPosition();
            }
        }
        return null;
    }
}

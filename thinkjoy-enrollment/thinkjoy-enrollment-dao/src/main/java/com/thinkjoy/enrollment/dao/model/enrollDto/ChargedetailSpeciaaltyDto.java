package com.thinkjoy.enrollment.dao.model.enrollDto;

import java.io.Serializable;

/**
 * Created by gengsongbo on 2017/12/20.
 */
public class ChargedetailSpeciaaltyDto implements Serializable {

	/**
	 * 费用项目id
	 *
	 * @mbg.generated
	 */
	private Integer detailId;

	/**
	 * 校内专业编码,每个学校专业不同，学校会创建自己的专业编码
	 *
	 * @mbg.generated
	 */
	private String specialtyCode;

	/**
	 * 年份
	 *
	 * @mbg.generated
	 */
	private Integer year;

	/**
	 * 学校编码,学校数据隔离
	 *
	 * @mbg.generated
	 */
	private String schoolCode;

	/**
	 * 费用类别id
	 *
	 * @mbg.generated
	 */
	private Integer itemId;

	/**
	 * 费用类别名称
	 *
	 * @mbg.generated
	 */
	private String itemName;

	/**
	 * 专业名称
	 *
	 * @mbg.generated
	 */
	private String specialtyName;

	public Integer getDetailId() {return detailId;}

	public void setDetailId(Integer detailId) {this.detailId = detailId;}

	public String getSpecialtyCode() {return specialtyCode;}

	public void setSpecialtyCode(String specialtyCode) {this.specialtyCode = specialtyCode;}

	public Integer getYear() {return year;}

	public void setYear(Integer year) {this.year = year;}

	public String getSchoolCode() {return schoolCode;}

	public void setSchoolCode(String schoolCode) {this.schoolCode = schoolCode;}

	public Integer getItemId() {return itemId;}

	public void setItemId(Integer itemId) {this.itemId = itemId;}

	public String getItemName() {return itemName;}

	public void setItemName(String itemName) {this.itemName = itemName;}

	public String getSpecialtyName() {return specialtyName;}

	public void setSpecialtyName(String specialtyName) {this.specialtyName = specialtyName;}
}

package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UcenterSpecialtyExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public UcenterSpecialtyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSpecialtyIdIsNull() {
            addCriterion("specialty_id is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdIsNotNull() {
            addCriterion("specialty_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdEqualTo(Integer value) {
            addCriterion("specialty_id =", value, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdNotEqualTo(Integer value) {
            addCriterion("specialty_id <>", value, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdGreaterThan(Integer value) {
            addCriterion("specialty_id >", value, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("specialty_id >=", value, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdLessThan(Integer value) {
            addCriterion("specialty_id <", value, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdLessThanOrEqualTo(Integer value) {
            addCriterion("specialty_id <=", value, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdIn(List<Integer> values) {
            addCriterion("specialty_id in", values, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdNotIn(List<Integer> values) {
            addCriterion("specialty_id not in", values, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdBetween(Integer value1, Integer value2) {
            addCriterion("specialty_id between", value1, value2, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("specialty_id not between", value1, value2, "specialtyId");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeIsNull() {
            addCriterion("specialty_code is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeIsNotNull() {
            addCriterion("specialty_code is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeEqualTo(String value) {
            addCriterion("specialty_code =", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeNotEqualTo(String value) {
            addCriterion("specialty_code <>", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeGreaterThan(String value) {
            addCriterion("specialty_code >", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("specialty_code >=", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeLessThan(String value) {
            addCriterion("specialty_code <", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeLessThanOrEqualTo(String value) {
            addCriterion("specialty_code <=", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeLike(String value) {
            addCriterion("specialty_code like", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeNotLike(String value) {
            addCriterion("specialty_code not like", value, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeIn(List<String> values) {
            addCriterion("specialty_code in", values, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeNotIn(List<String> values) {
            addCriterion("specialty_code not in", values, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeBetween(String value1, String value2) {
            addCriterion("specialty_code between", value1, value2, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyCodeNotBetween(String value1, String value2) {
            addCriterion("specialty_code not between", value1, value2, "specialtyCode");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoIsNull() {
            addCriterion("specialty_no is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoIsNotNull() {
            addCriterion("specialty_no is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoEqualTo(String value) {
            addCriterion("specialty_no =", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoNotEqualTo(String value) {
            addCriterion("specialty_no <>", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoGreaterThan(String value) {
            addCriterion("specialty_no >", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoGreaterThanOrEqualTo(String value) {
            addCriterion("specialty_no >=", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoLessThan(String value) {
            addCriterion("specialty_no <", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoLessThanOrEqualTo(String value) {
            addCriterion("specialty_no <=", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoLike(String value) {
            addCriterion("specialty_no like", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoNotLike(String value) {
            addCriterion("specialty_no not like", value, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoIn(List<String> values) {
            addCriterion("specialty_no in", values, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoNotIn(List<String> values) {
            addCriterion("specialty_no not in", values, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoBetween(String value1, String value2) {
            addCriterion("specialty_no between", value1, value2, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNoNotBetween(String value1, String value2) {
            addCriterion("specialty_no not between", value1, value2, "specialtyNo");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameIsNull() {
            addCriterion("specialty_name is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameIsNotNull() {
            addCriterion("specialty_name is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameEqualTo(String value) {
            addCriterion("specialty_name =", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameNotEqualTo(String value) {
            addCriterion("specialty_name <>", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameGreaterThan(String value) {
            addCriterion("specialty_name >", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameGreaterThanOrEqualTo(String value) {
            addCriterion("specialty_name >=", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameLessThan(String value) {
            addCriterion("specialty_name <", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameLessThanOrEqualTo(String value) {
            addCriterion("specialty_name <=", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameLike(String value) {
            addCriterion("specialty_name like", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameNotLike(String value) {
            addCriterion("specialty_name not like", value, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameIn(List<String> values) {
            addCriterion("specialty_name in", values, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameNotIn(List<String> values) {
            addCriterion("specialty_name not in", values, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameBetween(String value1, String value2) {
            addCriterion("specialty_name between", value1, value2, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNameNotBetween(String value1, String value2) {
            addCriterion("specialty_name not between", value1, value2, "specialtyName");
            return (Criteria) this;
        }

        public Criteria andTrdrIdIsNull() {
            addCriterion("trdr_id is null");
            return (Criteria) this;
        }

        public Criteria andTrdrIdIsNotNull() {
            addCriterion("trdr_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrdrIdEqualTo(Integer value) {
            addCriterion("trdr_id =", value, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdNotEqualTo(Integer value) {
            addCriterion("trdr_id <>", value, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdGreaterThan(Integer value) {
            addCriterion("trdr_id >", value, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trdr_id >=", value, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdLessThan(Integer value) {
            addCriterion("trdr_id <", value, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdLessThanOrEqualTo(Integer value) {
            addCriterion("trdr_id <=", value, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdIn(List<Integer> values) {
            addCriterion("trdr_id in", values, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdNotIn(List<Integer> values) {
            addCriterion("trdr_id not in", values, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdBetween(Integer value1, Integer value2) {
            addCriterion("trdr_id between", value1, value2, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trdr_id not between", value1, value2, "trdrId");
            return (Criteria) this;
        }

        public Criteria andTrdrNameIsNull() {
            addCriterion("trdr_name is null");
            return (Criteria) this;
        }

        public Criteria andTrdrNameIsNotNull() {
            addCriterion("trdr_name is not null");
            return (Criteria) this;
        }

        public Criteria andTrdrNameEqualTo(String value) {
            addCriterion("trdr_name =", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameNotEqualTo(String value) {
            addCriterion("trdr_name <>", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameGreaterThan(String value) {
            addCriterion("trdr_name >", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameGreaterThanOrEqualTo(String value) {
            addCriterion("trdr_name >=", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameLessThan(String value) {
            addCriterion("trdr_name <", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameLessThanOrEqualTo(String value) {
            addCriterion("trdr_name <=", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameLike(String value) {
            addCriterion("trdr_name like", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameNotLike(String value) {
            addCriterion("trdr_name not like", value, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameIn(List<String> values) {
            addCriterion("trdr_name in", values, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameNotIn(List<String> values) {
            addCriterion("trdr_name not in", values, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameBetween(String value1, String value2) {
            addCriterion("trdr_name between", value1, value2, "trdrName");
            return (Criteria) this;
        }

        public Criteria andTrdrNameNotBetween(String value1, String value2) {
            addCriterion("trdr_name not between", value1, value2, "trdrName");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeIsNull() {
            addCriterion("specialty_type is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeIsNotNull() {
            addCriterion("specialty_type is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeEqualTo(String value) {
            addCriterion("specialty_type =", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeNotEqualTo(String value) {
            addCriterion("specialty_type <>", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeGreaterThan(String value) {
            addCriterion("specialty_type >", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("specialty_type >=", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeLessThan(String value) {
            addCriterion("specialty_type <", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeLessThanOrEqualTo(String value) {
            addCriterion("specialty_type <=", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeLike(String value) {
            addCriterion("specialty_type like", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeNotLike(String value) {
            addCriterion("specialty_type not like", value, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeIn(List<String> values) {
            addCriterion("specialty_type in", values, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeNotIn(List<String> values) {
            addCriterion("specialty_type not in", values, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeBetween(String value1, String value2) {
            addCriterion("specialty_type between", value1, value2, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyTypeNotBetween(String value1, String value2) {
            addCriterion("specialty_type not between", value1, value2, "specialtyType");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcIsNull() {
            addCriterion("specialty_jc is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcIsNotNull() {
            addCriterion("specialty_jc is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcEqualTo(String value) {
            addCriterion("specialty_jc =", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcNotEqualTo(String value) {
            addCriterion("specialty_jc <>", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcGreaterThan(String value) {
            addCriterion("specialty_jc >", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcGreaterThanOrEqualTo(String value) {
            addCriterion("specialty_jc >=", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcLessThan(String value) {
            addCriterion("specialty_jc <", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcLessThanOrEqualTo(String value) {
            addCriterion("specialty_jc <=", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcLike(String value) {
            addCriterion("specialty_jc like", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcNotLike(String value) {
            addCriterion("specialty_jc not like", value, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcIn(List<String> values) {
            addCriterion("specialty_jc in", values, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcNotIn(List<String> values) {
            addCriterion("specialty_jc not in", values, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcBetween(String value1, String value2) {
            addCriterion("specialty_jc between", value1, value2, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyJcNotBetween(String value1, String value2) {
            addCriterion("specialty_jc not between", value1, value2, "specialtyJc");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameIsNull() {
            addCriterion("specialty_enname is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameIsNotNull() {
            addCriterion("specialty_enname is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameEqualTo(String value) {
            addCriterion("specialty_enname =", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameNotEqualTo(String value) {
            addCriterion("specialty_enname <>", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameGreaterThan(String value) {
            addCriterion("specialty_enname >", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameGreaterThanOrEqualTo(String value) {
            addCriterion("specialty_enname >=", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameLessThan(String value) {
            addCriterion("specialty_enname <", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameLessThanOrEqualTo(String value) {
            addCriterion("specialty_enname <=", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameLike(String value) {
            addCriterion("specialty_enname like", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameNotLike(String value) {
            addCriterion("specialty_enname not like", value, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameIn(List<String> values) {
            addCriterion("specialty_enname in", values, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameNotIn(List<String> values) {
            addCriterion("specialty_enname not in", values, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameBetween(String value1, String value2) {
            addCriterion("specialty_enname between", value1, value2, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEnnameNotBetween(String value1, String value2) {
            addCriterion("specialty_enname not between", value1, value2, "specialtyEnname");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeIsNull() {
            addCriterion("faculty_code is null");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeIsNotNull() {
            addCriterion("faculty_code is not null");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeEqualTo(String value) {
            addCriterion("faculty_code =", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeNotEqualTo(String value) {
            addCriterion("faculty_code <>", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeGreaterThan(String value) {
            addCriterion("faculty_code >", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("faculty_code >=", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeLessThan(String value) {
            addCriterion("faculty_code <", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeLessThanOrEqualTo(String value) {
            addCriterion("faculty_code <=", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeLike(String value) {
            addCriterion("faculty_code like", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeNotLike(String value) {
            addCriterion("faculty_code not like", value, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeIn(List<String> values) {
            addCriterion("faculty_code in", values, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeNotIn(List<String> values) {
            addCriterion("faculty_code not in", values, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeBetween(String value1, String value2) {
            addCriterion("faculty_code between", value1, value2, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyCodeNotBetween(String value1, String value2) {
            addCriterion("faculty_code not between", value1, value2, "facultyCode");
            return (Criteria) this;
        }

        public Criteria andFacultyNameIsNull() {
            addCriterion("faculty_name is null");
            return (Criteria) this;
        }

        public Criteria andFacultyNameIsNotNull() {
            addCriterion("faculty_name is not null");
            return (Criteria) this;
        }

        public Criteria andFacultyNameEqualTo(String value) {
            addCriterion("faculty_name =", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameNotEqualTo(String value) {
            addCriterion("faculty_name <>", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameGreaterThan(String value) {
            addCriterion("faculty_name >", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameGreaterThanOrEqualTo(String value) {
            addCriterion("faculty_name >=", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameLessThan(String value) {
            addCriterion("faculty_name <", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameLessThanOrEqualTo(String value) {
            addCriterion("faculty_name <=", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameLike(String value) {
            addCriterion("faculty_name like", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameNotLike(String value) {
            addCriterion("faculty_name not like", value, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameIn(List<String> values) {
            addCriterion("faculty_name in", values, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameNotIn(List<String> values) {
            addCriterion("faculty_name not in", values, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameBetween(String value1, String value2) {
            addCriterion("faculty_name between", value1, value2, "facultyName");
            return (Criteria) this;
        }

        public Criteria andFacultyNameNotBetween(String value1, String value2) {
            addCriterion("faculty_name not between", value1, value2, "facultyName");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemIsNull() {
            addCriterion("school_system is null");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemIsNotNull() {
            addCriterion("school_system is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemEqualTo(String value) {
            addCriterion("school_system =", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemNotEqualTo(String value) {
            addCriterion("school_system <>", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemGreaterThan(String value) {
            addCriterion("school_system >", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemGreaterThanOrEqualTo(String value) {
            addCriterion("school_system >=", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemLessThan(String value) {
            addCriterion("school_system <", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemLessThanOrEqualTo(String value) {
            addCriterion("school_system <=", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemLike(String value) {
            addCriterion("school_system like", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemNotLike(String value) {
            addCriterion("school_system not like", value, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemIn(List<String> values) {
            addCriterion("school_system in", values, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemNotIn(List<String> values) {
            addCriterion("school_system not in", values, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemBetween(String value1, String value2) {
            addCriterion("school_system between", value1, value2, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andSchoolSystemNotBetween(String value1, String value2) {
            addCriterion("school_system not between", value1, value2, "schoolSystem");
            return (Criteria) this;
        }

        public Criteria andHighestEducationIsNull() {
            addCriterion("highest_education is null");
            return (Criteria) this;
        }

        public Criteria andHighestEducationIsNotNull() {
            addCriterion("highest_education is not null");
            return (Criteria) this;
        }

        public Criteria andHighestEducationEqualTo(String value) {
            addCriterion("highest_education =", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationNotEqualTo(String value) {
            addCriterion("highest_education <>", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationGreaterThan(String value) {
            addCriterion("highest_education >", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationGreaterThanOrEqualTo(String value) {
            addCriterion("highest_education >=", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationLessThan(String value) {
            addCriterion("highest_education <", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationLessThanOrEqualTo(String value) {
            addCriterion("highest_education <=", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationLike(String value) {
            addCriterion("highest_education like", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationNotLike(String value) {
            addCriterion("highest_education not like", value, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationIn(List<String> values) {
            addCriterion("highest_education in", values, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationNotIn(List<String> values) {
            addCriterion("highest_education not in", values, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationBetween(String value1, String value2) {
            addCriterion("highest_education between", value1, value2, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andHighestEducationNotBetween(String value1, String value2) {
            addCriterion("highest_education not between", value1, value2, "highestEducation");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureIsNull() {
            addCriterion("specialty_nature is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureIsNotNull() {
            addCriterion("specialty_nature is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureEqualTo(String value) {
            addCriterion("specialty_nature =", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureNotEqualTo(String value) {
            addCriterion("specialty_nature <>", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureGreaterThan(String value) {
            addCriterion("specialty_nature >", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureGreaterThanOrEqualTo(String value) {
            addCriterion("specialty_nature >=", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureLessThan(String value) {
            addCriterion("specialty_nature <", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureLessThanOrEqualTo(String value) {
            addCriterion("specialty_nature <=", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureLike(String value) {
            addCriterion("specialty_nature like", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureNotLike(String value) {
            addCriterion("specialty_nature not like", value, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureIn(List<String> values) {
            addCriterion("specialty_nature in", values, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureNotIn(List<String> values) {
            addCriterion("specialty_nature not in", values, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureBetween(String value1, String value2) {
            addCriterion("specialty_nature between", value1, value2, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNatureNotBetween(String value1, String value2) {
            addCriterion("specialty_nature not between", value1, value2, "specialtyNature");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetIsNull() {
            addCriterion("enrollment_target is null");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetIsNotNull() {
            addCriterion("enrollment_target is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetEqualTo(String value) {
            addCriterion("enrollment_target =", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetNotEqualTo(String value) {
            addCriterion("enrollment_target <>", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetGreaterThan(String value) {
            addCriterion("enrollment_target >", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetGreaterThanOrEqualTo(String value) {
            addCriterion("enrollment_target >=", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetLessThan(String value) {
            addCriterion("enrollment_target <", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetLessThanOrEqualTo(String value) {
            addCriterion("enrollment_target <=", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetLike(String value) {
            addCriterion("enrollment_target like", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetNotLike(String value) {
            addCriterion("enrollment_target not like", value, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetIn(List<String> values) {
            addCriterion("enrollment_target in", values, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetNotIn(List<String> values) {
            addCriterion("enrollment_target not in", values, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetBetween(String value1, String value2) {
            addCriterion("enrollment_target between", value1, value2, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTargetNotBetween(String value1, String value2) {
            addCriterion("enrollment_target not between", value1, value2, "enrollmentTarget");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCtimeIsNull() {
            addCriterion("ctime is null");
            return (Criteria) this;
        }

        public Criteria andCtimeIsNotNull() {
            addCriterion("ctime is not null");
            return (Criteria) this;
        }

        public Criteria andCtimeEqualTo(Long value) {
            addCriterion("ctime =", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotEqualTo(Long value) {
            addCriterion("ctime <>", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeGreaterThan(Long value) {
            addCriterion("ctime >", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeGreaterThanOrEqualTo(Long value) {
            addCriterion("ctime >=", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeLessThan(Long value) {
            addCriterion("ctime <", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeLessThanOrEqualTo(Long value) {
            addCriterion("ctime <=", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeIn(List<Long> values) {
            addCriterion("ctime in", values, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotIn(List<Long> values) {
            addCriterion("ctime not in", values, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeBetween(Long value1, Long value2) {
            addCriterion("ctime between", value1, value2, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotBetween(Long value1, Long value2) {
            addCriterion("ctime not between", value1, value2, "ctime");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Integer value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Integer> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Integer> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Integer value1, Integer value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Integer value1, Integer value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeIsNull() {
            addCriterion("school_code is null");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeIsNotNull() {
            addCriterion("school_code is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeEqualTo(String value) {
            addCriterion("school_code =", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeNotEqualTo(String value) {
            addCriterion("school_code <>", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeGreaterThan(String value) {
            addCriterion("school_code >", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeGreaterThanOrEqualTo(String value) {
            addCriterion("school_code >=", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeLessThan(String value) {
            addCriterion("school_code <", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeLessThanOrEqualTo(String value) {
            addCriterion("school_code <=", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeLike(String value) {
            addCriterion("school_code like", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeNotLike(String value) {
            addCriterion("school_code not like", value, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeIn(List<String> values) {
            addCriterion("school_code in", values, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeNotIn(List<String> values) {
            addCriterion("school_code not in", values, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeBetween(String value1, String value2) {
            addCriterion("school_code between", value1, value2, "schoolCode");
            return (Criteria) this;
        }

        public Criteria andSchoolCodeNotBetween(String value1, String value2) {
            addCriterion("school_code not between", value1, value2, "schoolCode");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
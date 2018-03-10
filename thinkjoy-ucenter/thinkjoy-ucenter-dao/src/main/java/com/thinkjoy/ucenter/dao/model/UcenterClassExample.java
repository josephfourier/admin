package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UcenterClassExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public UcenterClassExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andClassIdIsNull() {
            addCriterion("class_id is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("class_id is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("class_id =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("class_id <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("class_id in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("class_id not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("class_id between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("class_id not between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNull() {
            addCriterion("class_name is null");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNotNull() {
            addCriterion("class_name is not null");
            return (Criteria) this;
        }

        public Criteria andClassNameEqualTo(String value) {
            addCriterion("class_name =", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotEqualTo(String value) {
            addCriterion("class_name <>", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThan(String value) {
            addCriterion("class_name >", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("class_name >=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThan(String value) {
            addCriterion("class_name <", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThanOrEqualTo(String value) {
            addCriterion("class_name <=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLike(String value) {
            addCriterion("class_name like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotLike(String value) {
            addCriterion("class_name not like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameIn(List<String> values) {
            addCriterion("class_name in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotIn(List<String> values) {
            addCriterion("class_name not in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameBetween(String value1, String value2) {
            addCriterion("class_name between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotBetween(String value1, String value2) {
            addCriterion("class_name not between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andBzrIdIsNull() {
            addCriterion("bzr_id is null");
            return (Criteria) this;
        }

        public Criteria andBzrIdIsNotNull() {
            addCriterion("bzr_id is not null");
            return (Criteria) this;
        }

        public Criteria andBzrIdEqualTo(Integer value) {
            addCriterion("bzr_id =", value, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdNotEqualTo(Integer value) {
            addCriterion("bzr_id <>", value, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdGreaterThan(Integer value) {
            addCriterion("bzr_id >", value, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bzr_id >=", value, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdLessThan(Integer value) {
            addCriterion("bzr_id <", value, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdLessThanOrEqualTo(Integer value) {
            addCriterion("bzr_id <=", value, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdIn(List<Integer> values) {
            addCriterion("bzr_id in", values, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdNotIn(List<Integer> values) {
            addCriterion("bzr_id not in", values, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdBetween(Integer value1, Integer value2) {
            addCriterion("bzr_id between", value1, value2, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bzr_id not between", value1, value2, "bzrId");
            return (Criteria) this;
        }

        public Criteria andBzrNameIsNull() {
            addCriterion("bzr_name is null");
            return (Criteria) this;
        }

        public Criteria andBzrNameIsNotNull() {
            addCriterion("bzr_name is not null");
            return (Criteria) this;
        }

        public Criteria andBzrNameEqualTo(String value) {
            addCriterion("bzr_name =", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameNotEqualTo(String value) {
            addCriterion("bzr_name <>", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameGreaterThan(String value) {
            addCriterion("bzr_name >", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameGreaterThanOrEqualTo(String value) {
            addCriterion("bzr_name >=", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameLessThan(String value) {
            addCriterion("bzr_name <", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameLessThanOrEqualTo(String value) {
            addCriterion("bzr_name <=", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameLike(String value) {
            addCriterion("bzr_name like", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameNotLike(String value) {
            addCriterion("bzr_name not like", value, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameIn(List<String> values) {
            addCriterion("bzr_name in", values, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameNotIn(List<String> values) {
            addCriterion("bzr_name not in", values, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameBetween(String value1, String value2) {
            addCriterion("bzr_name between", value1, value2, "bzrName");
            return (Criteria) this;
        }

        public Criteria andBzrNameNotBetween(String value1, String value2) {
            addCriterion("bzr_name not between", value1, value2, "bzrName");
            return (Criteria) this;
        }

        public Criteria andFdyIdIsNull() {
            addCriterion("fdy_id is null");
            return (Criteria) this;
        }

        public Criteria andFdyIdIsNotNull() {
            addCriterion("fdy_id is not null");
            return (Criteria) this;
        }

        public Criteria andFdyIdEqualTo(Integer value) {
            addCriterion("fdy_id =", value, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdNotEqualTo(Integer value) {
            addCriterion("fdy_id <>", value, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdGreaterThan(Integer value) {
            addCriterion("fdy_id >", value, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("fdy_id >=", value, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdLessThan(Integer value) {
            addCriterion("fdy_id <", value, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdLessThanOrEqualTo(Integer value) {
            addCriterion("fdy_id <=", value, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdIn(List<Integer> values) {
            addCriterion("fdy_id in", values, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdNotIn(List<Integer> values) {
            addCriterion("fdy_id not in", values, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdBetween(Integer value1, Integer value2) {
            addCriterion("fdy_id between", value1, value2, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("fdy_id not between", value1, value2, "fdyId");
            return (Criteria) this;
        }

        public Criteria andFdyNameIsNull() {
            addCriterion("fdy_name is null");
            return (Criteria) this;
        }

        public Criteria andFdyNameIsNotNull() {
            addCriterion("fdy_name is not null");
            return (Criteria) this;
        }

        public Criteria andFdyNameEqualTo(String value) {
            addCriterion("fdy_name =", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameNotEqualTo(String value) {
            addCriterion("fdy_name <>", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameGreaterThan(String value) {
            addCriterion("fdy_name >", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameGreaterThanOrEqualTo(String value) {
            addCriterion("fdy_name >=", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameLessThan(String value) {
            addCriterion("fdy_name <", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameLessThanOrEqualTo(String value) {
            addCriterion("fdy_name <=", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameLike(String value) {
            addCriterion("fdy_name like", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameNotLike(String value) {
            addCriterion("fdy_name not like", value, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameIn(List<String> values) {
            addCriterion("fdy_name in", values, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameNotIn(List<String> values) {
            addCriterion("fdy_name not in", values, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameBetween(String value1, String value2) {
            addCriterion("fdy_name between", value1, value2, "fdyName");
            return (Criteria) this;
        }

        public Criteria andFdyNameNotBetween(String value1, String value2) {
            addCriterion("fdy_name not between", value1, value2, "fdyName");
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

        public Criteria andGraduationTimeIsNull() {
            addCriterion("graduation_time is null");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeIsNotNull() {
            addCriterion("graduation_time is not null");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeEqualTo(Date value) {
            addCriterionForJDBCDate("graduation_time =", value, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("graduation_time <>", value, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("graduation_time >", value, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("graduation_time >=", value, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeLessThan(Date value) {
            addCriterionForJDBCDate("graduation_time <", value, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("graduation_time <=", value, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeIn(List<Date> values) {
            addCriterionForJDBCDate("graduation_time in", values, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("graduation_time not in", values, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("graduation_time between", value1, value2, "graduationTime");
            return (Criteria) this;
        }

        public Criteria andGraduationTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("graduation_time not between", value1, value2, "graduationTime");
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
package com.thinkjoy.enrollment.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EnrollStudentExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public EnrollStudentExample() {
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

        public Criteria andStudentIdIsNull() {
            addCriterion("student_id is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("student_id is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Integer value) {
            addCriterion("student_id =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Integer value) {
            addCriterion("student_id <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Integer value) {
            addCriterion("student_id >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("student_id >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Integer value) {
            addCriterion("student_id <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Integer value) {
            addCriterion("student_id <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Integer> values) {
            addCriterion("student_id in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Integer> values) {
            addCriterion("student_id not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Integer value1, Integer value2) {
            addCriterion("student_id between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("student_id not between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentNameIsNull() {
            addCriterion("student_name is null");
            return (Criteria) this;
        }

        public Criteria andStudentNameIsNotNull() {
            addCriterion("student_name is not null");
            return (Criteria) this;
        }

        public Criteria andStudentNameEqualTo(String value) {
            addCriterion("student_name =", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotEqualTo(String value) {
            addCriterion("student_name <>", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameGreaterThan(String value) {
            addCriterion("student_name >", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameGreaterThanOrEqualTo(String value) {
            addCriterion("student_name >=", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameLessThan(String value) {
            addCriterion("student_name <", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameLessThanOrEqualTo(String value) {
            addCriterion("student_name <=", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameLike(String value) {
            addCriterion("student_name like", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotLike(String value) {
            addCriterion("student_name not like", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameIn(List<String> values) {
            addCriterion("student_name in", values, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotIn(List<String> values) {
            addCriterion("student_name not in", values, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameBetween(String value1, String value2) {
            addCriterion("student_name between", value1, value2, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotBetween(String value1, String value2) {
            addCriterion("student_name not between", value1, value2, "studentName");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andOriginIsNull() {
            addCriterion("origin is null");
            return (Criteria) this;
        }

        public Criteria andOriginIsNotNull() {
            addCriterion("origin is not null");
            return (Criteria) this;
        }

        public Criteria andOriginEqualTo(String value) {
            addCriterion("origin =", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotEqualTo(String value) {
            addCriterion("origin <>", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThan(String value) {
            addCriterion("origin >", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThanOrEqualTo(String value) {
            addCriterion("origin >=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThan(String value) {
            addCriterion("origin <", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThanOrEqualTo(String value) {
            addCriterion("origin <=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLike(String value) {
            addCriterion("origin like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotLike(String value) {
            addCriterion("origin not like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginIn(List<String> values) {
            addCriterion("origin in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotIn(List<String> values) {
            addCriterion("origin not in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginBetween(String value1, String value2) {
            addCriterion("origin between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotBetween(String value1, String value2) {
            addCriterion("origin not between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andPoliticsIsNull() {
            addCriterion("politics is null");
            return (Criteria) this;
        }

        public Criteria andPoliticsIsNotNull() {
            addCriterion("politics is not null");
            return (Criteria) this;
        }

        public Criteria andPoliticsEqualTo(String value) {
            addCriterion("politics =", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsNotEqualTo(String value) {
            addCriterion("politics <>", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsGreaterThan(String value) {
            addCriterion("politics >", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsGreaterThanOrEqualTo(String value) {
            addCriterion("politics >=", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsLessThan(String value) {
            addCriterion("politics <", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsLessThanOrEqualTo(String value) {
            addCriterion("politics <=", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsLike(String value) {
            addCriterion("politics like", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsNotLike(String value) {
            addCriterion("politics not like", value, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsIn(List<String> values) {
            addCriterion("politics in", values, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsNotIn(List<String> values) {
            addCriterion("politics not in", values, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsBetween(String value1, String value2) {
            addCriterion("politics between", value1, value2, "politics");
            return (Criteria) this;
        }

        public Criteria andPoliticsNotBetween(String value1, String value2) {
            addCriterion("politics not between", value1, value2, "politics");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andDomicileIsNull() {
            addCriterion("domicile is null");
            return (Criteria) this;
        }

        public Criteria andDomicileIsNotNull() {
            addCriterion("domicile is not null");
            return (Criteria) this;
        }

        public Criteria andDomicileEqualTo(String value) {
            addCriterion("domicile =", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotEqualTo(String value) {
            addCriterion("domicile <>", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileGreaterThan(String value) {
            addCriterion("domicile >", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileGreaterThanOrEqualTo(String value) {
            addCriterion("domicile >=", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileLessThan(String value) {
            addCriterion("domicile <", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileLessThanOrEqualTo(String value) {
            addCriterion("domicile <=", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileLike(String value) {
            addCriterion("domicile like", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotLike(String value) {
            addCriterion("domicile not like", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileIn(List<String> values) {
            addCriterion("domicile in", values, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotIn(List<String> values) {
            addCriterion("domicile not in", values, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileBetween(String value1, String value2) {
            addCriterion("domicile between", value1, value2, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotBetween(String value1, String value2) {
            addCriterion("domicile not between", value1, value2, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeIsNull() {
            addCriterion("domicile_type is null");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeIsNotNull() {
            addCriterion("domicile_type is not null");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeEqualTo(String value) {
            addCriterion("domicile_type =", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeNotEqualTo(String value) {
            addCriterion("domicile_type <>", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeGreaterThan(String value) {
            addCriterion("domicile_type >", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeGreaterThanOrEqualTo(String value) {
            addCriterion("domicile_type >=", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeLessThan(String value) {
            addCriterion("domicile_type <", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeLessThanOrEqualTo(String value) {
            addCriterion("domicile_type <=", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeLike(String value) {
            addCriterion("domicile_type like", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeNotLike(String value) {
            addCriterion("domicile_type not like", value, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeIn(List<String> values) {
            addCriterion("domicile_type in", values, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeNotIn(List<String> values) {
            addCriterion("domicile_type not in", values, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeBetween(String value1, String value2) {
            addCriterion("domicile_type between", value1, value2, "domicileType");
            return (Criteria) this;
        }

        public Criteria andDomicileTypeNotBetween(String value1, String value2) {
            addCriterion("domicile_type not between", value1, value2, "domicileType");
            return (Criteria) this;
        }

        public Criteria andFromplaceIsNull() {
            addCriterion("fromplace is null");
            return (Criteria) this;
        }

        public Criteria andFromplaceIsNotNull() {
            addCriterion("fromplace is not null");
            return (Criteria) this;
        }

        public Criteria andFromplaceEqualTo(String value) {
            addCriterion("fromplace =", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceNotEqualTo(String value) {
            addCriterion("fromplace <>", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceGreaterThan(String value) {
            addCriterion("fromplace >", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceGreaterThanOrEqualTo(String value) {
            addCriterion("fromplace >=", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceLessThan(String value) {
            addCriterion("fromplace <", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceLessThanOrEqualTo(String value) {
            addCriterion("fromplace <=", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceLike(String value) {
            addCriterion("fromplace like", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceNotLike(String value) {
            addCriterion("fromplace not like", value, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceIn(List<String> values) {
            addCriterion("fromplace in", values, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceNotIn(List<String> values) {
            addCriterion("fromplace not in", values, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceBetween(String value1, String value2) {
            addCriterion("fromplace between", value1, value2, "fromplace");
            return (Criteria) this;
        }

        public Criteria andFromplaceNotBetween(String value1, String value2) {
            addCriterion("fromplace not between", value1, value2, "fromplace");
            return (Criteria) this;
        }

        public Criteria andIsPoorIsNull() {
            addCriterion("is_poor is null");
            return (Criteria) this;
        }

        public Criteria andIsPoorIsNotNull() {
            addCriterion("is_poor is not null");
            return (Criteria) this;
        }

        public Criteria andIsPoorEqualTo(Boolean value) {
            addCriterion("is_poor =", value, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorNotEqualTo(Boolean value) {
            addCriterion("is_poor <>", value, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorGreaterThan(Boolean value) {
            addCriterion("is_poor >", value, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_poor >=", value, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorLessThan(Boolean value) {
            addCriterion("is_poor <", value, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorLessThanOrEqualTo(Boolean value) {
            addCriterion("is_poor <=", value, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorIn(List<Boolean> values) {
            addCriterion("is_poor in", values, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorNotIn(List<Boolean> values) {
            addCriterion("is_poor not in", values, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorBetween(Boolean value1, Boolean value2) {
            addCriterion("is_poor between", value1, value2, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsPoorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_poor not between", value1, value2, "isPoor");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolIsNull() {
            addCriterion("is_liveschool is null");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolIsNotNull() {
            addCriterion("is_liveschool is not null");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolEqualTo(Boolean value) {
            addCriterion("is_liveschool =", value, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolNotEqualTo(Boolean value) {
            addCriterion("is_liveschool <>", value, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolGreaterThan(Boolean value) {
            addCriterion("is_liveschool >", value, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_liveschool >=", value, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolLessThan(Boolean value) {
            addCriterion("is_liveschool <", value, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolLessThanOrEqualTo(Boolean value) {
            addCriterion("is_liveschool <=", value, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolIn(List<Boolean> values) {
            addCriterion("is_liveschool in", values, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolNotIn(List<Boolean> values) {
            addCriterion("is_liveschool not in", values, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolBetween(Boolean value1, Boolean value2) {
            addCriterion("is_liveschool between", value1, value2, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andIsLiveschoolNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_liveschool not between", value1, value2, "isLiveschool");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andMailIsNull() {
            addCriterion("mail is null");
            return (Criteria) this;
        }

        public Criteria andMailIsNotNull() {
            addCriterion("mail is not null");
            return (Criteria) this;
        }

        public Criteria andMailEqualTo(String value) {
            addCriterion("mail =", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotEqualTo(String value) {
            addCriterion("mail <>", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThan(String value) {
            addCriterion("mail >", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThanOrEqualTo(String value) {
            addCriterion("mail >=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThan(String value) {
            addCriterion("mail <", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThanOrEqualTo(String value) {
            addCriterion("mail <=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLike(String value) {
            addCriterion("mail like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotLike(String value) {
            addCriterion("mail not like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailIn(List<String> values) {
            addCriterion("mail in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotIn(List<String> values) {
            addCriterion("mail not in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailBetween(String value1, String value2) {
            addCriterion("mail between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotBetween(String value1, String value2) {
            addCriterion("mail not between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneIsNull() {
            addCriterion("family_phone is null");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneIsNotNull() {
            addCriterion("family_phone is not null");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneEqualTo(String value) {
            addCriterion("family_phone =", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneNotEqualTo(String value) {
            addCriterion("family_phone <>", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneGreaterThan(String value) {
            addCriterion("family_phone >", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("family_phone >=", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneLessThan(String value) {
            addCriterion("family_phone <", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneLessThanOrEqualTo(String value) {
            addCriterion("family_phone <=", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneLike(String value) {
            addCriterion("family_phone like", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneNotLike(String value) {
            addCriterion("family_phone not like", value, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneIn(List<String> values) {
            addCriterion("family_phone in", values, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneNotIn(List<String> values) {
            addCriterion("family_phone not in", values, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneBetween(String value1, String value2) {
            addCriterion("family_phone between", value1, value2, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andFamilyPhoneNotBetween(String value1, String value2) {
            addCriterion("family_phone not between", value1, value2, "familyPhone");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andPostalCodeIsNull() {
            addCriterion("postal_code is null");
            return (Criteria) this;
        }

        public Criteria andPostalCodeIsNotNull() {
            addCriterion("postal_code is not null");
            return (Criteria) this;
        }

        public Criteria andPostalCodeEqualTo(String value) {
            addCriterion("postal_code =", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotEqualTo(String value) {
            addCriterion("postal_code <>", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeGreaterThan(String value) {
            addCriterion("postal_code >", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeGreaterThanOrEqualTo(String value) {
            addCriterion("postal_code >=", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeLessThan(String value) {
            addCriterion("postal_code <", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeLessThanOrEqualTo(String value) {
            addCriterion("postal_code <=", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeLike(String value) {
            addCriterion("postal_code like", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotLike(String value) {
            addCriterion("postal_code not like", value, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeIn(List<String> values) {
            addCriterion("postal_code in", values, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotIn(List<String> values) {
            addCriterion("postal_code not in", values, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeBetween(String value1, String value2) {
            addCriterion("postal_code between", value1, value2, "postalCode");
            return (Criteria) this;
        }

        public Criteria andPostalCodeNotBetween(String value1, String value2) {
            addCriterion("postal_code not between", value1, value2, "postalCode");
            return (Criteria) this;
        }

        public Criteria andGradSchoolIsNull() {
            addCriterion("grad_school is null");
            return (Criteria) this;
        }

        public Criteria andGradSchoolIsNotNull() {
            addCriterion("grad_school is not null");
            return (Criteria) this;
        }

        public Criteria andGradSchoolEqualTo(String value) {
            addCriterion("grad_school =", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolNotEqualTo(String value) {
            addCriterion("grad_school <>", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolGreaterThan(String value) {
            addCriterion("grad_school >", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("grad_school >=", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolLessThan(String value) {
            addCriterion("grad_school <", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolLessThanOrEqualTo(String value) {
            addCriterion("grad_school <=", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolLike(String value) {
            addCriterion("grad_school like", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolNotLike(String value) {
            addCriterion("grad_school not like", value, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolIn(List<String> values) {
            addCriterion("grad_school in", values, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolNotIn(List<String> values) {
            addCriterion("grad_school not in", values, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolBetween(String value1, String value2) {
            addCriterion("grad_school between", value1, value2, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andGradSchoolNotBetween(String value1, String value2) {
            addCriterion("grad_school not between", value1, value2, "gradSchool");
            return (Criteria) this;
        }

        public Criteria andStudentTypeIsNull() {
            addCriterion("student_type is null");
            return (Criteria) this;
        }

        public Criteria andStudentTypeIsNotNull() {
            addCriterion("student_type is not null");
            return (Criteria) this;
        }

        public Criteria andStudentTypeEqualTo(String value) {
            addCriterion("student_type =", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeNotEqualTo(String value) {
            addCriterion("student_type <>", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeGreaterThan(String value) {
            addCriterion("student_type >", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("student_type >=", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeLessThan(String value) {
            addCriterion("student_type <", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeLessThanOrEqualTo(String value) {
            addCriterion("student_type <=", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeLike(String value) {
            addCriterion("student_type like", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeNotLike(String value) {
            addCriterion("student_type not like", value, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeIn(List<String> values) {
            addCriterion("student_type in", values, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeNotIn(List<String> values) {
            addCriterion("student_type not in", values, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeBetween(String value1, String value2) {
            addCriterion("student_type between", value1, value2, "studentType");
            return (Criteria) this;
        }

        public Criteria andStudentTypeNotBetween(String value1, String value2) {
            addCriterion("student_type not between", value1, value2, "studentType");
            return (Criteria) this;
        }

        public Criteria andGradTimeIsNull() {
            addCriterion("grad_time is null");
            return (Criteria) this;
        }

        public Criteria andGradTimeIsNotNull() {
            addCriterion("grad_time is not null");
            return (Criteria) this;
        }

        public Criteria andGradTimeEqualTo(Date value) {
            addCriterionForJDBCDate("grad_time =", value, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("grad_time <>", value, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("grad_time >", value, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grad_time >=", value, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeLessThan(Date value) {
            addCriterionForJDBCDate("grad_time <", value, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grad_time <=", value, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeIn(List<Date> values) {
            addCriterionForJDBCDate("grad_time in", values, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("grad_time not in", values, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grad_time between", value1, value2, "gradTime");
            return (Criteria) this;
        }

        public Criteria andGradTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grad_time not between", value1, value2, "gradTime");
            return (Criteria) this;
        }

        public Criteria andBatchIdIsNull() {
            addCriterion("batch_id is null");
            return (Criteria) this;
        }

        public Criteria andBatchIdIsNotNull() {
            addCriterion("batch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBatchIdEqualTo(Integer value) {
            addCriterion("batch_id =", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdNotEqualTo(Integer value) {
            addCriterion("batch_id <>", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdGreaterThan(Integer value) {
            addCriterion("batch_id >", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("batch_id >=", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdLessThan(Integer value) {
            addCriterion("batch_id <", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("batch_id <=", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdIn(List<Integer> values) {
            addCriterion("batch_id in", values, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdNotIn(List<Integer> values) {
            addCriterion("batch_id not in", values, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdBetween(Integer value1, Integer value2) {
            addCriterion("batch_id between", value1, value2, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("batch_id not between", value1, value2, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchNameIsNull() {
            addCriterion("batch_name is null");
            return (Criteria) this;
        }

        public Criteria andBatchNameIsNotNull() {
            addCriterion("batch_name is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNameEqualTo(String value) {
            addCriterion("batch_name =", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameNotEqualTo(String value) {
            addCriterion("batch_name <>", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameGreaterThan(String value) {
            addCriterion("batch_name >", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameGreaterThanOrEqualTo(String value) {
            addCriterion("batch_name >=", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameLessThan(String value) {
            addCriterion("batch_name <", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameLessThanOrEqualTo(String value) {
            addCriterion("batch_name <=", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameLike(String value) {
            addCriterion("batch_name like", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameNotLike(String value) {
            addCriterion("batch_name not like", value, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameIn(List<String> values) {
            addCriterion("batch_name in", values, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameNotIn(List<String> values) {
            addCriterion("batch_name not in", values, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameBetween(String value1, String value2) {
            addCriterion("batch_name between", value1, value2, "batchName");
            return (Criteria) this;
        }

        public Criteria andBatchNameNotBetween(String value1, String value2) {
            addCriterion("batch_name not between", value1, value2, "batchName");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdIsNull() {
            addCriterion("enrollteacher_id is null");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdIsNotNull() {
            addCriterion("enrollteacher_id is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdEqualTo(Integer value) {
            addCriterion("enrollteacher_id =", value, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdNotEqualTo(Integer value) {
            addCriterion("enrollteacher_id <>", value, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdGreaterThan(Integer value) {
            addCriterion("enrollteacher_id >", value, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("enrollteacher_id >=", value, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdLessThan(Integer value) {
            addCriterion("enrollteacher_id <", value, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdLessThanOrEqualTo(Integer value) {
            addCriterion("enrollteacher_id <=", value, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdIn(List<Integer> values) {
            addCriterion("enrollteacher_id in", values, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdNotIn(List<Integer> values) {
            addCriterion("enrollteacher_id not in", values, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdBetween(Integer value1, Integer value2) {
            addCriterion("enrollteacher_id between", value1, value2, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andEnrollteacherIdNotBetween(Integer value1, Integer value2) {
            addCriterion("enrollteacher_id not between", value1, value2, "enrollteacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherNameIsNull() {
            addCriterion("teacher_name is null");
            return (Criteria) this;
        }

        public Criteria andTeacherNameIsNotNull() {
            addCriterion("teacher_name is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherNameEqualTo(String value) {
            addCriterion("teacher_name =", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameNotEqualTo(String value) {
            addCriterion("teacher_name <>", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameGreaterThan(String value) {
            addCriterion("teacher_name >", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameGreaterThanOrEqualTo(String value) {
            addCriterion("teacher_name >=", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameLessThan(String value) {
            addCriterion("teacher_name <", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameLessThanOrEqualTo(String value) {
            addCriterion("teacher_name <=", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameLike(String value) {
            addCriterion("teacher_name like", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameNotLike(String value) {
            addCriterion("teacher_name not like", value, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameIn(List<String> values) {
            addCriterion("teacher_name in", values, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameNotIn(List<String> values) {
            addCriterion("teacher_name not in", values, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameBetween(String value1, String value2) {
            addCriterion("teacher_name between", value1, value2, "teacherName");
            return (Criteria) this;
        }

        public Criteria andTeacherNameNotBetween(String value1, String value2) {
            addCriterion("teacher_name not between", value1, value2, "teacherName");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherIsNull() {
            addCriterion("grad_headteacher is null");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherIsNotNull() {
            addCriterion("grad_headteacher is not null");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherEqualTo(String value) {
            addCriterion("grad_headteacher =", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherNotEqualTo(String value) {
            addCriterion("grad_headteacher <>", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherGreaterThan(String value) {
            addCriterion("grad_headteacher >", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherGreaterThanOrEqualTo(String value) {
            addCriterion("grad_headteacher >=", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherLessThan(String value) {
            addCriterion("grad_headteacher <", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherLessThanOrEqualTo(String value) {
            addCriterion("grad_headteacher <=", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherLike(String value) {
            addCriterion("grad_headteacher like", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherNotLike(String value) {
            addCriterion("grad_headteacher not like", value, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherIn(List<String> values) {
            addCriterion("grad_headteacher in", values, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherNotIn(List<String> values) {
            addCriterion("grad_headteacher not in", values, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherBetween(String value1, String value2) {
            addCriterion("grad_headteacher between", value1, value2, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andGradHeadteacherNotBetween(String value1, String value2) {
            addCriterion("grad_headteacher not between", value1, value2, "gradHeadteacher");
            return (Criteria) this;
        }

        public Criteria andExamnumIsNull() {
            addCriterion("examnum is null");
            return (Criteria) this;
        }

        public Criteria andExamnumIsNotNull() {
            addCriterion("examnum is not null");
            return (Criteria) this;
        }

        public Criteria andExamnumEqualTo(String value) {
            addCriterion("examnum =", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumNotEqualTo(String value) {
            addCriterion("examnum <>", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumGreaterThan(String value) {
            addCriterion("examnum >", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumGreaterThanOrEqualTo(String value) {
            addCriterion("examnum >=", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumLessThan(String value) {
            addCriterion("examnum <", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumLessThanOrEqualTo(String value) {
            addCriterion("examnum <=", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumLike(String value) {
            addCriterion("examnum like", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumNotLike(String value) {
            addCriterion("examnum not like", value, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumIn(List<String> values) {
            addCriterion("examnum in", values, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumNotIn(List<String> values) {
            addCriterion("examnum not in", values, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumBetween(String value1, String value2) {
            addCriterion("examnum between", value1, value2, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamnumNotBetween(String value1, String value2) {
            addCriterion("examnum not between", value1, value2, "examnum");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberIsNull() {
            addCriterion("examinee_number is null");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberIsNotNull() {
            addCriterion("examinee_number is not null");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberEqualTo(String value) {
            addCriterion("examinee_number =", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberNotEqualTo(String value) {
            addCriterion("examinee_number <>", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberGreaterThan(String value) {
            addCriterion("examinee_number >", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberGreaterThanOrEqualTo(String value) {
            addCriterion("examinee_number >=", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberLessThan(String value) {
            addCriterion("examinee_number <", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberLessThanOrEqualTo(String value) {
            addCriterion("examinee_number <=", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberLike(String value) {
            addCriterion("examinee_number like", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberNotLike(String value) {
            addCriterion("examinee_number not like", value, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberIn(List<String> values) {
            addCriterion("examinee_number in", values, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberNotIn(List<String> values) {
            addCriterion("examinee_number not in", values, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberBetween(String value1, String value2) {
            addCriterion("examinee_number between", value1, value2, "examineeNumber");
            return (Criteria) this;
        }

        public Criteria andExamineeNumberNotBetween(String value1, String value2) {
            addCriterion("examinee_number not between", value1, value2, "examineeNumber");
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

        public Criteria andFirstVolcodeIsNull() {
            addCriterion("first_volcode is null");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeIsNotNull() {
            addCriterion("first_volcode is not null");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeEqualTo(String value) {
            addCriterion("first_volcode =", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeNotEqualTo(String value) {
            addCriterion("first_volcode <>", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeGreaterThan(String value) {
            addCriterion("first_volcode >", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeGreaterThanOrEqualTo(String value) {
            addCriterion("first_volcode >=", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeLessThan(String value) {
            addCriterion("first_volcode <", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeLessThanOrEqualTo(String value) {
            addCriterion("first_volcode <=", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeLike(String value) {
            addCriterion("first_volcode like", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeNotLike(String value) {
            addCriterion("first_volcode not like", value, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeIn(List<String> values) {
            addCriterion("first_volcode in", values, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeNotIn(List<String> values) {
            addCriterion("first_volcode not in", values, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeBetween(String value1, String value2) {
            addCriterion("first_volcode between", value1, value2, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolcodeNotBetween(String value1, String value2) {
            addCriterion("first_volcode not between", value1, value2, "firstVolcode");
            return (Criteria) this;
        }

        public Criteria andFirstVolIsNull() {
            addCriterion("first_vol is null");
            return (Criteria) this;
        }

        public Criteria andFirstVolIsNotNull() {
            addCriterion("first_vol is not null");
            return (Criteria) this;
        }

        public Criteria andFirstVolEqualTo(String value) {
            addCriterion("first_vol =", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolNotEqualTo(String value) {
            addCriterion("first_vol <>", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolGreaterThan(String value) {
            addCriterion("first_vol >", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolGreaterThanOrEqualTo(String value) {
            addCriterion("first_vol >=", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolLessThan(String value) {
            addCriterion("first_vol <", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolLessThanOrEqualTo(String value) {
            addCriterion("first_vol <=", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolLike(String value) {
            addCriterion("first_vol like", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolNotLike(String value) {
            addCriterion("first_vol not like", value, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolIn(List<String> values) {
            addCriterion("first_vol in", values, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolNotIn(List<String> values) {
            addCriterion("first_vol not in", values, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolBetween(String value1, String value2) {
            addCriterion("first_vol between", value1, value2, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstVolNotBetween(String value1, String value2) {
            addCriterion("first_vol not between", value1, value2, "firstVol");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameIsNull() {
            addCriterion("first_trdrname is null");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameIsNotNull() {
            addCriterion("first_trdrname is not null");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameEqualTo(String value) {
            addCriterion("first_trdrname =", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameNotEqualTo(String value) {
            addCriterion("first_trdrname <>", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameGreaterThan(String value) {
            addCriterion("first_trdrname >", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameGreaterThanOrEqualTo(String value) {
            addCriterion("first_trdrname >=", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameLessThan(String value) {
            addCriterion("first_trdrname <", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameLessThanOrEqualTo(String value) {
            addCriterion("first_trdrname <=", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameLike(String value) {
            addCriterion("first_trdrname like", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameNotLike(String value) {
            addCriterion("first_trdrname not like", value, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameIn(List<String> values) {
            addCriterion("first_trdrname in", values, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameNotIn(List<String> values) {
            addCriterion("first_trdrname not in", values, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameBetween(String value1, String value2) {
            addCriterion("first_trdrname between", value1, value2, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstTrdrnameNotBetween(String value1, String value2) {
            addCriterion("first_trdrname not between", value1, value2, "firstTrdrname");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysIsNull() {
            addCriterion("first_schsys is null");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysIsNotNull() {
            addCriterion("first_schsys is not null");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysEqualTo(String value) {
            addCriterion("first_schsys =", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysNotEqualTo(String value) {
            addCriterion("first_schsys <>", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysGreaterThan(String value) {
            addCriterion("first_schsys >", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysGreaterThanOrEqualTo(String value) {
            addCriterion("first_schsys >=", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysLessThan(String value) {
            addCriterion("first_schsys <", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysLessThanOrEqualTo(String value) {
            addCriterion("first_schsys <=", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysLike(String value) {
            addCriterion("first_schsys like", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysNotLike(String value) {
            addCriterion("first_schsys not like", value, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysIn(List<String> values) {
            addCriterion("first_schsys in", values, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysNotIn(List<String> values) {
            addCriterion("first_schsys not in", values, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysBetween(String value1, String value2) {
            addCriterion("first_schsys between", value1, value2, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstSchsysNotBetween(String value1, String value2) {
            addCriterion("first_schsys not between", value1, value2, "firstSchsys");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeIsNull() {
            addCriterion("first_facultycode is null");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeIsNotNull() {
            addCriterion("first_facultycode is not null");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeEqualTo(String value) {
            addCriterion("first_facultycode =", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeNotEqualTo(String value) {
            addCriterion("first_facultycode <>", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeGreaterThan(String value) {
            addCriterion("first_facultycode >", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeGreaterThanOrEqualTo(String value) {
            addCriterion("first_facultycode >=", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeLessThan(String value) {
            addCriterion("first_facultycode <", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeLessThanOrEqualTo(String value) {
            addCriterion("first_facultycode <=", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeLike(String value) {
            addCriterion("first_facultycode like", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeNotLike(String value) {
            addCriterion("first_facultycode not like", value, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeIn(List<String> values) {
            addCriterion("first_facultycode in", values, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeNotIn(List<String> values) {
            addCriterion("first_facultycode not in", values, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeBetween(String value1, String value2) {
            addCriterion("first_facultycode between", value1, value2, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultycodeNotBetween(String value1, String value2) {
            addCriterion("first_facultycode not between", value1, value2, "firstFacultycode");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameIsNull() {
            addCriterion("first_facultyname is null");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameIsNotNull() {
            addCriterion("first_facultyname is not null");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameEqualTo(String value) {
            addCriterion("first_facultyname =", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameNotEqualTo(String value) {
            addCriterion("first_facultyname <>", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameGreaterThan(String value) {
            addCriterion("first_facultyname >", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameGreaterThanOrEqualTo(String value) {
            addCriterion("first_facultyname >=", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameLessThan(String value) {
            addCriterion("first_facultyname <", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameLessThanOrEqualTo(String value) {
            addCriterion("first_facultyname <=", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameLike(String value) {
            addCriterion("first_facultyname like", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameNotLike(String value) {
            addCriterion("first_facultyname not like", value, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameIn(List<String> values) {
            addCriterion("first_facultyname in", values, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameNotIn(List<String> values) {
            addCriterion("first_facultyname not in", values, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameBetween(String value1, String value2) {
            addCriterion("first_facultyname between", value1, value2, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andFirstFacultynameNotBetween(String value1, String value2) {
            addCriterion("first_facultyname not between", value1, value2, "firstFacultyname");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeIsNull() {
            addCriterion("second_volcode is null");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeIsNotNull() {
            addCriterion("second_volcode is not null");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeEqualTo(String value) {
            addCriterion("second_volcode =", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeNotEqualTo(String value) {
            addCriterion("second_volcode <>", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeGreaterThan(String value) {
            addCriterion("second_volcode >", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeGreaterThanOrEqualTo(String value) {
            addCriterion("second_volcode >=", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeLessThan(String value) {
            addCriterion("second_volcode <", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeLessThanOrEqualTo(String value) {
            addCriterion("second_volcode <=", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeLike(String value) {
            addCriterion("second_volcode like", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeNotLike(String value) {
            addCriterion("second_volcode not like", value, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeIn(List<String> values) {
            addCriterion("second_volcode in", values, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeNotIn(List<String> values) {
            addCriterion("second_volcode not in", values, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeBetween(String value1, String value2) {
            addCriterion("second_volcode between", value1, value2, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolcodeNotBetween(String value1, String value2) {
            addCriterion("second_volcode not between", value1, value2, "secondVolcode");
            return (Criteria) this;
        }

        public Criteria andSecondVolIsNull() {
            addCriterion("second_vol is null");
            return (Criteria) this;
        }

        public Criteria andSecondVolIsNotNull() {
            addCriterion("second_vol is not null");
            return (Criteria) this;
        }

        public Criteria andSecondVolEqualTo(String value) {
            addCriterion("second_vol =", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolNotEqualTo(String value) {
            addCriterion("second_vol <>", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolGreaterThan(String value) {
            addCriterion("second_vol >", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolGreaterThanOrEqualTo(String value) {
            addCriterion("second_vol >=", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolLessThan(String value) {
            addCriterion("second_vol <", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolLessThanOrEqualTo(String value) {
            addCriterion("second_vol <=", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolLike(String value) {
            addCriterion("second_vol like", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolNotLike(String value) {
            addCriterion("second_vol not like", value, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolIn(List<String> values) {
            addCriterion("second_vol in", values, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolNotIn(List<String> values) {
            addCriterion("second_vol not in", values, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolBetween(String value1, String value2) {
            addCriterion("second_vol between", value1, value2, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondVolNotBetween(String value1, String value2) {
            addCriterion("second_vol not between", value1, value2, "secondVol");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameIsNull() {
            addCriterion("second_trdrname is null");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameIsNotNull() {
            addCriterion("second_trdrname is not null");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameEqualTo(String value) {
            addCriterion("second_trdrname =", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameNotEqualTo(String value) {
            addCriterion("second_trdrname <>", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameGreaterThan(String value) {
            addCriterion("second_trdrname >", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameGreaterThanOrEqualTo(String value) {
            addCriterion("second_trdrname >=", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameLessThan(String value) {
            addCriterion("second_trdrname <", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameLessThanOrEqualTo(String value) {
            addCriterion("second_trdrname <=", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameLike(String value) {
            addCriterion("second_trdrname like", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameNotLike(String value) {
            addCriterion("second_trdrname not like", value, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameIn(List<String> values) {
            addCriterion("second_trdrname in", values, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameNotIn(List<String> values) {
            addCriterion("second_trdrname not in", values, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameBetween(String value1, String value2) {
            addCriterion("second_trdrname between", value1, value2, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondTrdrnameNotBetween(String value1, String value2) {
            addCriterion("second_trdrname not between", value1, value2, "secondTrdrname");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysIsNull() {
            addCriterion("second_schsys is null");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysIsNotNull() {
            addCriterion("second_schsys is not null");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysEqualTo(String value) {
            addCriterion("second_schsys =", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysNotEqualTo(String value) {
            addCriterion("second_schsys <>", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysGreaterThan(String value) {
            addCriterion("second_schsys >", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysGreaterThanOrEqualTo(String value) {
            addCriterion("second_schsys >=", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysLessThan(String value) {
            addCriterion("second_schsys <", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysLessThanOrEqualTo(String value) {
            addCriterion("second_schsys <=", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysLike(String value) {
            addCriterion("second_schsys like", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysNotLike(String value) {
            addCriterion("second_schsys not like", value, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysIn(List<String> values) {
            addCriterion("second_schsys in", values, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysNotIn(List<String> values) {
            addCriterion("second_schsys not in", values, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysBetween(String value1, String value2) {
            addCriterion("second_schsys between", value1, value2, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondSchsysNotBetween(String value1, String value2) {
            addCriterion("second_schsys not between", value1, value2, "secondSchsys");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeIsNull() {
            addCriterion("second_facultycode is null");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeIsNotNull() {
            addCriterion("second_facultycode is not null");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeEqualTo(String value) {
            addCriterion("second_facultycode =", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeNotEqualTo(String value) {
            addCriterion("second_facultycode <>", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeGreaterThan(String value) {
            addCriterion("second_facultycode >", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeGreaterThanOrEqualTo(String value) {
            addCriterion("second_facultycode >=", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeLessThan(String value) {
            addCriterion("second_facultycode <", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeLessThanOrEqualTo(String value) {
            addCriterion("second_facultycode <=", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeLike(String value) {
            addCriterion("second_facultycode like", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeNotLike(String value) {
            addCriterion("second_facultycode not like", value, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeIn(List<String> values) {
            addCriterion("second_facultycode in", values, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeNotIn(List<String> values) {
            addCriterion("second_facultycode not in", values, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeBetween(String value1, String value2) {
            addCriterion("second_facultycode between", value1, value2, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultycodeNotBetween(String value1, String value2) {
            addCriterion("second_facultycode not between", value1, value2, "secondFacultycode");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameIsNull() {
            addCriterion("second_facultname is null");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameIsNotNull() {
            addCriterion("second_facultname is not null");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameEqualTo(String value) {
            addCriterion("second_facultname =", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameNotEqualTo(String value) {
            addCriterion("second_facultname <>", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameGreaterThan(String value) {
            addCriterion("second_facultname >", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameGreaterThanOrEqualTo(String value) {
            addCriterion("second_facultname >=", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameLessThan(String value) {
            addCriterion("second_facultname <", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameLessThanOrEqualTo(String value) {
            addCriterion("second_facultname <=", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameLike(String value) {
            addCriterion("second_facultname like", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameNotLike(String value) {
            addCriterion("second_facultname not like", value, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameIn(List<String> values) {
            addCriterion("second_facultname in", values, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameNotIn(List<String> values) {
            addCriterion("second_facultname not in", values, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameBetween(String value1, String value2) {
            addCriterion("second_facultname between", value1, value2, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andSecondFacultnameNotBetween(String value1, String value2) {
            addCriterion("second_facultname not between", value1, value2, "secondFacultname");
            return (Criteria) this;
        }

        public Criteria andEnrollVolIsNull() {
            addCriterion("enroll_vol is null");
            return (Criteria) this;
        }

        public Criteria andEnrollVolIsNotNull() {
            addCriterion("enroll_vol is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollVolEqualTo(String value) {
            addCriterion("enroll_vol =", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolNotEqualTo(String value) {
            addCriterion("enroll_vol <>", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolGreaterThan(String value) {
            addCriterion("enroll_vol >", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolGreaterThanOrEqualTo(String value) {
            addCriterion("enroll_vol >=", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolLessThan(String value) {
            addCriterion("enroll_vol <", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolLessThanOrEqualTo(String value) {
            addCriterion("enroll_vol <=", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolLike(String value) {
            addCriterion("enroll_vol like", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolNotLike(String value) {
            addCriterion("enroll_vol not like", value, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolIn(List<String> values) {
            addCriterion("enroll_vol in", values, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolNotIn(List<String> values) {
            addCriterion("enroll_vol not in", values, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolBetween(String value1, String value2) {
            addCriterion("enroll_vol between", value1, value2, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andEnrollVolNotBetween(String value1, String value2) {
            addCriterion("enroll_vol not between", value1, value2, "enrollVol");
            return (Criteria) this;
        }

        public Criteria andBatchYearIsNull() {
            addCriterion("batch_year is null");
            return (Criteria) this;
        }

        public Criteria andBatchYearIsNotNull() {
            addCriterion("batch_year is not null");
            return (Criteria) this;
        }

        public Criteria andBatchYearEqualTo(Integer value) {
            addCriterion("batch_year =", value, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearNotEqualTo(Integer value) {
            addCriterion("batch_year <>", value, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearGreaterThan(Integer value) {
            addCriterion("batch_year >", value, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("batch_year >=", value, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearLessThan(Integer value) {
            addCriterion("batch_year <", value, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearLessThanOrEqualTo(Integer value) {
            addCriterion("batch_year <=", value, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearIn(List<Integer> values) {
            addCriterion("batch_year in", values, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearNotIn(List<Integer> values) {
            addCriterion("batch_year not in", values, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearBetween(Integer value1, Integer value2) {
            addCriterion("batch_year between", value1, value2, "batchYear");
            return (Criteria) this;
        }

        public Criteria andBatchYearNotBetween(Integer value1, Integer value2) {
            addCriterion("batch_year not between", value1, value2, "batchYear");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlIsNull() {
            addCriterion("notice_url is null");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlIsNotNull() {
            addCriterion("notice_url is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlEqualTo(String value) {
            addCriterion("notice_url =", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlNotEqualTo(String value) {
            addCriterion("notice_url <>", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlGreaterThan(String value) {
            addCriterion("notice_url >", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlGreaterThanOrEqualTo(String value) {
            addCriterion("notice_url >=", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlLessThan(String value) {
            addCriterion("notice_url <", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlLessThanOrEqualTo(String value) {
            addCriterion("notice_url <=", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlLike(String value) {
            addCriterion("notice_url like", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlNotLike(String value) {
            addCriterion("notice_url not like", value, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlIn(List<String> values) {
            addCriterion("notice_url in", values, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlNotIn(List<String> values) {
            addCriterion("notice_url not in", values, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlBetween(String value1, String value2) {
            addCriterion("notice_url between", value1, value2, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andNoticeUrlNotBetween(String value1, String value2) {
            addCriterion("notice_url not between", value1, value2, "noticeUrl");
            return (Criteria) this;
        }

        public Criteria andFeeStatusIsNull() {
            addCriterion("fee_status is null");
            return (Criteria) this;
        }

        public Criteria andFeeStatusIsNotNull() {
            addCriterion("fee_status is not null");
            return (Criteria) this;
        }

        public Criteria andFeeStatusEqualTo(String value) {
            addCriterion("fee_status =", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusNotEqualTo(String value) {
            addCriterion("fee_status <>", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusGreaterThan(String value) {
            addCriterion("fee_status >", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusGreaterThanOrEqualTo(String value) {
            addCriterion("fee_status >=", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusLessThan(String value) {
            addCriterion("fee_status <", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusLessThanOrEqualTo(String value) {
            addCriterion("fee_status <=", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusLike(String value) {
            addCriterion("fee_status like", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusNotLike(String value) {
            addCriterion("fee_status not like", value, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusIn(List<String> values) {
            addCriterion("fee_status in", values, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusNotIn(List<String> values) {
            addCriterion("fee_status not in", values, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusBetween(String value1, String value2) {
            addCriterion("fee_status between", value1, value2, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andFeeStatusNotBetween(String value1, String value2) {
            addCriterion("fee_status not between", value1, value2, "feeStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIsNull() {
            addCriterion("enroll__status is null");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIsNotNull() {
            addCriterion("enroll__status is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusEqualTo(String value) {
            addCriterion("enroll__status =", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotEqualTo(String value) {
            addCriterion("enroll__status <>", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusGreaterThan(String value) {
            addCriterion("enroll__status >", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusGreaterThanOrEqualTo(String value) {
            addCriterion("enroll__status >=", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusLessThan(String value) {
            addCriterion("enroll__status <", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusLessThanOrEqualTo(String value) {
            addCriterion("enroll__status <=", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusLike(String value) {
            addCriterion("enroll__status like", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotLike(String value) {
            addCriterion("enroll__status not like", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIn(List<String> values) {
            addCriterion("enroll__status in", values, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotIn(List<String> values) {
            addCriterion("enroll__status not in", values, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusBetween(String value1, String value2) {
            addCriterion("enroll__status between", value1, value2, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotBetween(String value1, String value2) {
            addCriterion("enroll__status not between", value1, value2, "enrollStatus");
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
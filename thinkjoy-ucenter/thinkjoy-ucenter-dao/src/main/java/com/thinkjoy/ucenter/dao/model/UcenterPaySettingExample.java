package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UcenterPaySettingExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public UcenterPaySettingExample() {
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

        public Criteria andPaysettingIdIsNull() {
            addCriterion("paysetting_id is null");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdIsNotNull() {
            addCriterion("paysetting_id is not null");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdEqualTo(Integer value) {
            addCriterion("paysetting_id =", value, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdNotEqualTo(Integer value) {
            addCriterion("paysetting_id <>", value, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdGreaterThan(Integer value) {
            addCriterion("paysetting_id >", value, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("paysetting_id >=", value, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdLessThan(Integer value) {
            addCriterion("paysetting_id <", value, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdLessThanOrEqualTo(Integer value) {
            addCriterion("paysetting_id <=", value, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdIn(List<Integer> values) {
            addCriterion("paysetting_id in", values, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdNotIn(List<Integer> values) {
            addCriterion("paysetting_id not in", values, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdBetween(Integer value1, Integer value2) {
            addCriterion("paysetting_id between", value1, value2, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andPaysettingIdNotBetween(Integer value1, Integer value2) {
            addCriterion("paysetting_id not between", value1, value2, "paysettingId");
            return (Criteria) this;
        }

        public Criteria andMchNameIsNull() {
            addCriterion("mch_name is null");
            return (Criteria) this;
        }

        public Criteria andMchNameIsNotNull() {
            addCriterion("mch_name is not null");
            return (Criteria) this;
        }

        public Criteria andMchNameEqualTo(String value) {
            addCriterion("mch_name =", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameNotEqualTo(String value) {
            addCriterion("mch_name <>", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameGreaterThan(String value) {
            addCriterion("mch_name >", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameGreaterThanOrEqualTo(String value) {
            addCriterion("mch_name >=", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameLessThan(String value) {
            addCriterion("mch_name <", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameLessThanOrEqualTo(String value) {
            addCriterion("mch_name <=", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameLike(String value) {
            addCriterion("mch_name like", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameNotLike(String value) {
            addCriterion("mch_name not like", value, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameIn(List<String> values) {
            addCriterion("mch_name in", values, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameNotIn(List<String> values) {
            addCriterion("mch_name not in", values, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameBetween(String value1, String value2) {
            addCriterion("mch_name between", value1, value2, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchNameNotBetween(String value1, String value2) {
            addCriterion("mch_name not between", value1, value2, "mchName");
            return (Criteria) this;
        }

        public Criteria andMchIdIsNull() {
            addCriterion("mch_id is null");
            return (Criteria) this;
        }

        public Criteria andMchIdIsNotNull() {
            addCriterion("mch_id is not null");
            return (Criteria) this;
        }

        public Criteria andMchIdEqualTo(String value) {
            addCriterion("mch_id =", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotEqualTo(String value) {
            addCriterion("mch_id <>", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdGreaterThan(String value) {
            addCriterion("mch_id >", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdGreaterThanOrEqualTo(String value) {
            addCriterion("mch_id >=", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdLessThan(String value) {
            addCriterion("mch_id <", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdLessThanOrEqualTo(String value) {
            addCriterion("mch_id <=", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdLike(String value) {
            addCriterion("mch_id like", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotLike(String value) {
            addCriterion("mch_id not like", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdIn(List<String> values) {
            addCriterion("mch_id in", values, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotIn(List<String> values) {
            addCriterion("mch_id not in", values, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdBetween(String value1, String value2) {
            addCriterion("mch_id between", value1, value2, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotBetween(String value1, String value2) {
            addCriterion("mch_id not between", value1, value2, "mchId");
            return (Criteria) this;
        }

        public Criteria andMckKeyIsNull() {
            addCriterion("mck_key is null");
            return (Criteria) this;
        }

        public Criteria andMckKeyIsNotNull() {
            addCriterion("mck_key is not null");
            return (Criteria) this;
        }

        public Criteria andMckKeyEqualTo(String value) {
            addCriterion("mck_key =", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyNotEqualTo(String value) {
            addCriterion("mck_key <>", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyGreaterThan(String value) {
            addCriterion("mck_key >", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyGreaterThanOrEqualTo(String value) {
            addCriterion("mck_key >=", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyLessThan(String value) {
            addCriterion("mck_key <", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyLessThanOrEqualTo(String value) {
            addCriterion("mck_key <=", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyLike(String value) {
            addCriterion("mck_key like", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyNotLike(String value) {
            addCriterion("mck_key not like", value, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyIn(List<String> values) {
            addCriterion("mck_key in", values, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyNotIn(List<String> values) {
            addCriterion("mck_key not in", values, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyBetween(String value1, String value2) {
            addCriterion("mck_key between", value1, value2, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMckKeyNotBetween(String value1, String value2) {
            addCriterion("mck_key not between", value1, value2, "mckKey");
            return (Criteria) this;
        }

        public Criteria andMchBackurlIsNull() {
            addCriterion("mch_backurl is null");
            return (Criteria) this;
        }

        public Criteria andMchBackurlIsNotNull() {
            addCriterion("mch_backurl is not null");
            return (Criteria) this;
        }

        public Criteria andMchBackurlEqualTo(String value) {
            addCriterion("mch_backurl =", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlNotEqualTo(String value) {
            addCriterion("mch_backurl <>", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlGreaterThan(String value) {
            addCriterion("mch_backurl >", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlGreaterThanOrEqualTo(String value) {
            addCriterion("mch_backurl >=", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlLessThan(String value) {
            addCriterion("mch_backurl <", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlLessThanOrEqualTo(String value) {
            addCriterion("mch_backurl <=", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlLike(String value) {
            addCriterion("mch_backurl like", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlNotLike(String value) {
            addCriterion("mch_backurl not like", value, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlIn(List<String> values) {
            addCriterion("mch_backurl in", values, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlNotIn(List<String> values) {
            addCriterion("mch_backurl not in", values, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlBetween(String value1, String value2) {
            addCriterion("mch_backurl between", value1, value2, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchBackurlNotBetween(String value1, String value2) {
            addCriterion("mch_backurl not between", value1, value2, "mchBackurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlIsNull() {
            addCriterion("mch_pageurl is null");
            return (Criteria) this;
        }

        public Criteria andMchPageurlIsNotNull() {
            addCriterion("mch_pageurl is not null");
            return (Criteria) this;
        }

        public Criteria andMchPageurlEqualTo(String value) {
            addCriterion("mch_pageurl =", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlNotEqualTo(String value) {
            addCriterion("mch_pageurl <>", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlGreaterThan(String value) {
            addCriterion("mch_pageurl >", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlGreaterThanOrEqualTo(String value) {
            addCriterion("mch_pageurl >=", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlLessThan(String value) {
            addCriterion("mch_pageurl <", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlLessThanOrEqualTo(String value) {
            addCriterion("mch_pageurl <=", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlLike(String value) {
            addCriterion("mch_pageurl like", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlNotLike(String value) {
            addCriterion("mch_pageurl not like", value, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlIn(List<String> values) {
            addCriterion("mch_pageurl in", values, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlNotIn(List<String> values) {
            addCriterion("mch_pageurl not in", values, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlBetween(String value1, String value2) {
            addCriterion("mch_pageurl between", value1, value2, "mchPageurl");
            return (Criteria) this;
        }

        public Criteria andMchPageurlNotBetween(String value1, String value2) {
            addCriterion("mch_pageurl not between", value1, value2, "mchPageurl");
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
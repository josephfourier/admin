package com.thinkjoy.ams.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AmsApproleExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public AmsApproleExample() {
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

        public Criteria andApproleIdIsNull() {
            addCriterion("approle_id is null");
            return (Criteria) this;
        }

        public Criteria andApproleIdIsNotNull() {
            addCriterion("approle_id is not null");
            return (Criteria) this;
        }

        public Criteria andApproleIdEqualTo(Integer value) {
            addCriterion("approle_id =", value, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdNotEqualTo(Integer value) {
            addCriterion("approle_id <>", value, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdGreaterThan(Integer value) {
            addCriterion("approle_id >", value, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("approle_id >=", value, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdLessThan(Integer value) {
            addCriterion("approle_id <", value, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdLessThanOrEqualTo(Integer value) {
            addCriterion("approle_id <=", value, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdIn(List<Integer> values) {
            addCriterion("approle_id in", values, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdNotIn(List<Integer> values) {
            addCriterion("approle_id not in", values, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdBetween(Integer value1, Integer value2) {
            addCriterion("approle_id between", value1, value2, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("approle_id not between", value1, value2, "approleId");
            return (Criteria) this;
        }

        public Criteria andApproleNameIsNull() {
            addCriterion("approle_name is null");
            return (Criteria) this;
        }

        public Criteria andApproleNameIsNotNull() {
            addCriterion("approle_name is not null");
            return (Criteria) this;
        }

        public Criteria andApproleNameEqualTo(String value) {
            addCriterion("approle_name =", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameNotEqualTo(String value) {
            addCriterion("approle_name <>", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameGreaterThan(String value) {
            addCriterion("approle_name >", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameGreaterThanOrEqualTo(String value) {
            addCriterion("approle_name >=", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameLessThan(String value) {
            addCriterion("approle_name <", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameLessThanOrEqualTo(String value) {
            addCriterion("approle_name <=", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameLike(String value) {
            addCriterion("approle_name like", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameNotLike(String value) {
            addCriterion("approle_name not like", value, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameIn(List<String> values) {
            addCriterion("approle_name in", values, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameNotIn(List<String> values) {
            addCriterion("approle_name not in", values, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameBetween(String value1, String value2) {
            addCriterion("approle_name between", value1, value2, "approleName");
            return (Criteria) this;
        }

        public Criteria andApproleNameNotBetween(String value1, String value2) {
            addCriterion("approle_name not between", value1, value2, "approleName");
            return (Criteria) this;
        }

        public Criteria andRelationNameIsNull() {
            addCriterion("relation_name is null");
            return (Criteria) this;
        }

        public Criteria andRelationNameIsNotNull() {
            addCriterion("relation_name is not null");
            return (Criteria) this;
        }

        public Criteria andRelationNameEqualTo(String value) {
            addCriterion("relation_name =", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameNotEqualTo(String value) {
            addCriterion("relation_name <>", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameGreaterThan(String value) {
            addCriterion("relation_name >", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameGreaterThanOrEqualTo(String value) {
            addCriterion("relation_name >=", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameLessThan(String value) {
            addCriterion("relation_name <", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameLessThanOrEqualTo(String value) {
            addCriterion("relation_name <=", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameLike(String value) {
            addCriterion("relation_name like", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameNotLike(String value) {
            addCriterion("relation_name not like", value, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameIn(List<String> values) {
            addCriterion("relation_name in", values, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameNotIn(List<String> values) {
            addCriterion("relation_name not in", values, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameBetween(String value1, String value2) {
            addCriterion("relation_name between", value1, value2, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationNameNotBetween(String value1, String value2) {
            addCriterion("relation_name not between", value1, value2, "relationName");
            return (Criteria) this;
        }

        public Criteria andRelationCodeIsNull() {
            addCriterion("relation_code is null");
            return (Criteria) this;
        }

        public Criteria andRelationCodeIsNotNull() {
            addCriterion("relation_code is not null");
            return (Criteria) this;
        }

        public Criteria andRelationCodeEqualTo(String value) {
            addCriterion("relation_code =", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeNotEqualTo(String value) {
            addCriterion("relation_code <>", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeGreaterThan(String value) {
            addCriterion("relation_code >", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("relation_code >=", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeLessThan(String value) {
            addCriterion("relation_code <", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeLessThanOrEqualTo(String value) {
            addCriterion("relation_code <=", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeLike(String value) {
            addCriterion("relation_code like", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeNotLike(String value) {
            addCriterion("relation_code not like", value, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeIn(List<String> values) {
            addCriterion("relation_code in", values, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeNotIn(List<String> values) {
            addCriterion("relation_code not in", values, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeBetween(String value1, String value2) {
            addCriterion("relation_code between", value1, value2, "relationCode");
            return (Criteria) this;
        }

        public Criteria andRelationCodeNotBetween(String value1, String value2) {
            addCriterion("relation_code not between", value1, value2, "relationCode");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdIsNull() {
            addCriterion("usertype_id is null");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdIsNotNull() {
            addCriterion("usertype_id is not null");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdEqualTo(Integer value) {
            addCriterion("usertype_id =", value, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdNotEqualTo(Integer value) {
            addCriterion("usertype_id <>", value, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdGreaterThan(Integer value) {
            addCriterion("usertype_id >", value, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("usertype_id >=", value, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdLessThan(Integer value) {
            addCriterion("usertype_id <", value, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("usertype_id <=", value, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdIn(List<Integer> values) {
            addCriterion("usertype_id in", values, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdNotIn(List<Integer> values) {
            addCriterion("usertype_id not in", values, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdBetween(Integer value1, Integer value2) {
            addCriterion("usertype_id between", value1, value2, "usertypeId");
            return (Criteria) this;
        }

        public Criteria andUsertypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("usertype_id not between", value1, value2, "usertypeId");
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

        public Criteria andOrdersIsNull() {
            addCriterion("orders is null");
            return (Criteria) this;
        }

        public Criteria andOrdersIsNotNull() {
            addCriterion("orders is not null");
            return (Criteria) this;
        }

        public Criteria andOrdersEqualTo(Long value) {
            addCriterion("orders =", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersNotEqualTo(Long value) {
            addCriterion("orders <>", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersGreaterThan(Long value) {
            addCriterion("orders >", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersGreaterThanOrEqualTo(Long value) {
            addCriterion("orders >=", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersLessThan(Long value) {
            addCriterion("orders <", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersLessThanOrEqualTo(Long value) {
            addCriterion("orders <=", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersIn(List<Long> values) {
            addCriterion("orders in", values, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersNotIn(List<Long> values) {
            addCriterion("orders not in", values, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersBetween(Long value1, Long value2) {
            addCriterion("orders between", value1, value2, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersNotBetween(Long value1, Long value2) {
            addCriterion("orders not between", value1, value2, "orders");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationIsNull() {
            addCriterion("per_personalization is null");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationIsNotNull() {
            addCriterion("per_personalization is not null");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationEqualTo(String value) {
            addCriterion("per_personalization =", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationNotEqualTo(String value) {
            addCriterion("per_personalization <>", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationGreaterThan(String value) {
            addCriterion("per_personalization >", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationGreaterThanOrEqualTo(String value) {
            addCriterion("per_personalization >=", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationLessThan(String value) {
            addCriterion("per_personalization <", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationLessThanOrEqualTo(String value) {
            addCriterion("per_personalization <=", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationLike(String value) {
            addCriterion("per_personalization like", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationNotLike(String value) {
            addCriterion("per_personalization not like", value, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationIn(List<String> values) {
            addCriterion("per_personalization in", values, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationNotIn(List<String> values) {
            addCriterion("per_personalization not in", values, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationBetween(String value1, String value2) {
            addCriterion("per_personalization between", value1, value2, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andPerPersonalizationNotBetween(String value1, String value2) {
            addCriterion("per_personalization not between", value1, value2, "perPersonalization");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Integer value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Integer value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Integer value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Integer value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Integer value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Integer> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Integer> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Integer value1, Integer value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Integer value1, Integer value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
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
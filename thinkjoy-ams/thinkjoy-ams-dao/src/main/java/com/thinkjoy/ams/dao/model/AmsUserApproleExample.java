package com.thinkjoy.ams.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AmsUserApproleExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public AmsUserApproleExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
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
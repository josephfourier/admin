package com.thinkjoy.ams.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OauthAccessTokenExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public OauthAccessTokenExample() {
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

        public Criteria andTokenIdIsNull() {
            addCriterion("token_id is null");
            return (Criteria) this;
        }

        public Criteria andTokenIdIsNotNull() {
            addCriterion("token_id is not null");
            return (Criteria) this;
        }

        public Criteria andTokenIdEqualTo(String value) {
            addCriterion("token_id =", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotEqualTo(String value) {
            addCriterion("token_id <>", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdGreaterThan(String value) {
            addCriterion("token_id >", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdGreaterThanOrEqualTo(String value) {
            addCriterion("token_id >=", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdLessThan(String value) {
            addCriterion("token_id <", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdLessThanOrEqualTo(String value) {
            addCriterion("token_id <=", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdLike(String value) {
            addCriterion("token_id like", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotLike(String value) {
            addCriterion("token_id not like", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdIn(List<String> values) {
            addCriterion("token_id in", values, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotIn(List<String> values) {
            addCriterion("token_id not in", values, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdBetween(String value1, String value2) {
            addCriterion("token_id between", value1, value2, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotBetween(String value1, String value2) {
            addCriterion("token_id not between", value1, value2, "tokenId");
            return (Criteria) this;
        }

        public Criteria andClientIdIsNull() {
            addCriterion("client_id is null");
            return (Criteria) this;
        }

        public Criteria andClientIdIsNotNull() {
            addCriterion("client_id is not null");
            return (Criteria) this;
        }

        public Criteria andClientIdEqualTo(String value) {
            addCriterion("client_id =", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotEqualTo(String value) {
            addCriterion("client_id <>", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThan(String value) {
            addCriterion("client_id >", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThanOrEqualTo(String value) {
            addCriterion("client_id >=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThan(String value) {
            addCriterion("client_id <", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThanOrEqualTo(String value) {
            addCriterion("client_id <=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLike(String value) {
            addCriterion("client_id like", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotLike(String value) {
            addCriterion("client_id not like", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdIn(List<String> values) {
            addCriterion("client_id in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotIn(List<String> values) {
            addCriterion("client_id not in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdBetween(String value1, String value2) {
            addCriterion("client_id between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotBetween(String value1, String value2) {
            addCriterion("client_id not between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdIsNull() {
            addCriterion("authentication_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdIsNotNull() {
            addCriterion("authentication_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdEqualTo(String value) {
            addCriterion("authentication_id =", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdNotEqualTo(String value) {
            addCriterion("authentication_id <>", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdGreaterThan(String value) {
            addCriterion("authentication_id >", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdGreaterThanOrEqualTo(String value) {
            addCriterion("authentication_id >=", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdLessThan(String value) {
            addCriterion("authentication_id <", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdLessThanOrEqualTo(String value) {
            addCriterion("authentication_id <=", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdLike(String value) {
            addCriterion("authentication_id like", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdNotLike(String value) {
            addCriterion("authentication_id not like", value, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdIn(List<String> values) {
            addCriterion("authentication_id in", values, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdNotIn(List<String> values) {
            addCriterion("authentication_id not in", values, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdBetween(String value1, String value2) {
            addCriterion("authentication_id between", value1, value2, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIdNotBetween(String value1, String value2) {
            addCriterion("authentication_id not between", value1, value2, "authenticationId");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andTokenTypeIsNull() {
            addCriterion("token_type is null");
            return (Criteria) this;
        }

        public Criteria andTokenTypeIsNotNull() {
            addCriterion("token_type is not null");
            return (Criteria) this;
        }

        public Criteria andTokenTypeEqualTo(String value) {
            addCriterion("token_type =", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeNotEqualTo(String value) {
            addCriterion("token_type <>", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeGreaterThan(String value) {
            addCriterion("token_type >", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeGreaterThanOrEqualTo(String value) {
            addCriterion("token_type >=", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeLessThan(String value) {
            addCriterion("token_type <", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeLessThanOrEqualTo(String value) {
            addCriterion("token_type <=", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeLike(String value) {
            addCriterion("token_type like", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeNotLike(String value) {
            addCriterion("token_type not like", value, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeIn(List<String> values) {
            addCriterion("token_type in", values, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeNotIn(List<String> values) {
            addCriterion("token_type not in", values, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeBetween(String value1, String value2) {
            addCriterion("token_type between", value1, value2, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenTypeNotBetween(String value1, String value2) {
            addCriterion("token_type not between", value1, value2, "tokenType");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsIsNull() {
            addCriterion("token_expired_seconds is null");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsIsNotNull() {
            addCriterion("token_expired_seconds is not null");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsEqualTo(Integer value) {
            addCriterion("token_expired_seconds =", value, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsNotEqualTo(Integer value) {
            addCriterion("token_expired_seconds <>", value, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsGreaterThan(Integer value) {
            addCriterion("token_expired_seconds >", value, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsGreaterThanOrEqualTo(Integer value) {
            addCriterion("token_expired_seconds >=", value, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsLessThan(Integer value) {
            addCriterion("token_expired_seconds <", value, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsLessThanOrEqualTo(Integer value) {
            addCriterion("token_expired_seconds <=", value, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsIn(List<Integer> values) {
            addCriterion("token_expired_seconds in", values, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsNotIn(List<Integer> values) {
            addCriterion("token_expired_seconds not in", values, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsBetween(Integer value1, Integer value2) {
            addCriterion("token_expired_seconds between", value1, value2, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andTokenExpiredSecondsNotBetween(Integer value1, Integer value2) {
            addCriterion("token_expired_seconds not between", value1, value2, "tokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenIsNull() {
            addCriterion("refresh_token is null");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenIsNotNull() {
            addCriterion("refresh_token is not null");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenEqualTo(String value) {
            addCriterion("refresh_token =", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenNotEqualTo(String value) {
            addCriterion("refresh_token <>", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenGreaterThan(String value) {
            addCriterion("refresh_token >", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenGreaterThanOrEqualTo(String value) {
            addCriterion("refresh_token >=", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenLessThan(String value) {
            addCriterion("refresh_token <", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenLessThanOrEqualTo(String value) {
            addCriterion("refresh_token <=", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenLike(String value) {
            addCriterion("refresh_token like", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenNotLike(String value) {
            addCriterion("refresh_token not like", value, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenIn(List<String> values) {
            addCriterion("refresh_token in", values, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenNotIn(List<String> values) {
            addCriterion("refresh_token not in", values, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenBetween(String value1, String value2) {
            addCriterion("refresh_token between", value1, value2, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenNotBetween(String value1, String value2) {
            addCriterion("refresh_token not between", value1, value2, "refreshToken");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsIsNull() {
            addCriterion("refresh_token_expired_seconds is null");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsIsNotNull() {
            addCriterion("refresh_token_expired_seconds is not null");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsEqualTo(Integer value) {
            addCriterion("refresh_token_expired_seconds =", value, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsNotEqualTo(Integer value) {
            addCriterion("refresh_token_expired_seconds <>", value, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsGreaterThan(Integer value) {
            addCriterion("refresh_token_expired_seconds >", value, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsGreaterThanOrEqualTo(Integer value) {
            addCriterion("refresh_token_expired_seconds >=", value, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsLessThan(Integer value) {
            addCriterion("refresh_token_expired_seconds <", value, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsLessThanOrEqualTo(Integer value) {
            addCriterion("refresh_token_expired_seconds <=", value, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsIn(List<Integer> values) {
            addCriterion("refresh_token_expired_seconds in", values, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsNotIn(List<Integer> values) {
            addCriterion("refresh_token_expired_seconds not in", values, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsBetween(Integer value1, Integer value2) {
            addCriterion("refresh_token_expired_seconds between", value1, value2, "refreshTokenExpiredSeconds");
            return (Criteria) this;
        }

        public Criteria andRefreshTokenExpiredSecondsNotBetween(Integer value1, Integer value2) {
            addCriterion("refresh_token_expired_seconds not between", value1, value2, "refreshTokenExpiredSeconds");
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
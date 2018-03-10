package com.thinkjoy.ams.dao.model;

import java.io.Serializable;

public class OauthAccessToken implements Serializable {
    private String tokenId;

    /**
     * 客户端id
     *
     * @mbg.generated
     */
    private String clientId;

    /**
     * 该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的
     *
     * @mbg.generated
     */
    private String authenticationId;

    /**
     * 登录时的用户名
     *
     * @mbg.generated
     */
    private String username;

    /**
     * Bearer   ?
     *
     * @mbg.generated
     */
    private String tokenType;

    /**
     * token生效时间
     *
     * @mbg.generated
     */
    private Integer tokenExpiredSeconds;

    /**
     * 该字段的值是将<code>refresh_token</code>的值通过MD5加密后存储的
     *
     * @mbg.generated
     */
    private String refreshToken;

    /**
     * refresh_taken生效时间
     *
     * @mbg.generated
     */
    private Integer refreshTokenExpiredSeconds;

    private Long ctime;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 用户类型
     *
     * @mbg.generated
     */
    private Integer usertypeId;

    /**
     * 学校/机构编码
     *
     * @mbg.generated
     */
    private String relationCode;

    /**
     * 个性化设置:
            0-默认
            1-个性化
     *
     * @mbg.generated
     */
    private String perPersonalization;

    private static final long serialVersionUID = 1L;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getTokenExpiredSeconds() {
        return tokenExpiredSeconds;
    }

    public void setTokenExpiredSeconds(Integer tokenExpiredSeconds) {
        this.tokenExpiredSeconds = tokenExpiredSeconds;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getRefreshTokenExpiredSeconds() {
        return refreshTokenExpiredSeconds;
    }

    public void setRefreshTokenExpiredSeconds(Integer refreshTokenExpiredSeconds) {
        this.refreshTokenExpiredSeconds = refreshTokenExpiredSeconds;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(Integer usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getPerPersonalization() {
        return perPersonalization;
    }

    public void setPerPersonalization(String perPersonalization) {
        this.perPersonalization = perPersonalization;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tokenId=").append(tokenId);
        sb.append(", clientId=").append(clientId);
        sb.append(", authenticationId=").append(authenticationId);
        sb.append(", username=").append(username);
        sb.append(", tokenType=").append(tokenType);
        sb.append(", tokenExpiredSeconds=").append(tokenExpiredSeconds);
        sb.append(", refreshToken=").append(refreshToken);
        sb.append(", refreshTokenExpiredSeconds=").append(refreshTokenExpiredSeconds);
        sb.append(", ctime=").append(ctime);
        sb.append(", userId=").append(userId);
        sb.append(", usertypeId=").append(usertypeId);
        sb.append(", relationCode=").append(relationCode);
        sb.append(", perPersonalization=").append(perPersonalization);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OauthAccessToken other = (OauthAccessToken) that;
        return (this.getTokenId() == null ? other.getTokenId() == null : this.getTokenId().equals(other.getTokenId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getAuthenticationId() == null ? other.getAuthenticationId() == null : this.getAuthenticationId().equals(other.getAuthenticationId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getTokenType() == null ? other.getTokenType() == null : this.getTokenType().equals(other.getTokenType()))
            && (this.getTokenExpiredSeconds() == null ? other.getTokenExpiredSeconds() == null : this.getTokenExpiredSeconds().equals(other.getTokenExpiredSeconds()))
            && (this.getRefreshToken() == null ? other.getRefreshToken() == null : this.getRefreshToken().equals(other.getRefreshToken()))
            && (this.getRefreshTokenExpiredSeconds() == null ? other.getRefreshTokenExpiredSeconds() == null : this.getRefreshTokenExpiredSeconds().equals(other.getRefreshTokenExpiredSeconds()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUsertypeId() == null ? other.getUsertypeId() == null : this.getUsertypeId().equals(other.getUsertypeId()))
            && (this.getRelationCode() == null ? other.getRelationCode() == null : this.getRelationCode().equals(other.getRelationCode()))
            && (this.getPerPersonalization() == null ? other.getPerPersonalization() == null : this.getPerPersonalization().equals(other.getPerPersonalization()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTokenId() == null) ? 0 : getTokenId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getAuthenticationId() == null) ? 0 : getAuthenticationId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getTokenType() == null) ? 0 : getTokenType().hashCode());
        result = prime * result + ((getTokenExpiredSeconds() == null) ? 0 : getTokenExpiredSeconds().hashCode());
        result = prime * result + ((getRefreshToken() == null) ? 0 : getRefreshToken().hashCode());
        result = prime * result + ((getRefreshTokenExpiredSeconds() == null) ? 0 : getRefreshTokenExpiredSeconds().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUsertypeId() == null) ? 0 : getUsertypeId().hashCode());
        result = prime * result + ((getRelationCode() == null) ? 0 : getRelationCode().hashCode());
        result = prime * result + ((getPerPersonalization() == null) ? 0 : getPerPersonalization().hashCode());
        return result;
    }
}
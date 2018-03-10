package com.thinkjoy.ams.dao.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

public class OauthClientDetails implements Serializable {
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 客户端id
     *
     * @mbg.generated
     */
    @Length(min = 1, max = 64, message = "客户端ID长度必须在{min}-{max}之间")
    @NotBlank(message = "客户端ID不能为空!")
    private String clientId;

    /**
     * 客户端私钥
     *
     * @mbg.generated
     */
    @Length(min = 1, max = 256, message = "客户端私钥长度必须在{min}-{max}之间")
    @NotBlank(message = "客户端私钥不能为空!")
    private String clientSecret;

    /**
     * 客户端名称
     *
     * @mbg.generated
     */
    @Length(min = 1, max = 128, message = "客户端名称长度必须在{min}-{max}之间")
    @NotBlank(message = "客户端名称不能为空!")
    private String clientName;

    /**
     * 读写权限:
            read
            write
            read write
     *
     * @mbg.generated
     */
    private String scope;

    /**
     * 资源列表
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     * 应用系统id，一个应用系统对应一个clientId
     *
     * @mbg.generated
     */
    private Integer appId;

    /**
     * 可访问资源集合
     *
     * @mbg.generated
     */
    private String resourceIds;

    /**
     * 授权类型
            authorization_code,password,refresh_token,implicit,,client_credentials
     *
     * @mbg.generated
     */
    private String grantTypes;

    /**
     * web_server重定向地址
     *
     * @mbg.generated
     */
    @Length(min = 1, max = 256, message = "重定向地址长度必须在{min}-{max}之间")
    @NotBlank(message = "重定向地址不能为空!")
    private String redirectUri;

    /**
     *  指定客户端所拥有的Spring Security的权限值
     *
     * @mbg.generated
     */
    private String authorities;

    /**
     * 设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时)
     *
     * @mbg.generated
     */
    @Min(value = 0, message = "客户端的access_token的有效时间值需填写正数!")
    @Max(value = 2147483647 , message = "客户端的access_token的有效时间值需填写正数!")
    private Integer accessTokenValidity;

    /**
     * 设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天).
     *
     * @mbg.generated
     */
    @Min(value = 0, message = "客户端的refresh_token的有效时间值需填写正数!")
    @Max(value = 2147483647 , message = "客户端的refresh_token的有效时间值需填写正数!")
    private Integer refreshTokenValidity;

    /**
     * 设置客户端是否为受信任的,默认为'0'(即不受信任的,1为受信任的).
     *
     * @mbg.generated
     */
    private String trusted;

    /**
     * 是否支持同时在线  0 否  1是
     *
     * @mbg.generated
     */
    private String isMutiLogin;

    /**
     * 创建人
     *
     * @mbg.generated
     */
    private String creator;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Long ctime;

    /**
     * 描述
     *
     * @mbg.generated
     */
    @Length(max = 256, message = "描述长度必须在{min}-{max}之间")
    private String description;

    /**
     * 状态 ：0.锁定
                 1.正常(默认)
            
            用于标识客户端是否已存档(即实现逻辑删除),默认值为'1'(即未存档).
     *
     * @mbg.generated
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getTrusted() {
        return trusted;
    }

    public void setTrusted(String trusted) {
        this.trusted = trusted;
    }

    public String getIsMutiLogin() {
        return isMutiLogin;
    }

    public void setIsMutiLogin(String isMutiLogin) {
        this.isMutiLogin = isMutiLogin;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientId=").append(clientId);
        sb.append(", clientSecret=").append(clientSecret);
        sb.append(", clientName=").append(clientName);
        sb.append(", scope=").append(scope);
        sb.append(", roleId=").append(roleId);
        sb.append(", appId=").append(appId);
        sb.append(", resourceIds=").append(resourceIds);
        sb.append(", grantTypes=").append(grantTypes);
        sb.append(", redirectUri=").append(redirectUri);
        sb.append(", authorities=").append(authorities);
        sb.append(", accessTokenValidity=").append(accessTokenValidity);
        sb.append(", refreshTokenValidity=").append(refreshTokenValidity);
        sb.append(", trusted=").append(trusted);
        sb.append(", isMutiLogin=").append(isMutiLogin);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
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
        OauthClientDetails other = (OauthClientDetails) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getClientSecret() == null ? other.getClientSecret() == null : this.getClientSecret().equals(other.getClientSecret()))
            && (this.getClientName() == null ? other.getClientName() == null : this.getClientName().equals(other.getClientName()))
            && (this.getScope() == null ? other.getScope() == null : this.getScope().equals(other.getScope()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getResourceIds() == null ? other.getResourceIds() == null : this.getResourceIds().equals(other.getResourceIds()))
            && (this.getGrantTypes() == null ? other.getGrantTypes() == null : this.getGrantTypes().equals(other.getGrantTypes()))
            && (this.getRedirectUri() == null ? other.getRedirectUri() == null : this.getRedirectUri().equals(other.getRedirectUri()))
            && (this.getAuthorities() == null ? other.getAuthorities() == null : this.getAuthorities().equals(other.getAuthorities()))
            && (this.getAccessTokenValidity() == null ? other.getAccessTokenValidity() == null : this.getAccessTokenValidity().equals(other.getAccessTokenValidity()))
            && (this.getRefreshTokenValidity() == null ? other.getRefreshTokenValidity() == null : this.getRefreshTokenValidity().equals(other.getRefreshTokenValidity()))
            && (this.getTrusted() == null ? other.getTrusted() == null : this.getTrusted().equals(other.getTrusted()))
            && (this.getIsMutiLogin() == null ? other.getIsMutiLogin() == null : this.getIsMutiLogin().equals(other.getIsMutiLogin()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getClientSecret() == null) ? 0 : getClientSecret().hashCode());
        result = prime * result + ((getClientName() == null) ? 0 : getClientName().hashCode());
        result = prime * result + ((getScope() == null) ? 0 : getScope().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getResourceIds() == null) ? 0 : getResourceIds().hashCode());
        result = prime * result + ((getGrantTypes() == null) ? 0 : getGrantTypes().hashCode());
        result = prime * result + ((getRedirectUri() == null) ? 0 : getRedirectUri().hashCode());
        result = prime * result + ((getAuthorities() == null) ? 0 : getAuthorities().hashCode());
        result = prime * result + ((getAccessTokenValidity() == null) ? 0 : getAccessTokenValidity().hashCode());
        result = prime * result + ((getRefreshTokenValidity() == null) ? 0 : getRefreshTokenValidity().hashCode());
        result = prime * result + ((getTrusted() == null) ? 0 : getTrusted().hashCode());
        result = prime * result + ((getIsMutiLogin() == null) ? 0 : getIsMutiLogin().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}
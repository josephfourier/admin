package com.thinkjoy.common.enumUtil;

/**
 * Created by wangcheng on 17/9/14.
 */
public enum TableDescriptioin {

    AMS_PROJECT_APP("ams_project_app", "项目管理-->项目属性管理-->关联应用"),
    OAUTH_CLIENT_DETAILS("oauth_client_details", "应用管理-->认证配置管理-->编辑认证配置"),
    AMS_ROLE_RESOURCE("ams_role_resource", "应用管理-->认证配置管理-->编辑认证配置");

    private String tName;
    private String tDes;

    TableDescriptioin(String tName, String tDes) {
        this.tName = tName;
        this.tDes = tDes;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettDes() {
        return tDes;
    }

    public void settDes(String tDes) {
        this.tDes = tDes;
    }
}

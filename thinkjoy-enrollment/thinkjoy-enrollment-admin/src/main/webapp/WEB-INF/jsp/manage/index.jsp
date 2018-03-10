<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<c:set var="newDate" value="<%=new Date().getTime()%>" scope="page"></c:set>
<%--<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>招生管理系统</title>

    <link href="${basePath}/resources/thinkjoy-admin/plugins/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${basePath}/resources/thinkjoy-admin/plugins/material-design-iconic-font-2.2.0/css/material-design-iconic-font.min.css"
          rel="stylesheet"/>
    <link href="${basePath}/resources/thinkjoy-admin/plugins/waves-0.7.5/waves.min.css" rel="stylesheet"/>
    <link href="${basePath}/resources/thinkjoy-admin/plugins/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css"
          rel="stylesheet"/>
    <link href="${basePath}/resources/thinkjoy-admin/css/admin.css" rel="stylesheet"/>
    <style>
        /** skins **/

        #thinkjoy-enrollment-admin #header {background: #0d8fe1;}
        #thinkjoy-enrollment-admin .content_tab{background: #0d8fe1;}
        #thinkjoy-enrollment-admin .s-profile>a{background: url(http://devfile.xiyue.cn/upload/20170829115059436375564.jpg) left top no-repeat;}

    </style>
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?5d84a7c755c92c5bcc4445c96138f9b8";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>
<body>
<header id="header">
    <ul id="menu">
        <li id="guide" class="line-trigger">
            <div class="line-wrap">
                <div class="line top"></div>
                <div class="line center"></div>
                <div class="line bottom"></div>
            </div>
        </li>
        <li id="logo" class="hidden-xs">
            <a href="${basePath}/manage/index">
                <img src="${basePath}/resources/thinkjoy-admin/images/logo1.png"/>
            </a>
            <span id="system_title">招生管理系统</span>
        </li>
        <li class="pull-right">
            <ul class="hi-menu">
                <!-- 搜索 -->
                <li class="dropdown">
                    <a class="waves-effect waves-light" data-toggle="dropdown" href="javascript:;">
                        <i class="him-icon zmdi zmdi-search"></i>
                    </a>
                    <ul class="dropdown-menu dm-icon pull-right">
                        <form id="search-form" class="form-inline">
                            <div class="input-group">
                                <input id="keywords" type="text" name="keywords" class="form-control" placeholder="搜索"/>
                                <div class="input-group-btn">
                                    <button type="submit" class="btn btn-default"><span
                                            class="glyphicon glyphicon-search"></span></button>
                                </div>
                            </div>
                        </form>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="waves-effect waves-light" data-toggle="dropdown" href="javascript:;">
                        <i class="him-icon zmdi zmdi-dropbox"></i>
                    </a>
                </li>
                <li class="dropdown">
                    <a class="waves-effect waves-light" data-toggle="dropdown" href="javascript:;">
                        <i class="him-icon zmdi zmdi-more-vert"></i>
                    </a>
                    <ul class="dropdown-menu dm-icon pull-right">
                        <li class="hidden-xs">
                            <a class="waves-effect" data-ma-action="fullscreen" href="javascript:fullPage();"><i
                                    class="zmdi zmdi-fullscreen"></i> 全屏模式</a>
                        </li>
                        <li>
                            <a class="waves-effect" href="${basePath}/sso/logout"><i class="zmdi zmdi-run"></i> 退出登录</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</header>
<section id="main">
    <!-- 左侧导航区 -->
    <aside id="sidebar">
        <!-- 个人资料区 -->
        <div class="s-profile">
            <a class="waves-effect waves-light" href="javascript:;">
                <div class="sp-pic">
                    <img src="${basePath}${ucenterUser.avatar}"/>
                </div>
                <div class="sp-info">
                    ${ucenterUser.fullname}，您好！
                    <i class="zmdi zmdi-caret-down"></i>
                </div>
            </a>
            <ul class="main-menu">
                &lt;%&ndash;<li>&ndash;%&gt;
                &lt;%&ndash;<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-account"></i> 个人资料</a>&ndash;%&gt;
                &lt;%&ndash;</li>&ndash;%&gt;
                &lt;%&ndash;<li>&ndash;%&gt;
                &lt;%&ndash;<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-face"></i> 隐私管理</a>&ndash;%&gt;
                &lt;%&ndash;</li>&ndash;%&gt;
                &lt;%&ndash;<li>&ndash;%&gt;
                &lt;%&ndash;<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-settings"></i> 系统设置</a>&ndash;%&gt;
                &lt;%&ndash;</li>&ndash;%&gt;
                &lt;%&ndash;<li>&ndash;%&gt;
                &lt;%&ndash;<a class="waves-effect" href="${basePath}/sso/logout"><i class="zmdi zmdi-run"></i> 退出登录</a>&ndash;%&gt;
                &lt;%&ndash;</li>&ndash;%&gt;
            </ul>
        </div>
        <!-- /个人资料区 -->
        <!-- 菜单区 -->
        <ul class="main-menu">
            <li>
                <a class="waves-effect" href="javascript:Tab.addTab('首页', 'home');"><i class="zmdi zmdi-home"></i>
                    首页1</a>
            </li>
            <c:forEach var="appPermission" items="${appPermissions}" varStatus="status">
                <c:if test="${appPermission.pid eq 0}">
                    <li class="sub-menu system_${appPermission.systemId} ${status.index}">
                        <a class="waves-effect" href="javascript:;"><i
                                class="${appPermission.icon}"></i> ${appPermission.name}</a>
                        <ul>
                            <c:forEach var="subAppPermission" items="${appPermissions}">
                                <c:if test="${subAppPermission.pid eq appPermission.permissionId}">
                                    <c:if test="${subAppPermission.systemId eq amsApp.appId}">
                                        <c:set var="systemBasePath" value="${amsApp.redirectUri}"/></c:if>
                                    <li><a class="waves-effect"
                                           href="javascript:Tab.addTab('${subAppPermission.name}', '${systemBasePath}${subAppPermission.uri}');">${subAppPermission.name}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
            <li>
                <div class="enrollment-version">&copy; thinkjoy-enrollment V1.0.0</div>
            </li>
        </ul>
        <!-- /菜单区 -->
    </aside>
    <!-- /左侧导航区 -->
    <section id="content">
        <div class="content_tab">
            <div class="tab_left">
                <a class="waves-effect waves-light" href="javascript:;"><i class="zmdi zmdi-chevron-left"></i></a>
            </div>
            <div class="tab_right">
                <a class="waves-effect waves-light" href="javascript:;"><i class="zmdi zmdi-chevron-right"></i></a>
            </div>
            <ul id="tabs" class="tabs">
                <li id="tab_home" data-index="home" data-closeable="false" class="cur">
                    <a class="waves-effect waves-light" href="javascript:;">首页</a>
                </li>
            </ul>
        </div>
        <div class="content_main">
            <div id="iframe_home" class="iframe cur">
                <p><h4>通用用户权限管理系统</h4></p>
                <p><b>系统简介</b>：本系统是基于RBAC授权和基于用户授权的细粒度权限控制通用平台，并提供单点登录、会话管理和日志管理。接入的系统可自由定义组织、角色、权限、资源等。</p><br/>
                <p><h4>系统功能概述：</h4></p>
                <p><b>系统组织管理</b>：系统和组织增加、删除、修改、查询功能。</p>
                <p><b>用户角色管理</b>：用户和角色增加、删除、修改、查询功能。</p>
                <p><b>资源权限管理</b>：菜单和按钮增加、删除、修改、查询功能。</p>
                <p><b>权限分配管理</b>：提供给角色和用户的权限增加、删除、修改、查询功能。</p>
                <p><b>单点登录(SSO)</b>：提供统一用户单点登录认证、用户鉴权功能。</p>
                <p><b>用户会话管理</b>：提供分布式用户会话管理</p>
                <p><b>操作日志管理</b>：提供记录用户登录、操作等日志。</p><br/>
                <p><h4>对外接口概述：</h4></p>
                <p><b>系统组织数据接口</b>、<b>用户角色数据接口</b>、<b>资源权限数据接口</b>、<b>用户鉴权接口</b></p><br/>
            </div>
        </div>
    </section>
</section>
<footer id="footer"></footer>
<script>var BASE_PATH = '${basePath}';</script>
&lt;%&ndash;<script>var BACK_URL = '${backUrl}';</script>&ndash;%&gt;
&lt;%&ndash;<script>var BACK_URL_TITLE = '${backUrl_title}';</script>&ndash;%&gt;
<script src="${basePath}/resources/thinkjoy-admin/plugins/jquery.1.12.4.min.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/waves-0.7.5/waves.min.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/BootstrapMenu.min.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/device.min.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/jquery.cookie.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/js/admin.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/fullPage/jquery.fullPage.min.js"></script>
<script src="${basePath}/resources/thinkjoy-admin/plugins/fullPage/jquery.jdirk.min.js"></script>
</body>
</html>--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>招生系统</title>

    <link rel="stylesheet" href="${basePath}/resources/layui/css/layui.css?t=${newDate}" media="all">
    <link rel="stylesheet" href="${basePath}/resources/lap/css/lap-eas.css?t=${newDate}" media="all">

</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">
            <a href="javascript:;">招生管理系统</a>
        </div>
        <div class="lap-agency">
            <span>${ucenterSchool.schoolName}</span>

            <span class="title"><i class="spacer"></i>招生管理系统</span>

           <%-- <span>${amsApp}</span>--%>
        </div>

        <ul class="layui-nav layui-layout-right" id="user-info">
            <li class="layui-nav-item">
                <a href="javascript:;" class="lap-nav-user">
                    <i></i>
                    <span class="user">${ucenterUser.username}</span>
                </a>
                <dl class="layui-nav-child">
                   <%-- <dd><a href="">基本资料</a></dd>
                    <dd><a href="">修改密码</a></dd>--%>
                    <dd><a href="${basePath}/logout">返回个人中心</a></dd>
                    <dd><a href="http://web.zhijiaoyun.net/sso/logout">退出系统</a></dd>
                </dl>
            </li>
        </ul>
    </div>

    <div class="layui-side lap-bg-default">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="test">

                <c:forEach var="appPermission" items="${appPermissions}" varStatus="status">
                    <c:if test="${appPermission.type eq 1}">
                        <li class="layui-nav-item  system_${appPermission.systemId} ${status.index}">

                                <%--判断是否有二级菜单--%>
                            <c:set var="flag" value="0" scope="page"></c:set>
                            <c:forEach var="subAppPermission" items="${appPermissions}">
                                <c:if test="${subAppPermission.pid eq appPermission.permissionId}">
                                    <c:if test="${subAppPermission.type eq 2}">
                                        <c:set var="flag" value="1" scope="page"></c:set>
                                    </c:if>
                                </c:if>
                            </c:forEach>

                            <a class="${appPermission.icon}"
                               data-url="${empty appPermission.uri ? 'javascript:;' : appPermission.uri}"
                               data-id=${appPermission.permissionId} href="javascript:;">
                                <i class="icon"></i>
                                <span>${appPermission.name}</span>
                            </a>

                                <%-- 若有二级菜单 --%>
                            <c:if test="${flag eq 1}">
                                <dl class="layui-nav-child">
                                    <c:forEach var="subAppPermission" items="${appPermissions}">
                                        <c:if test="${subAppPermission.pid eq appPermission.permissionId}">
                                            <c:if test="${subAppPermission.systemId eq amsApp.appId}">
                                                <c:set var="systemBasePath" value="http://localhost:4444"/>
                                            </c:if>

                                            <%--不展示按钮--%>
                                            <c:if test="${subAppPermission.type eq 2}">
                                                <dd>
                                                    <a data-url="${empty subAppPermission.uri ? 'javascript:;' : subAppPermission.uri}"
                                                       data-id=${subAppPermission.permissionId}  href="javascript:;">${subAppPermission.name}</a>
                                                </dd>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </dl>
                            </c:if>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>


    <div class="layui-body">
        <div id="container" class="lap-tab"></div>
    </div>


</div>

<script src="${basePath}/resources/layui/layui.js?t=${newDate}" charset="utf-8"></script>

<script>
    layui.config({
        base: '${basePath}/resources/lap/js/'
    }).use('tab');

    layui.use(['tab', 'element'], function () {
        var element = layui.element
                , tab = layui.tab
                , $ = layui.jquery;

        tab.set({
            elem: '#container',
            main_url: 'layui-test'
        }).render();

        //初始化一级菜单点击事件
        $('.layui-nav-item a').click(function (e) {
            var href, id, dd;

            if ((dd = $($(this).siblings('dl')[0]).find('dd')).length == 1) {
                href = $(dd).find('a').data('url');
                id = $(dd).find('a').data('id');
            } else {
                href = $(this).data('url');
                id = $(this).data('id');
            }

            if (href == 'javascript:;') return;
            tab.add_tab({
                id: id,
                url: href
            });
        });
        tab.add_tab({
            id: 20,
            url: '/manage/enrollment/index'
        })
    });
</script>
</body>
</html>

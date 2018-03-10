<%@ page import="com.thinkjoy.web.common.constant.WebResult" %>
<%@ page import="com.thinkjoy.web.common.constant.WebResultConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/tags/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/resources/home/css/reset.css">
    <link rel="stylesheet" href="${ctx}/resources/home/css/auth.css">
    <script type="text/javascript" src="${ctx}/resources/home/js/jquery-3.2.1.min.js"></script>
    <style>
        body{
            font: 12px/1.5 fzlthk,Microsoft YaHei,tahoma,arial,Hiragino Sans GB,\\5b8b\4f53,sans-serif;
            background-color: #f5f5f5;
        }
    </style>
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?5d84a7c755c92c5bcc4445c96138f9b8";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>
<body>


<div class="auth-list">
    <div class="title">选择身份</div>
    <div class="items">


        <c:forEach items="${userIdentities}" var="u">
            <c:choose>
                <c:when test="${u.usertypeId eq 3}">
                    <div class="item teacher">
                        <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                            <div class="auth-icon"></div>
                            <p>${u.usertypeName}账号</p>
                            <p>
                                <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                                <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                            </p>
                        </a>
                    </div>
                </c:when>

                <c:when test="${u.usertypeId eq 1}">
                    <div class="item student">
                        <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                            <div class="auth-icon"></div>
                            <p>${u.usertypeName}账号</p>
                            <p>
                                <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                                <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                            </p>
                        </a>
                    </div>
                </c:when>

                <c:when test="${u.usertypeId eq 4}">
                    <div class="item organization">
                        <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                            <div class="auth-icon"></div>
                            <p>${u.usertypeName}账号</p>
                            <p>
                                <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                                <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                            </p>
                        </a>
                    </div>
                </c:when>

                <c:when test="${u.usertypeId eq 2}">
                    <div class="item parents">
                        <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                            <div class="auth-icon"></div>
                            <p>${u.usertypeName}账号</p>
                            <p>
                                <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                                <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                            </p>
                        </a>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>

    </div>
</div>


<script>
//    $(function () {
//        $('.item').click(function (e) {
//            e.preventDefault();
//
//            $(this).attr('class')
//
//            window.location.href="/manage/index?userType=";
//        });
//    })
</script>
</body>
</html>
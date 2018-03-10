<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="utf-8">

    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/resources/home/css/reset.css">
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        body {background-color:#ffffff;font:12px/1.5 Microsoft YaHei,tahoma,arial,Hiragino Sans GB,\\5b8b\4f53,sans-serif}
        a{text-decoration: none;}
        ol,li{list-style-type: none;}
        .auth{}
        .auth .title{border-bottom:1px solid #eeeeee;line-height: 50px; text-align: center;font-size:14px;color:#000000;}
        .auth .list{padding:40px 40px 20px 40px;}

        .auth .list  .item{float:left;padding:0 80px 20px 0;}
        .auth .list  .item:nth-child(2n){  padding-left: 10px; padding-right: 0;}
        .auth .list  .item:nth-child(3n){float:right;  padding-right: 0;}
        .auth .list  .item>img{padding-bottom:12px;}
        .auth .list  .item a{color:#323232;}
        .auth .list  .item a:hover{color:#4CB3FF;}
        .auth .list  .item p{
            text-align: center;}
        .auth .list  .item p.type{font-size:16px;}
        .auth .list  .item p.organization{font-size:12px;}

    </style>

    <script type="text/javascript" src="${ctx}/resources/home/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/home/js/jquery.leoweather.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/home/plugins/layer/layer.js"></script>

</head>
<body>

<div class="auth">
    <div class="title">
        <p>选择身份</p>
    </div>
    <form action="">
        <ul class="list clear">

            <c:forEach items="${userIdentities}" var="ui">
                <li class="item">
                    <c:choose>
                        <c:when test="${ui.usertypeId == 1}">
                            <img src="${ctx}/resources/home/images/ic_xs.png" alt="">
                        </c:when>
                        <c:when test="${ui.usertypeId == 2}">
                            <img src="${ctx}/resources/home/images/ic_xs.png" alt="">
                        </c:when>
                        <c:when test="${ui.usertypeId == 3}">
                            <img src="${ctx}/resources/home/images/ic_js.png" alt="">
                        </c:when>
                        <c:when test="${ui.usertypeId == 4}">
                            <img src="${ctx}/resources/home/images/ic_jg.png" alt="">
                        </c:when>
                    </c:choose>
                    <p class="type"><a href="javascript:;">${ui.usertypeName}</a></p>
                    <p class="organization">
                        <a href="${ctx}/manage/index?userType=${ui.usertypeId}">
                        <c:if test="${not empty ui.schoolName}">${ui.schoolName}</c:if>
                            <c:if test="${not empty ui.agencyName}">${ui.agencyName}</c:if>
                        </a>
                    </p>
                </li>
            </c:forEach>


            <%--<li class="item">--%>
                <%--<img src="${ctx}/resources/home/images/ic_xs.png" alt="">--%>
                <%--<p class="type"><a href="javascript:;">学生账户</a></p>--%>
                <%--<p class="organization"><a href="javascript:;">西北工业大学</a></p>--%>
            <%--</li>--%>
            <%--<li class="item">--%>
                <%--<img src="${ctx}/resources/home/images/ic_jg.png" alt="">--%>
                <%--<p class="type"><a href="javascript:;">机构账户</a></p>--%>
                <%--<p class="organization"><a href="javascript:;">陕师大机构</a></p>--%>
            <%--</li>--%>

            <%--<li class="item">--%>
                <%--<img src="${ctx}/resources/home/images/ic_js.png" alt="">--%>
                <%--<p class="type"><a href="javascript:;">教师账户</a></p>--%>
                <%--<p class="organization"><a href="javascript:;">邮电大学</a></p>--%>
            <%--</li>--%>
        </ul>
    </form>
</div>

<script>
    $('a').click(function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);

        //alert($(this).attr("href"))
        window.parent.location.href = $(this).attr("href");

    })
</script>
</body>
</html>
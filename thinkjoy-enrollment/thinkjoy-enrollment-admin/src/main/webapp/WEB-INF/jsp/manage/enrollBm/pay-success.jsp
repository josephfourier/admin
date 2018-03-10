<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>报名信息</title>
    <link rel="stylesheet" href="${basePath}/resources/mobile/lib/frozenui/css/frozen.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/pay.css?t=<%=new Date().getTime()%>">

    <script src="${basePath}/resources/mobile/lib/jquery.js"></script>

    <script src="${basePath}/resources/mobile/js/dialog.js?t=<%=new Date().getTime()%>"></script>
</head>

<body ontouchstart>
<header class="ui-header-positive" id="header">
    <div class="img-container">
        <img src="${basePath}/resources/mobile/imgs/img_zfcg.png" alt="">
    </div>
</header>
<section class="ui-container">
    <div class="pay-stat success">
        <p>已支付</p>
        <p>¥<span>2000</span></p>
    </div>
    <button class="button">完成支付</button>
</section>
<script>
    var schoolCode = ${enrollStudent.schoolCode};

</script>
</body>
</html>
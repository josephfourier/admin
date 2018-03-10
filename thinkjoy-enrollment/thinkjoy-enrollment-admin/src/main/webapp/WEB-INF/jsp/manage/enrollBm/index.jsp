<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>${ucenterSchool.schoolName}入口选择</title>
    <link rel="stylesheet" href="${basePath}/resources/mobile/lib/frozenui/css/frozen.css?t=<%=new Date().getTime()%>">

    <script src="${basePath}/resources/mobile/lib/jquery.js"></script>

    <style>
        body{
            background-color: #fff;
        }
        #header{
            background-color: #fff;}
        #header>.wrap{
            padding-top: 45px;
            width: 282px;
            margin:0 auto;}

        #header .wrap a>img{width: 111px;}
        #header .wrap a{display: inline-block; }
        #header .wrap a:first-child{  margin-right: 45px; }

        #header .wrap>a>p{
            font-size: 15px;
            color: #222222;
            padding-top: 10px;
            display: block;
            position: relative;
            left: 8px;
            text-align: center;
            font-family:"Microsoft Yahei", Verdana, Simsun, "Segoe UI Web Light", "Segoe UI Light", "Segoe UI Web Regular", "Segoe UI", "Segoe UI Symbol", "Helvetica Neue", Arial}

    </style>
</head>

<body ontouchstart>
<header class="ui-header-positive" id="header">
    <div class="wrap">

        <a href="/enrollBm/bm/mobile?schoolCode=${schoolCode}">
            <img src="${basePath}/resources/mobile/imgs/enroll.png" alt="">
            <p>我要报名</p>
        </a>
        <a href="/enrollBm/bm/query?schoolCode=${schoolCode}">
            <img src="${basePath}/resources/mobile/imgs/query.png" alt="">
            <p>录取查询</p>
        </a>
    </div>

</header>

<script>


</script>
</body>
</html>
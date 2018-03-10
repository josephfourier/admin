<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>批量打印</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        * {
            padding: 0;
            margin: 0;
        }

        h2 {
            font-size: 25px;
            text-align: center;
            margin: 20px 0 40px;
        }

        h3 {
            font-size: 20px;
            text-align: left;
            margin: 10px 0;
        }

        table {
            background-color: transparent;
            border-collapse: collapse;
        }

        .table {
            width: 800px;
            margin-bottom: 20px;
        }

        .table th,
        .table td {
            text-align: center;
            vertical-align: middle;
            border: 1px solid #dddddd;
            height: 60px;
        }

        .table th {
            font-weight: bold;
        }

        .table thead th {
            vertical-align: middle;
        }

        .container {
            width: 800px;
            margin: 0 auto;
        }

        .btn {
            display: inline-block;
            *display: inline;
            padding: 4px 12px;
            margin-bottom: 0;
            *margin-left: .3em;
            font-size: 14px;
            line-height: 20px;
            color: #333333;
            text-align: center;
            text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
            vertical-align: middle;
            cursor: pointer;
            background-color: #f5f5f5;
            *background-color: #e6e6e6;
            background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
            background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
            background-repeat: repeat-x;
            border: 1px solid #cccccc;
        }
        @media print {
            .btn{
                display:none;
            }
        }

        .printArea {
            width: 794px;
            height: 998px;
            margin: 0 auto;
        }
    </style>
</head>

<body>

<div class="container">
    <button type="button" id="btn" onclick="" class="btn btn-primary printer">批量打印通知书</button>

    <c:forEach items="${objlist}" var="detail">
        <div class="printArea">
            <img src="${detail}" STYLE="page-break-after: always;width:794px;height:1023px;"/>
        </div>
    </c:forEach>
</div>

<script>
    var btn = document.getElementById('btn');
    btn.addEventListener('click', function () {
        window.print();
    })
</script>
</body>
</html>

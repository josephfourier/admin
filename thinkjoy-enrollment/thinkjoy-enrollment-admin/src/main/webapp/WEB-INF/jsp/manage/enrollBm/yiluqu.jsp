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
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/check.css?t=<%=new Date().getTime()%>">

    <script src="${basePath}/resources/mobile/lib/jquery.js"></script>

    <script src="${basePath}/resources/mobile/js/dialog.js?t=<%=new Date().getTime()%>"></script>
</head>

<body ontouchstart>


<header class="ui-header-positive enroll" id="header">
    <div class="desc enroll-desc">
        <p>恭喜您<br/>已被录取</p>
    </div>
</header>


<section class="ui-container">
   <div id="detail">
       <div class="item">
           <span>姓名：</span><span>${enrollStudent.studentName}</span>
       </div>
       <div class="item">
           <span>身份证号：</span>
           <span>${enrollStudent.idcard}</span>
       </div>
       <div class="item">
           <span>手机号码：</span>
           <span>${enrollStudent.phone}</span>
       </div>
       <div class="item">
           <span>准考证号：</span>
           <span>${enrollStudent.examnum}</span>
       </div>
       <div class="item">
           <span>考生号：</span>
           <span>${enrollStudent.examineeNumber}</span>
       </div>
       <div class="item">
           <span>录取专业：</span>
           <span>${enrollStudent.specialtyName}</span>
       </div>
       <div class="item">
           <span>缴费状态：</span>
           <span>
            <c:if test="${enrollStudent.feeStatus==1}">
                未交费
            </c:if>
           <c:if test="${enrollStudent.feeStatus==2}">
               预交费
           </c:if>
           <c:if test="${enrollStudent.feeStatus==3}">
               已缴费
           </c:if>
           <c:if test="${enrollStudent.feeStatus==4}">
               待退费
           </c:if>
           <c:if test="${enrollStudent.feeStatus==5}">
               已退费
           </c:if>
           </span>
       </div>
       <div class="item">
           <span>已支付金额：</span>
           <span><span class="money">${enrollChargedetail.money}</span>元</span>
       </div>

      <%-- <c:if test="${status eq 0 and fee ne 1}">
           <button class="query">立即缴费</button>
       </c:if>

       <c:if test="${status eq 0 and fee eq 1}">
           <button class="query">申请退费</button>
       </c:if>--%>
   </div>
</section>
<script>
    var schoolCode = ${enrollStudent.schoolCode};
</script>
</body>
</html>
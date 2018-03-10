<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
您好,${sessionScope.sysUser.username}<br/>
<a href="/logout">注销</a>

<div>
    用户身份:${userIdentity.usertypeName}
</div>
    用户应用列表:
    <br/>
    <c:forEach items="${apps}" var="app">

        <li>
            应用图标: <img src="${app.icon}" style="width: 100px;height: 100px"/>
            应用名称: <span>${app.appName}</span>
            应用连接: <a href="${app.redirectUri}">重定向连接</a>
        </li>

    </c:forEach>

<script>
</script>
</body>
</html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp" %>
<%--访问localhost:4444即根路径默认访问该页面,然后会重定向到Manage/index,返回首页--%>
<c:redirect url="/manage/index"/>
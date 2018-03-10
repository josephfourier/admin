<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Title</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/resources/css/style2.css">
</head>
<body>
	<div id="header">
		<div class="wrap">
			<div class="logo">
				<h1>职教云</h1>
				<b class="spacer"></b>
				<h2>管理平台</h2>
			</div>
			<ul class="h-nav">
				<li><a href="/login">首页</a></li>
				<li class="spacer"></li>
				<li><a href="javascript:;">帮助</a></li>
				<li class="spacer"></li>
				<li>服务电话 <span>400-9933-135</span></li>
			</ul>
		</div>
	</div>
	<div class="main">
		<div class="wrap">
			<div class="forget-panel">
				<form action="" class="forget-form">
					<p class="form-title">找回密码</p>
					<div class="form-item">
						<label for="" class="label-a"></label> <input type="text"
							placeholder="请输入账号或账号对应的手机号码" name="username" id="username"/>
					</div>
					<div class="tip"></div>
			
					<div class="form-item inline">
						<label for="" class="label-v"></label> <input type="text" placeholder="请输入验证码" class="input-v" name="captcha" maxlength="4"/>
					</div>
					
					<button class="btn-v inline" id="btn-v"></button>
					<div class="tip"></div>
					<div class="form-item">
						<label for="" class="label-p"></label> <input type="password"
							placeholder="请输入新密码" name="password" id="password"/>
					</div>
					<div class="tip"></div>
					<div class="form-item">
						<label for="" class="label-p"></label> <input type="password"
							placeholder="请确认再次输入新密码" name="cfmpassword"/>
					</div>
					<div class="tip"></div>
					<div class="form-item no c">
						<button class="btn-confirm">确认修改</button>
					</div>
				</form>
				<div class="mascot_tip">
					<img src="${ctx}/images/mascot_ok.png" alt="" style="margin: auto;"/>
					<p class="mascot_tip">修改密码成功 <a href="/index2">现在登录?</a> </p>
				</div>
			</div>
		</div>
		<div id="copyright">Copyright 1999-2016 全通教育集团（广东）股份有限公司 版权所有
			粤ICP备10021799号-34</div>
	</div>

	<script type="text/javascript" src="${ctx}/resources/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/login.js"></script>
	
	<script>
		
	</script>
</body>
</html>
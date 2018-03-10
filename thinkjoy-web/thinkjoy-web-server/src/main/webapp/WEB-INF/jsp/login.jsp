<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>管理平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit"/>
    <link rel="stylesheet" href="${ctx}/resources/public/css/reset.css">
    <link rel="stylesheet" href="${ctx}/resources/public/css/header.css">
    <link rel="stylesheet" href="${ctx}/resources/public/css/login.css">

    <!--[if lt IE 8]>
    <script type="text/javascript" src="${ctx}/resources/js/json.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
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
<%--<body>
	<div id="header">
		<div class="wrap">
			<div class="logo">
				<h1>职教云</h1>
				<b class="spacer"></b>
				<h2>管理平台</h2>
			</div>
			<ul class="h-nav">
				<li><a href="http://www.zhijiaoyun.net">首页</a></li>
				<li class="spacer"></li>
				<li><a href="javascript:;">帮助</a></li>
				<li class="spacer"></li>
				<li>服务电话 <span>400-9933-135</span></li>
			</ul>
		</div>
	</div>
	<div class="content">

		<div class="login-wrap">
			<div class="wrap clear">
				<div class="login-panel fr">
					<form action="" class="login-form" method="post" id="login-form">
						<input type="hidden" value="${backurl}" id="backurl" name="backurl"/>
						<p class="form-title">登录</p>
						<div class="form-item">
							<label for="username" class="name-label"></label> <input
								type="text" id="username" placeholder="请输入账号" name="username">
						</div>
						<div class="tip"></div>

						<div class="form-item p">
							<label for="password" class="pwd-label"></label> <input
								type="password" id="password" placeholder="请输入密码"
								name="password">
						</div>
						<div class="tip"></div>
						<div class="form-item no">
							<button class="btn-login">登 录</button>
						</div>
						<div class="tip no"></div>
						<div class="form-item no">
							<input type="hidden" value="" name="rememberMe"/>
							<i class="chk"></i><span id="rememberMe">记住密码</span>
							<a href="/reset2" id="reset" class="fr">忘记密码?</a>
						</div>
					</form>
				</div>
			</div>
			<div class="login-banner">
				<div class="wrap">
&lt;%&ndash; 					<img class="banner-img" src="${ctx}/images/banner_img.png" alt="" />
 &ndash;%&gt;					<img class="banner-img" src="${ctx}/resources/images/bg_img.png" alt="" />
					<div class="banner-bg"></div>
				</div>
			</div>
		</div>
		<div class="wave">
		
		</div>
	</div>
<div class="footer">
	<p>Copyright 2017 西安习悦信息技术有限公司 版权所有 陕ICP备14010330号-13</p><p class="">全通教育集团（广东）股份有限公司</p>
</div>
</div><script>var BASE_PATH = '${ctx}';</script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/login.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
</body>--%>

<body>
<div id="header">
    <div class="wrap">
        <div class="logo">
            <h1>职教云</h1>
            <b class="spacer"></b>
            <h2>管理平台</h2>
        </div>
        <ul class="h-nav">
            <li><a href="./index.html">首页</a></li>
            <li class="spacer"></li>
            <li><a href="javascript:;">帮助</a></li>
            <li class="spacer"></li>
            <li>服务电话 <span>400-9933-135</span></li>
        </ul>
    </div>
</div>
<div class="content">
    <div class="wrap">
        <div class="main clear">
            <div class="banner fl">
                <img src="${ctx}/resources/images/bg_img.png" alt="">
            </div>

            <div class="login-panel fr">
                <form action="" class="login-form" method="post" id="login-form">
                    <p class="form-title">登录</p>
                    <div class="form-item">
                        <label for="username" class="label name-label"></label>
                        <input type="text" id="username" placeholder="请输入账号" name="username">
                    </div>
                    <div class="tip"></div>

                    <div class="form-item p">
                        <label for="password" class="label pwd-label"></label> <input
                            type="password" id="password" placeholder="请输入密码"
                            name="password">
                    </div>
                    <div class="tip"></div>
                    <div class="form-item no">
                        <input type="submit" class="btn-login" value="登录">
                    </div>
                    <div class="form-item help">
                        <i class="chk"></i><span id="remember">记住密码</span>
                        <input name="rememberMe" id="rememberMe" type="hidden"/>
                        <a href="/reset2" id="reset" class="">忘记密码?</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="footer">

    <p>Copyright 2017 西安习悦信息技术有限公司 版权所有 陕ICP备14010330号-13</p><p class="">全通教育集团（广东）股份有限公司</p>

</div>
<script>var BASE_PATH = '${ctx}';</script>
<%--
<script type="text/javascript" src="${ctx}/resources/js/jquery-3.2.1.min.js"></script>
--%>
<script type="text/javascript" src="${ctx}/resources/js/jquery-1.9.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.cookie.js"></script>
<%--
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
--%>
<script type="text/javascript" src="${ctx}/resources/js/validator.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/login.js"></script>

</body>
</html>
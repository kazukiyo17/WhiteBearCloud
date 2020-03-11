<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<!-- Bootstrap -->
<style>
html {
	width: 100%;
	height: 100%
}

body {
	width: 100%;
	height: 100%;
	background: url(images/polarbear.jpg) no-repeat;
	background-size: cover;
}

#register {
	width: 450px;
	margin: 50px auto;
}

#btn {
	margin-left: 197px;
	margin-top: -24px;
	width: 120px;
	height: 25px;
	font-size: 14px;
	color: #7904c9
}

#loginForm{
	margin-top:200px;
}
</style>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>

<script type="text/javascript">
	$(function() {
		$("#btn").click(function() {
			if ($("#signupAccount").val()) {

				$.ajax({
					type : "POST",
					url : "SendEmailServlet?random" + Math.random(),
					data : {
						signupAccount : $("#signupAccount").val(),
					},
					success : function(data) {
						alert("验证码发送成功，请注意查收。");
					},
				})
			} else {
				alert("邮箱发送失败");
				$("#notice").html("请填写邮箱");

				setTimeout(function() {
					$("#notice").hide();
				}, 1000);
			}
		});
		//  判断用户是否可以注册
		$("#submit").click(function() {
			if ($("#signupAccount").val()) {

				$("#RegisterForm").submit();

			} else { //  如果没有值
				$("#notice").html("请填写完整信息");
				setTimeout(function() {
					$("#notice").hide();
				}, 1000);
			}
		});
	});
</script>

</head>
<body id="login">
	<div class="container">
		<form class="form-signin" name="loginForm" method="post" action=""
			novalidate  id="loginForm">
			<h2 class="form-signin-heading">请登录</h2>
			<label for="inputAccount" class="sr-only">账号</label> <input
				type="text" name="userName" id="userName" class="form-control"
				placeholder="用户名"> <label for="inputPassword"
				class="sr-only">密码</label> <input type="password" name="pwd"
				id="pwd" class="form-control" placeholder="密码"> <label
				class="checkbox"> <input type="checkbox" value="remember-me">Remember
				me
			</label>
			<button class="btn btn-large btn-primary" onclick="ToLogin()">登录</button>
			<button class="btn btn-large" data-toggle="modal"
				data-target="#signupModel">注册</button>
			<c:if test="${not empty message}">
				<div class="alert alert-danger alert-dismissable"
					style="display: none;" id="registerError">
					<strong>${message}</strong>
				</div>
			</c:if>
		</form>

	</div>
	<!-- /container -->
	<!-- 注册模态框  -->
	<div class="modal fade" aria-hidden="true" style="display: none;"
		id="signupModel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">注册</h4>
				</div>



				<div id="register">
					<form class="form-horizontal" name="RegistForm" method="post"
						action="RegisterServlet" novalidate>
						<fieldset>
							<div id="legend" class="">
								<legend class="">用户注册</legend>
							</div>

							<!-- name = "username" -->
							<div class="control-group">
								<label class="control-label" for="focusedInput">用户名</label>
								<div class="controls">
									<input class="input-medium focused" id="signupUsername"
										name="signupUsername" type="text" placeholder="小写字母、数字或-"
										required>
								</div>
							</div>


							<!-- name="password" -->
							<div class="control-group">
								<label class="control-label" for="focusedInput">密码</label>
								<div class="controls">
									<input type="password" placeholder="请输入6位数密码..."
										class="input-medium focused" id="signupPwd" name="signupPwd"
										required>
								</div>
							</div>


							<!-- name="email" id="email"-->
							<div class="control-group">
								<label class="control-label" for="focusedInput">邮箱地址</label>
								<div class="controls">
									<input type="email" placeholder="请填写邮箱地址..." class=""
										input-medium focused""
								id="signupAccount"
										name="signupAccount" required>
								</div>
							</div>

							<!-- 							<div class="control-group">
								<label class="control-label" for="focusedInput">确认密码</label>
								<div class="controls">
									<input class="input-xlarge focused" id="code"
										name="code" type="password">
								</div>
							</div> -->

							<!-- id="code" name="code",令type="password"可以隐藏输入内容-->
							<div class="control-group">
								<label class="control-label" for="focusedInput">验证码</label>
								<div class="controls">
									<input type="text" name="code" id="code"
										placeholder="请输入邮箱的验证码" class="input-medium focused" required>
									<input type="button" name="btn" class="btn btn-default"
										id="btn" value="发送验证码" />
								</div>
							</div>

							<span id="notice" class="hide">请先完成邮箱验证</span><br />

							<!-- 							<div class="form-actions">
								<button class="btn btn-large btn-primary" onclick="ToSignup()">提交</button>
								<button class="btn btn-large" data-dismiss="modal">取消</button>
							</div> -->
							<div class="form-group">
								<div class="form-actions">
									<!-- <a href="index.jsp" class="btn btn-success">返回登录</a> <input
								class="btn btn-info" type="submit" id="submit" value="邮箱注册" /> -->
									<input class="btn btn-large btn-primary" " type="submit"
										id="submit" value="提交" />
									<!-- <button class="btn btn-large btn-primary" onclick="ToSignup()">提交</button> -->
									<button class="btn btn-large" data-dismiss="modal">取消</button>
								</div>


							</div>
				</div>
				</fieldset>
				</form>
			</div>
		</div>
	</div>
	</div>
	<script src="vendors/jquery-1.9.1.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
	<script type="text/javascript" src="js/signup.js"></script>
</body>
</html>
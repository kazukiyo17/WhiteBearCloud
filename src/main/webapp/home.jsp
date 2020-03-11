<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<style>
.table tbody tr td {
	vertical-align: middle;
}
</style>
<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="assets/DT_bootstrap.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>

<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->



<!--
	@author:全员均参与编写，具体见函数前
	@description:用户界面
	@lastmodifiedtime:2019年9月10日
-->
<script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>

<!-- 
	@author:冯书逸
	@description：修改用户信息
	-->
<script type="text/javascript">
		$(document).ready(function() {
			$("#updatebtn").click(function() {
				if ($("#updateAccount").val()) {
	
					$.ajax({
						type : "POST",
						url : "SendEmailServlet?random" + Math.random(),
						data : {
							updateAccount : $("#updateAccount").val(),
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
			//  判断用户是否可以修改用户信息
			$("#updatesubmit").click(function() {
				if ($("#updateAccount").val()) {
	
					$("#UploadForm").submit();
	
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

<!-- 
		@author:李旭芸
		@description：home界面大体框架
-->
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#"><img src="images/logo.png">白熊云</a>
				<div class="nav-collapse collapse">
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="icon-user"></i> ${trueusername} <i class="caret"></i>
						</a>
							<ul class="dropdown-menu">
								<li><a tabindex="-1" href="LogoutServlet">退出</a></li>
								<li class="divider"></li>
								<li><a tabindex="-1" href="login.html">注销</a></li>
								<li><button class="btn btn-link" data-toggle="modal"
										data-target="#updateModel">修改用户名密码</button></li>
							</ul></li>
					</ul>

				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>


	<!-- 
	@aurthor：冯书逸
	@description：修改用户信息模态框  
-->
	<div class="modal fade" aria-hidden="true" style="display: none;"
		id="updateModel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">修改</h4>
				</div>



				<div id="upload">
					<form class="form-horizontal" name="UploadForm" method="post"
						action="ChangeUserInformationServlet" novalidate>
						<fieldset>
							<div id="legend" class="">
								<legend class="">更新用户信息</legend>
							</div>

							<!-- name = "username" -->
							<div class="control-group">
								<label class="control-label">新用户名</label>
								<div class="controls">
									<input class="input-medium focused" id="uploadUsername"
										name="uploadUsername" type="text" placeholder="小写字母、数字或-"
										required>
								</div>
							</div>


							<!-- name="password" -->
							<div class="control-group">
								<label class="control-label">新密码</label>
								<div class="controls">
									<input type="password" placeholder="请输入6位数密码..."
										class="input-medium focused" id="uploadPwd" name="uploadPwd"
										required>
								</div>
							</div>


							<!-- name="email" id="email"-->
							<div class="control-group">
								<label class="control-label">邮箱地址</label>
								<div class="controls">
									<input type="email" placeholder="请填写邮箱地址..."
										class="
								  input-medium focused" id="updateAccount"
										name="updateAccount" required>
								</div>
							</div>


							<div class="control-group">
								<label class="control-label" for="focusedInput">验证码</label>
								<div class="controls">
									<input type="text" name="code" id="code"
										placeholder="请输入邮箱的验证码" class="input-medium focused" required>
									<input type="button" name="updatebtn" class="btn btn-default"
										id="updatebtn" value="发送验证码" />
								</div>
							</div>

							<span id="notice" class="hide">请先完成邮箱验证</span><br />


							<div class="form-group">
								<div class="form-actions">
									<input class="btn btn-large btn-primary" type="submit"
										id="updatesubmit" value="提交" />
									<button class="btn btn-large" data-dismiss="modal">取消</button>
								</div>


							</div>
				</div>
				</fieldset>
				</form>
			</div>
		</div>
	</div>


	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3" id="sidebar">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
					<li class="active"><a href="FileListServlet"><i
							class="icon-chevron-right"></i><i class="icon-list"></i>全部文件</a></li>
					<li><a href="FileListServlet?filter=picture"><i
							class="icon-chevron-right"></i><i class="icon-picture"></i>图片</a></li>
					<li><a href="FileListServlet?filter=music"><i
							class="icon-chevron-right"></i><i class="icon-music"></i>音频</a></li>
					<li><a href="FileListServlet?filter=video"><i
							class="icon-chevron-right"></i> <i class="icon-film"></i>视频</a></li>
					<li><a href="FileListServlet?filter=document"><i
							class="icon-chevron-right"></i><i class="icon-file"></i>文档</a></li>
					<li><a href="FileListServlet?filter=zip"><i
							class="icon-chevron-right"></i><i class="icon-folder-open"></i>压缩文件</a></li>
					<li><a href="FileListServlet?filter=torrent"><i
							class="icon-chevron-right"></i><i class="icon-leaf"></i>种子</a></li>
				</ul>
				<br> <br> <br> <br> <br> <br> <br>




				<form action="servlet/FileListServlet" method="post">

					<br>
					<div class="">空间使用： ${contain} MB/100MB</div>
				</form>

			</div>
			<!--/span-->
			<div class="span9" id="content">
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">我的网盘</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<div class="table-toolbar">
									<div class="btn-group">
										<label>
											<button class="btn btn-success" data-toggle="modal"
												data-target="#uploadModel">
												&emsp; 上传 &emsp; <i class="icon-plus icon-white"></i>
											</button>
											<button class="btn btn-primary" data-toggle="modal"
												data-target="#folderModel">
												&emsp;新建文件夹&emsp; <i class="icon-plus icon-white"></i>
											</button>
											<button class="btn" data-toggle="modal"
												data-target="#dshareModel">&emsp;链接下载&emsp;</button>
											<button class="btn" data-toggle="modal"
												data-target="#saveshareModel">&emsp;链接保存&emsp;</button>
										</label>
									</div>

									<!-- 
										@author:林源
										@description：上传模态框
									-->
									<!-- 上传模态框  -->
									<div class="modal fade" id="uploadModel">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h4 class="modal-title" id="myModalLabel">上传文件</h4>
												</div>
												<div class="modal-body">
													<form method="post" action="UploadServlet"
														enctype="multipart/form-data">
														<input class="input-medium search-query" type="file"
															name="file" /> <input class="btn btn-primary"
															type="submit" value="上传" />
													</form>
												</div>
											</div>
										</div>
									</div>
									<!-- 
										@author:龚德超
										@description：通过文件名查询文件
									-->
									<!-- 查询  -->
									<div class="btn-group pull-right">
										<label>
											<form action="SearchFileServlet"
												enctype="multipart/form-data" method="get">
												<input type="text" class="input-medium search-query"
													name="searchthing">
												<button class="btn btn-link" type="submit">
													<i class="icon-search"></i>
												</button>
											</form>
										</label>
									</div>
								</div>

								<!-- 
										@author:龚德超
										@description：新建文件夹、文件夹的进出
									-->
								<!-- 进入指定文件夹或返回 -->
								<div id="example2_wrapper"
									class="dataTables_wrapper form-inline" role="grid">
									<div class="row">
										<div class="span6">
											<div id="example2_length" class="dataTables_length">
												<div align="left">
													<form action="BackServlet" enctype="multipart/form-data"
														method="get">
														<button class="btn btn-link" type="submit">
															<i class="icon-arrow-up"></i>
														</button>

														<c:forTokens items="${filepath}" delims="/" var="name">
															<c:url value="FileListServlet" var="listurl">
																<c:param name="endpath" value="${name}"></c:param>
															</c:url>
															<a href="${listurl}">${name}/</a>
														</c:forTokens>
													</form>
												</div>
											</div>
											<br>
										</div>
										<div class="span6">
											<div id="example2_length" class="dataTables_length">
												<div class="btn-group pull-right">
													<form action="FileListServlet" method="get"
														id="sortingways">
														<select style="width: 100px;" name="sortingways"
															onchange="on_choice()">
															<option value="time"
																${sortingways=="time" ?'selected':''}>上传日期</option>
															<option value="name"
																${sortingways=="name" ?'selected':''}>文件名</option>
															<option value="size"
																${sortingways=="size" ?'selected':''}>文件大小</option>
														</select>
													</form>
												</div>
											</div>
										</div>
									</div>

									<!-- 新建文件夹模态框  -->
									<div class="modal fade" id="folderModel">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h4 class="modal-title" id="myModalLabel">新建文件夹</h4>
													<form action="FolderServlet" enctype="multipart/form-data"
														method="get">
														<input type="text" name="newfoldername">
														<button class="btn btn-primary" type="submit">新建</button>
													</form>
												</div>
												<div class="modal-body"></div>
											</div>
										</div>
									</div>


									<!-- 
										@author:林源
										@description：通过分享链接下载保存文件
									-->
									<!-- 分享下载文件模态框  -->
									<div class="modal fade" id="dshareModel">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h4 class="modal-title" id="myModalLabel">分享文件下载</h4>
													<form action="ShareDownServlet"
														enctype="multipart/form-data" method="get">
														<input type="text" name="shareurl" placeholder="输入分享链接">
														<button class="btn btn-primary" type="submit">确认</button>
													</form>
												</div>
												<div class="modal-body"></div>
											</div>
										</div>
									</div>
									<!-- 分享保存文件模态框  -->
									<div class="modal fade" id="saveshareModel">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h4 class="modal-title" id="myModalLabel">分享文件保存</h4>
													<form action="ShareSaveServlet"
														enctype="multipart/form-data" method="get">
														<input type="text" name="saveurl" placeholder="输入分享链接">
														<button class="btn btn-primary" type="submit">确认</button>
													</form>
												</div>
												<div class="modal-body"></div>
											</div>
										</div>
									</div>


									<!-- 
										@author:冯书逸、龚德超
										@description：显示文件
									-->
									<table cellpadding="0" cellspacing="0" border="0"
										class="table table-striped table-bordered dataTable"
										id="filestable">
										<thead>
											<tr>
												<th>文件名</th>
												<th>大小</th>
												<th>上传时间</th>
												<th>操作</th>
											</tr>
										</thead>
										</thead>
										<tbody>
											<!-- 遍历第一遍（文件夹）  -->
											<c:forEach var="files" items="${files}">
												<c:if test="${files.getType() eq 'folder'}">
													<tr>
														<th><c:url value="FileListServlet" var="listurl">
																<c:param name="filepath" value="${files.getPath()}">
																</c:param>
																<c:param name="foldername"
																	value="${files.getFileName()}"></c:param>
															</c:url> <a href="${listurl}">${files.getFileName()}</a></th>

														<th>${files.getSize()}字节</th>
														<th>${files.getUpdateTime()}</th>

														<th><c:url value="DeleteFolderServlet"
																var="deletefolderurl">
																<c:param name="foldername"
																	value="${files.getFileName()}"></c:param>
															</c:url> <a href="${deletefolderurl}">删除</a></th>

													</tr>
												</c:if>


											</c:forEach>

											<!-- 遍历第二遍（文件）  -->
											<c:forEach var="files" items="${files}">
												<c:if test="${files.getType() ne 'folder'}">
													<tr>
														<th>${files.getFileName()}</th>

														<th>${files.getSize()}字节</th>
														<th>${files.getUpdateTime()}</th>

														<th><c:url value="DownloadServlet" var="downurl">
																<c:param name="filename" value="${files.getFileName()}">
																</c:param>
															</c:url> <a href="${downurl}">下载</a> <c:url value="DeleteServlet"
																var="deleteurl">
																<c:param name="filename" value="${files.getFileName()}">
																</c:param>
															</c:url> <a href="${deleteurl}">删除</a> <c:url
																value="ShareServlet" var="shareurl">
																<c:param name="filename" value="${files.getFileName()}">
																</c:param>
																<c:param name="uuidname" value="${files.getUuidName()}">
																</c:param>
																<c:param name="username" value="${files.getUser()}">
																</c:param>
																<c:param name="size"
																	value="${String.valueOf(files.getSize())}">
																</c:param>
																<c:param name="filetype" value="${files.getType()}">
																</c:param>

															</c:url> <a href="${shareurl}">分享</a> <c:url
																value="ImageShowServlet" var="showurl">
																<c:param name="filename" value="${files.getFileName()}">
																</c:param>
															</c:url> <a href="${showurl}">预览</a></th>

													</tr>

												</c:if>

											</c:forEach>

										</tbody>

										<%
                        String mess=(String)request.getAttribute("message");
                        if("".equals(mess)  || mess==null){

                        }
                        else{%>
										<script type="text/javascript">
											alert("<%=mess%>");
										</script>
										<%request.setAttribute("message", null);
}%>

										</div>
										</div>
										</fieldset>
										</form>
										</div>
										</div>
										</div>
										</div>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- /block -->
				</div>
			</div>
		</div>
		<hr>
	</div>
	<!--/.fluid-container-->
	<script src="vendors/jquery-1.9.1.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="vendors/datatables/js/jquery.dataTables.min.js"></script>
	<script src="assets/scripts.js"></script>
	<script src="assets/DT_bootstrap.js"></script>
	<script>
		function on_choice() {
			var form = document.getElementById('sortingways');
			form.submit();
		}
	</script>
</body>

</html>
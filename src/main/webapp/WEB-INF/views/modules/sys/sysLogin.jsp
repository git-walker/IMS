<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productEnName')} 登录</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>	
</head>
<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="ace-icon fa fa-leaf green"></i>
								<span class="red">${fns:getConfig('productEnName')}</span>
								<span class="white" id="id-text2">${fns:getConfig('productZhName')}</span>
							</h1>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box" class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger" id="messageBox">
											<i id="message-icon" class="ace-icon fa fa-coffee green"></i>
											请输入您的验证信息
										</h4>

										<div class="space-6"></div>
										
										<form id="loginForm" action="${ctx}/login" method="post">
											<fieldset>
												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
														<input type="text" id="username" name="username" class="form-control" placeholder="用户名" value="admin"/>
														<i class="ace-icon fa fa-user"></i>
													</span>
												</label>

												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
														<input type="password" id="password" name="password" class="form-control" placeholder="密码" />
														<i class="ace-icon fa fa-lock"></i>
													</span>
												</label>

												<div class="space"></div>

												<div class="clearfix">
													<label class="inline">
														<input type="checkbox" class="ace" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/>
														<span class="lbl"> 记住我</span>
													</label>

													<button type="button" id="submitLogin" class="width-35 pull-right btn btn-sm btn-primary">
														<i class="ace-icon fa fa-key"></i>
														<span class="bigger-110">登录</span>
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>

									</div><!-- /.widget-main -->

									<div class="toolbar clearfix">
										<div>
											<a href="#" data-target="#forgot-box" class="forgot-password-link">
												<i class="ace-icon fa fa-arrow-left"></i>
												忘记密码？
											</a>
										</div>

										<div>
											<a href="#" data-target="#signup-box" class="user-signup-link">
												用户注册
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div>
								</div><!-- /.widget-body -->
							</div><!-- /.login-box -->

							<div id="forgot-box" class="forgot-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header red lighter bigger">
											<i class="ace-icon fa fa-key"></i>
											重置密码
										</h4>

										<div class="space-6"></div>

										<form>
											<fieldset>
												<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="邮箱" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
												</label>

												<div class="clearfix">
													<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
														<i class="ace-icon fa fa-lightbulb-o"></i>
														<span class="bigger-110">发送</span>
													</button>
												</div>
											</fieldset>
										</form>
									</div><!-- /.widget-main -->

									<div class="toolbar center">
										<a href="#" data-target="#login-box" class="back-to-login-link">
											返回登录页面
											<i class="ace-icon fa fa-arrow-right"></i>
										</a>
									</div>
								</div><!-- /.widget-body -->
							</div><!-- /.forgot-box -->

							<div id="signup-box" class="signup-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="ace-icon fa fa-users blue"></i>
											新用户注册
										</h4>

										<div class="space-6"></div>

										<form>
											<fieldset>
												<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="邮箱" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
												</label>

												<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
												</label>

												<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
												</label>

												<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="确认密码" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
												</label>

												<label class="block">
													<input type="checkbox" class="ace" />
													<span class="lbl">
															我已仔细阅读并接受
															<a href="#">《用户注册条款》</a>
														</span>
												</label>

												<div class="space-24"></div>

												<div class="clearfix">
													<button type="reset" class="width-30 pull-left btn btn-sm">
														<i class="ace-icon fa fa-refresh"></i>
														<span class="bigger-110">重置</span>
													</button>

													<button type="button" class="width-65 pull-right btn btn-sm btn-success">
														<span class="bigger-110">注册</span>

														<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
													</button>
												</div>
											</fieldset>
										</form>
									</div>

									<div class="toolbar center">
										<a href="#" data-target="#login-box" class="back-to-login-link">
											<i class="ace-icon fa fa-arrow-left"></i>
											返回登录页面
										</a>
									</div>
								</div><!-- /.widget-body -->
							</div><!-- /.signup-box -->
							
						</div><!-- /.position-relative -->

						<div class="navbar-fixed-top align-right">
							<br />
							&nbsp;
							<a id="btn-login-dark" href="javascript:;" style="color: #faffbd">Dark</a>
							&nbsp;
							<span class="black">/</span>
							&nbsp;
							<a id="btn-login-blur" href="javascript:;" style="color: #faffbd">Blur</a>
							&nbsp;
							<span class="black">/</span>
							&nbsp;
							<a id="btn-login-light" href="javascript:;" style="color: #faffbd">Light</a>
							&nbsp; &nbsp; &nbsp;
						</div>
					</div>
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<!--[if gte IE 9]> -->
	<script type="text/javascript">
        window.jQuery || document.write("<script src='${ctxStatic}/jquery/jquery-2.1.1.js'>"+"<"+"/script>");
	</script>
	<!-- <![endif]-->
	<!--[if !IE]> -->
	<script type="text/javascript">
        window.jQuery || document.write("<script src='${ctxStatic}/jquery/jquery-2.1.1.js'>"+"<"+"/script>");
	</script>
	<!-- <![endif]-->
	<!--[if lt IE 9]>
	<script type="text/javascript">
		window.jQuery || document.write("<script src='${ctxStatic}/jquery/jquery-1.11.1.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	<script type="text/javascript">
        if('ontouchstart' in document.documentElement) document.write("<script src='${ctxStatic}/jquery/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="${ctxStatic}/bootstrap-3.3.6/js/bootstrap.min.js"></script>
	<!--[if lte IE 8]>
	<script src="${ctxStatic}/assets/js/excanvas.min.js"></script>
	<![endif]-->

	<script src="${ctxStatic}/jquery-form/jquery.form.js"></script>
	<script src="${ctxStatic}/jquery-validation/js/jquery.validate.min.js"></script>
	<script src="${ctxStatic}/jqGrid/jquery.jqGrid.src.js"></script>
	<script src="${ctxStatic}/jqGrid/i18n/grid.locale-cn.js"></script>
	<script src="${ctxStatic}/flash/zoom.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

            $(document).on('click', '.toolbar a[data-target]', function(e) {
                e.preventDefault();
                var target = $(this).data('target');
                $('.widget-box.visible').removeClass('visible');//hide others
                $(target).addClass('visible');//show target
            });

			$('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			
		
			$("#loginForm").validate({
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#messageBox"));
					$('#message-icon').removeClass('green');
					$('#message-icon').addClass('red');
				} 
			});
			$('#submitLogin').click(function(){
				$("#loginForm").submit();
			});
			$("body").keydown(function(event) {
    			if (event.keyCode == "13") {//keyCode=13是回车键
        		$("#loginForm").submit();
			    }
			});    
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</body>
</html>
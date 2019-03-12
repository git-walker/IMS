<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>修改密码</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
				<div class="widget-box">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h4 class="widget-title lighter">密码设置</h4>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<form:form id="inputForm" modelAttribute="user"	action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal" novalidate="novalidate">
							<!-- <fieldset>	 -->				
							<div class="form-group">
								<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="oldPassword">旧密码:</label>
				
								<div class="col-xs-12 col-sm-3">
									<div class="clearfix">
										<span class="block input-icon">
											<input type="password" name="oldPassword" id="oldPassword" class="width-100" />
											<i class="ace-icon fa fa-lock blue"></i>
										</span>
									</div>
								</div>
								<label class="control-label col-xs-12 col-sm-1 no-padding-right" for="newPassword">新密码:</label>
				
								<div class="col-xs-12 col-sm-3">
									<div class="clearfix">
										<span class="block input-icon">
											<input type="password" name="newPassword" id="newPassword" class="width-100"/>
											<i class="ace-icon fa fa-lock blue"></i>
										</span>
									</div>
								</div>
							</div>
				
							<div class="form-group">
								<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="confirmNewPassword">确认新密码:</label>
				
								<div class="col-xs-12 col-sm-3">
									<div class="clearfix">
										<span class="block input-icon">
											<input type="password" name="confirmNewPassword" id="confirmNewPassword" class="width-100" />
											<i class="ace-icon fa fa-lock blue"></i>
										</span>
									</div>
								</div>
							</div>
							
							<div class="clearfix form-actions">
								<div class="col-md-offset-3 col-md-9 no-padding-left">
									<button class="btn btn-info btn-sm" type="button">
										<i class="ace-icon fa fa-check bigger-110"></i>
										保存
									</button>
					
									&nbsp; &nbsp; &nbsp;
									<button class="btn btn-sm" type="reset">
										<i class="ace-icon fa fa-undo bigger-110"></i>
										重置
									</button>
								</div>
							</div>
							<form:hidden path="id" />
							<sys:message content="${message}" />
						</form:form>							
					</div>
				</div>
			</div>
		</div>		
	</div>
</div>


	<script type="text/javascript">
	var scripts = [null, null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		jQuery(function($){
		
			$("#submitForm").click(function(){
				$("#oldPassword").focus();
				$("#inputForm").validate({
					
					errorElement: 'div',
					errorClass: 'help-block',
					focusInvalid: false,
					ignore: "",
					rules: {
						oldPassword: {
							required: true,
							minlength: 3
						},
						newPassword: {
							required: true,
							minlength: 3
						},
						confirmNewPassword: {
							required: true,
							minlength: 3
						}
					},
			
					messages: {
						confirmNewPassword: {equalTo: "输入与上面相同的密码"}
					},
			
			
					highlight: function (e) {
						$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
					},
			
					success: function (e) {
						$(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
						$(e).remove();
					},
			
					errorPlacement: function (error, element) {
						if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
							var controls = element.closest('div[class*="col-"]');
							if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
							else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
						}
						else if(element.is('.select2')) {
							error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
						}
						else if(element.is('.chosen-select')) {
							error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
						}
						else error.insertAfter(element.parent());
					},
			
					submitHandler: function (form) {
					},
					invalidHandler: function (form) {
					}
				});
			})
	
		});
	});
	</script>
	
	
	<%-- <form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal form-bg2">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" class="required" equalTo="#newPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form> --%>
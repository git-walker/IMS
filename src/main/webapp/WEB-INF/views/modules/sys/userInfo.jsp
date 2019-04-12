<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<title>个人信息</title>
<div class="row" id = "canvas">
	<div class="col-xs-12">
	<div>
			<div id="user-profile-1" class="user-profile row">
				<div class="col-xs-12 col-sm-3 center">
					<div>
						<span class="profile-picture">
							<c:if test="${not empty user.photo }">
								<img id="avatar" data-pk="13" data-placement="right" class="editable img-responsive" alt="${user.name }" src="${ctxUploads}/userAvatar/${user.id}_large${user.photo}" onerror="this.src='${ctxStatic}/assets/avatars/profile-pic.jpg'"/>
							</c:if>
							<c:if test="${empty user.photo}">
								<img id="avatar" data-pk="13" data-placement="right" class="editable img-responsive" alt="${user.name }" src="${ctxStatic}/assets/avatars/profile-pic.jpg" />
							</c:if>
						</span>

						<div class="space-4"></div>

						<div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
							<div class="inline position-relative">
								<a href="#" class="user-title-label">
									<span class="white">${user.name}</span>
								</a>
							</div>
						</div>
					</div>

					<div class="space-6"></div>

					<div class="profile-contact-info">
						<div class="profile-contact-links align-left">
							<c:forEach items="${user.roleList}" var="role" varStatus="idxStatus">
								<span class="label label-lg label-purple arrowed padding-6">${role.name}</span>
								<c:if test="${idxStatus.count%2 == '0'}">
								<br>
								</c:if>
							</c:forEach>
						</div>
					</div>

					<div class="hr hr12 dotted"></div>

					<%--<div class="clearfix">--%>
						<%--<c:forEach items="${menuList}" var="menu">--%>
							<%--<div class="grid3">--%>
								<%--<a href="#page${menu.href}">--%>
									<%--<span class="bigger-175 blue">${menu.taskCount}</span></a>--%>
								<%--<br />--%>
								<%--${menu.name}--%>
							<%--</div>--%>
						<%--</c:forEach>--%>
					<%--</div>--%>

					<div class="hr hr16 dotted"></div>
				</div>

				<div class="col-xs-12 col-sm-9" >
					<div class="profile-user-info profile-user-info-striped">
						<div class="profile-info-row">
							<div class="profile-info-name"> 用户名 </div>

							<div class="profile-info-value">
								<span class="editable" id="loginName">${user.loginName}</span>
							</div>
						</div>
						<div class="profile-info-row">
							<div class="profile-info-name"> 工号 </div>

							<div class="profile-info-value">
								<span class="editable" id="no">${user.no}</span>
							</div>
						</div>
						<div class="profile-info-row">
							<div class="profile-info-name"> 邮箱 </div>

							<div class="profile-info-value">
								<span class="editable" id="email">${user.email}</span>
							</div>
						</div>
						<div class="profile-info-row">
							<div class="profile-info-name"> 电话 </div>

							<div class="profile-info-value">
								<span class="editable" id="phone">${user.phone}</span>
							</div>
						</div>
						<div class="profile-info-row">
							<div class="profile-info-name"> 手机 </div>

							<div class="profile-info-value">
								<span class="editable" id="mobile">${user.mobile}</span>
							</div>
						</div>
						<div class="profile-info-row">
							<div class="profile-info-name"> 用户类型 </div>

							<div class="profile-info-value">
								<span class="editable" id="userType">${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</span>
							</div>
						</div>
						<div class="profile-info-row">
							<div class="profile-info-name"> 上次登录 </div>

							<div class="profile-info-value">
								<span>IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></span>
							</div>
						</div>
					</div>
					<div class="alert alert-success" style="margin-top:50px;display:none" >
						<a href="#" class="close" data-dismiss="alert">
							&times;
						</a>
						<div id="showDL">
						
						</div>
					</div>
					</div>
				</div>
			</div>

	</div>
</div>
<script type="text/javascript">
	var scripts = [ null,'${ctxStatic}/assets/js/x-editable/bootstrap-editable.js','${ctxStatic}/assets/js/x-editable/ace-editable.js', null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {
		jQuery(function($) {
			
			var u = navigator.userAgent;	
			// 如果不是谷歌浏览器，建议下载
			if(u.indexOf('AppleWebKit')<0){
				$("#showDL").html("当前浏览器不是谷歌浏览器，建议点击下载&nbsp;&nbsp;<a version='1' class='dlChrome' href='javascript:void(0)'>32位</a>&nbsp;&nbsp;\
				<a version='2' class='dlChrome' href='javascript:void(0)'>64位</a>").parent().show();
			} 
			
			$(document).on("click",".dlChrome",function(){
				var version = $(this).attr('version');
				location.href="${ctx}/sys/user/downloadChrome?version="+version;
			});

			$.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
			$.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>'+
										'<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';


			try {//ie8 throws some harmless exceptions, so let's catch'em

				//first let's add a fake appendChild method for Image element for browsers that have a problem with this
				//because editable plugin calls appendChild, and it causes errors on IE
				try {
					document.createElement('IMG').appendChild(document.createElement('B'));
				} catch(e) {
					Image.prototype.appendChild = function(el){}
				}

				var last_gritter;
				$('#avatar').editable({
					type: 'image',
					name: 'avatar',
					value: null,
					image: {
						//specify ace file input plugin's options here
						btn_choose: '修改头像',
						droppable: true,
						maxSize: 512000,//~500Kb

						//and a few extra ones here
						name: 'avatar',//put the field name here as well, will be used inside the custom plugin
						on_error : function(error_type) {//on_error function will be called when the selected file has a problem
							if(last_gritter) $.gritter.remove(last_gritter);
							if(error_type == 1) {//file format error
								last_gritter = $.gritter.add({
									title: 'File is not an image!',
									text: 'Please choose a jpg|gif|png image!',
									class_name: 'gritter-error gritter-center'
								});
							} else if(error_type == 2) {//file size rror
								last_gritter = $.gritter.add({
									title: 'File too big!',
									text: 'Image size should not exceed 100Kb!',
									class_name: 'gritter-error gritter-center'
								});
							}
							else {//other error
							}
						},
						on_success : function() {
							$.gritter.removeAll();
						}
					},
					url: function(params) {
						// ***UPDATE AVATAR HERE*** //
						var submit_url = '${ctx}/sys/user/saveAvatar';//please modify submit_url accordingly
						var deferred = null;
						var avatar = '#avatar';

						//if value is empty (""), it means no valid files were selected
						//but it may still be submitted by x-editable plugin
						//because "" (empty string) is different from previous non-empty value whatever it was
						//so we return just here to prevent problems
						var value = $(avatar).next().find('input[type=hidden]:eq(0)').val();
						if(!value || value.length == 0) {
							deferred = new $.Deferred;
							deferred.resolve();
							return deferred.promise();
						}

						var $form = $(avatar).next().find('.editableform:eq(0)');
						var file_input = $form.find('input[type=file]:eq(0)');
						var pk = $(avatar).attr('data-pk');//primary key to be sent to server

						var ie_timeout = null;


						if( "FormData" in window ) {
							var formData_object = new FormData();//create empty FormData object

							//serialize our form (which excludes file inputs)
							$.each($form.serializeArray(), function(i, item) {
								//add them one by one to our FormData
								formData_object.append(item.name, item.value);
							});
							//and then add files
							$form.find('input[type=file]').each(function(){
								var field_name = $(this).attr('name');
								var files = $(this).data('ace_input_files');
								if(files && files.length > 0) {
									formData_object.append(field_name, files[0]);
								}
							});

							//append primary key to our formData
							formData_object.append('pk', pk);

							deferred = $.ajax({
										url: submit_url,
									   type: 'POST',
								processData: false,//important
								contentType: false,//important
								   dataType: 'json',//server response type
									   data: formData_object
							})
						}
						else {
							deferred = new $.Deferred;

							var temporary_iframe_id = 'temporary-iframe-'+(new Date()).getTime()+'-'+(parseInt(Math.random()*1000));
							var temp_iframe =
									$('<iframe id="'+temporary_iframe_id+'" name="'+temporary_iframe_id+'" \
									frameborder="0" width="0" height="0" src="about:blank"\
									style="position:absolute; z-index:-1; visibility: hidden;"></iframe>')
									.insertAfter($form);

							$form.append('<input type="hidden" name="temporary-iframe-id" value="'+temporary_iframe_id+'" />');

							//append primary key (pk) to our form
							$('<input type="hidden" name="pk" />').val(pk).appendTo($form);

							temp_iframe.data('deferrer' , deferred);
							//we save the deferred object to the iframe and in our server side response
							//we use "temporary-iframe-id" to access iframe and its deferred object

							$form.attr({
									  action: submit_url,
									  method: 'POST',
									 enctype: 'multipart/form-data',
									  target: temporary_iframe_id //important
							});

							$form.get(0).submit();

							//if we don't receive any response after 30 seconds, declare it as failed!
							ie_timeout = setTimeout(function(){
								ie_timeout = null;
								temp_iframe.attr('src', 'about:blank').remove();
								deferred.reject({'status':'fail', 'message':'Timeout!'});
							} , 30000);
						}


						//deferred callbacks, triggered by both ajax and iframe solution
						deferred
						.done(function(result) {//success
							var res = result;//the `result` is formatted by your server side response and is arbitrary
							if(res.status == '1') {
								$(avatar).get(0).src = res.url+"?t="+new Date().getTime();
								//var reg=new RegExp("large","g");
								//newUrl=res.url.replace(reg,"middle");
								$('img.nav-user-photo')[0].src=res.url+"?t="+new Date().getTime();
								$('#navbarToggleImg')[0].src=res.url+"?t="+new Date().getTime();
							}
							else alert(res.message);
						})
						.fail(function(result) {//failure
							alert("There was an error");
						})
						.always(function() {//called on both success and failure
							if(ie_timeout) clearTimeout(ie_timeout);
							ie_timeout = null;
						});

						return deferred.promise();
						// ***END OF UPDATE AVATAR HERE*** //
					},

					success: function(response, newValue) {
					}
				});

				/**
				//let's display edit mode by default?
				$('#avatar').editable('show').on('hidden', function(e, reason) {
					if(reason == 'onblur') {
						$('#avatar').editable('show');
						return;
					}
					$('#avatar').off('hidden');
				})
				*/

			}catch(e) {}
		});
		if(location.protocol == 'file:') alert("For uploading to server, you should access this page using 'http' protocal, i.e. via a webserver.");
	});
	
</script>

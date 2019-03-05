<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" href="${ctxStatic}/assets/css/shade.css" />
<title>用户管理</title>
<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-sm-3 no-padding-right">
				<div class="widget-box widget-compact">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h5 class="widget-title lighter">组织机构</h5>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<div id="treeview" class="" data-id="" data-text=""></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-9 user-pane">
				<div class="widget-box widget-compact">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h5 class="widget-title lighter">查询条件</h5>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse"> <i
								class="ace-icon fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<form:form id="searchForm" modelAttribute="user"
								class="form-horizontal">
								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-2 no-padding-right"
										for="name">姓名:</label>
									<div class="col-xs-12 col-sm-3">
										<div class="clearfix input-group">
											<input type="text" id="name" class="ace width-100" />
										</div>
									</div>

									<label
										class="control-label col-xs-12 col-sm-2 no-padding-right"
										for="loginName">登录名:</label>
									<div class="col-xs-12 col-sm-3">
										<div class="clearfix input-group">
											<input type="text" id="loginName" class="ace width-100" />
										</div>
									</div>

									<div class="col-xs-12 col-sm-2 no-padding-right">
										<div class="clearfix input-group">
											<button class="btn btn-info btn-sm" type="button" id="query">
												查询</button>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
				<table id="grid-table"></table>
				<div id="grid-pager"></div>
				<div class="widget-box" style="display:none" id="editDivId"></div>
				<div id="load"  vertical-align="middle" style="z-index:9999;" >
					<span style="width:100%;position:absolute;top:40%;" align="center">
						<img src="${ctxStatic}/images/loading5.gif" width="50" height="50" align="absmiddle" style="vertical-align:middle;"/>
					</span>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctxStatic}/assets/js/shade.js"></script>
<script type="text/javascript">
	var scripts = [null,'${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js',null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		jQuery(function($) {
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
			var officeId = '';
			var officeName ='';

			var reSizeHeight = function(first){
				var strs = $.getWindowSize().toString().split(",");
				var tree_height = strs[0]-146;
				var jqgrid_height = strs[0]-337;
				if(first){
					$('#treeview').closest('.widget-main').ace_scroll({size:tree_height});
				}else{
					$('#treeview').closest('.widget-main').ace_scroll('update',{size:tree_height});
				}
				$(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};

			var jqgridData = function(param){
				$("#grid-table").jqGrid('setGridParam',{
					url:"${ctx}/sys/user/searchPage",
					mtype:"post",
					postData:param, //发送数据
					page:1
				}).trigger("reloadGrid"); //重新载入
			};

			$.getJSON( "${ctx}/sys/office/getOfficetree",{treetype:'1'},function(data) {
				officeId =data[0].id;
				officeName = data[0].text;
				$('#treeview').treeview({
					data: data,
					levels: 3,
					showBorder:true,
					selectedBackColor: "skyblue",
					emptyIcon:'fa fa-file-o red',
					collapseIcon:'fa fa-folder-open-o red',
					expandIcon:'fa fa-folder-o red',
					onNodeSelected: function(event, node) {
						$('#treeview').attr('data-id',node.id);
						$('#treeview').attr('data-text',node.text);
						officeId = node.id;
						officeName = node.text;
						var param = {'name':$("#name").val(),'loginName':$("#loginName").val(),'office.id':node.id};
						jqgridData(param);
					},
					onNodeCollapsed: function(event, node) {
						reSizeHeight();
					},
					onNodeExpanded: function(event, node) {
						reSizeHeight();
					},
				});
			});

			jQuery(grid_selector).jqGrid({
				datatype: "json", //将这里改为使用JSON数据
				url:'${ctx}/sys/user/searchPage', //这是数据的请求地址
				height: 'auto',
				autowidth:true,

				jsonReader: {
					root: "rows",   // json中代表实际模型数据的入口
					page: "page",   // json中代表当前页码的数据
					total: "total", // json中代表页码总数的数据
					records: "records", // json中代表数据行总数的数据
					repeatitems: false
				},
				prmNames : {
					page:"pageNo",    // 表示请求页码的参数名称
					rows:"rows",    // 表示请求行数的参数名称
					sort: "sidx", // 表示用于排序的列名的参数名称
					order: "sord", // 表示采用的排序方式的参数名称
					search:"_search", // 表示是否是搜索请求的参数名称
					nd:"nd", // 表示已经发送请求的次数的参数名称
					id:"id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
					oper:"oper",    // operation参数名称（我暂时还没用到）
					editoper:"edit", // 当在edit模式中提交数据时，操作的名称
					addoper:"add", // 当在add模式中提交数据时，操作的名称
					deloper:"del", // 当在delete模式中提交数据时，操作的名称
					subgridid:"id", // 当点击以载入数据到子表时，传递的数据名称
					npage: null,
					totalrows:"totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal
				},
				colNames:['姓名','登录名','角色','归属部门','手机号码','用户类型','是否允许登录','操作',''],
				colModel:[
					{name:'name',index:'name', editable: true},
					{name:'loginName',index:'login_name', editable: true},
					{name:'role1',index:'role1', editable: true},
					{name:'office.name',index:'office_id', editable: true},
					{name:'mobile',index:'mobile', editable: true},
					{name:'userType',index:'userType', editable: true},
					{name:'loginFlag',index:'loginFlag', editable: true},
					{name:'View',index:'View',width:60},
					{name:'id',index:'id', width:60,hidden:true}
				],

				viewrecords : true,
				rowNum:10,
				rowList:[10,20,30],
				pager : pager_selector,
				altRows: true,
				toppager: true,

				multiselect: true,
				//multikey: "ctrlKey",
				multiboxonly: true,
				rownumbers: true,
				loadComplete : function() {
					 $.changeGridTable.changeStyle(this);
					 $(grid_selector+"_toppager_center").remove();
					 $(grid_selector+"_toppager_right").remove();
					 $(pager_selector+"_left table").remove();
				},

				editurl: "/dummy.html",//nothing is saved
				caption: "用户列表",
				//toolbar: [true,"top"],
				gridComplete: function () {
					var ids = $(grid_selector).jqGrid('getDataIDs');
			         for (var i = 0; i < ids.length; i++) {
						var id = ids[i];
			         	var rowData = $("#grid-table").getRowData(id);

			         	var type = getDictLabel(${fns:toJson(fns:getDictList("sys_user_type"))}, rowData.userType);
			         	var flag = getDictLabel(${fns:toJson(fns:getDictList("yes_no"))}, rowData.loginFlag);
			         	var viewBtn = '<div class="action-buttons" style="white-space:normal">\
			         		<a data-action="resetPwd" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="重置密码">\
			         		<i class="ace-icon fa fa-key bigger-130"></i></a></div>';
			         	$(grid_selector).jqGrid('setRowData', ids[i], { userType:type,loginFlag:flag,View:viewBtn});
			         }
			         $(grid_selector).find('a[data-action=resetPwd]').on('click', function(event) {
							var id = $(this).attr('data-id');
							var rowData = $(grid_selector).getRowData(id);
				    		var params = {"id":id,"loginName":rowData.loginName};
							resetPwd(params);
						});
        		}

			});

			//$("#t_grid-table").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>");
			//$("input","#t_grid-table").click(function(){ alert("Hi! I'm added button at this toolbar"); });
			reSizeHeight(true);
			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);
			//search list by condition
			$("#query").click(function(){
			    var office1 = $("#treeview").attr("data-id");
		        var param ={'name':$("#name").val(),'loginName':$("#loginName").val(),'office.id':office1};
		        jqgridData(param);
		    });

			//navButtons
			jQuery(grid_selector).jqGrid('navGrid',pager_selector,
				{ 	//navbar options
					edit: true,
					editicon : 'ace-icon fa fa-pencil blue',
					editfunc : openDialogEdit,
					edittext:"编辑",
					edittitle:'',
					add: true,
					addicon : 'ace-icon fa fa-plus-circle purple',
					addfunc : openDialogAdd,
					addtext:"新增",
					addtitle:'',
					del: true,
					delicon : 'ace-icon fa fa-trash-o red',
					delfunc : doDelete,
					deltext:"删除",
					deltitle:'',
					search: false,
					searchicon : 'ace-icon fa fa-search orange',
					searchtext:"查询",
					searchtitle:'',
					refresh: false,
					refreshicon : 'ace-icon fa fa-refresh green',
					refreshtext:"刷新",
					refreshtitle:'',
					view: false,
					viewicon : 'ace-icon fa fa-search-plus grey',
					viewtext:"查看",
					viewtitle:'',
					cloneToTop:true,
				},
				{}, // use default settings for edit
				{}, // use default settings for add
				{},  // delete instead that del:false we need this
				{multipleSearch : true}, // enable the advanced searching
				{closeOnEscape:true} /* allow the view dialog to be closed when user press ESC key*/
			);

			//override dialog's title function to allow for HTML titles
			$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
				_title: function(title) {
					var $title = this.options.title || '&nbsp;';
					if( ("title_html" in this.options) && this.options.title_html == true )
						title.html($title);
					else title.text($title);
				}
			}));

		    function openDialogAdd(){
		       _edit();
		    }

			function openDialogEdit(){
				var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
			 	if(selectedIds.length>1){
				    //失败
				    $.msg_show.Init({
				        'msg':'请您选择一条记录修改',
				        'type':'error'
				    });

			 	}else{
			 		_edit(selectedIds[0]);
			 	}

			}
			function doDelete(){
				//信息确认插件
	    		$.msg_confirm.Init({
	    		    'msg': '要删除当前所选的用户吗？',//这个参数可选，默认值：'这是信息提示！'
	    		    'confirm_fn':function(){
	    		    	var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
	    		    	updateResult();
	    			 	if(selectedIds.length>1){
	    			 		var arrayObj = "";
	    					for (var i=0;i<selectedIds.length ;i++ ){
	    						var rowData = $("#grid-table").getRowData(selectedIds[i]);
	    						var id = rowData.id;
	    						arrayObj = (arrayObj + id) + (((i + 1)== selectedIds.length) ? '':',');
	    					}
	    			 		var params = {"ids":arrayObj};
	    					$.post("${ctx}/sys/user/batchDelete", params,function(result){
	    					    if(result.messageStatus=="1"){
	    						  	$.msg_show.Init({
	    						        'msg':result.message,
	    						        'type':'success'
	    						    });
	    							$(grid_selector).trigger("reloadGrid");

	    						}else if(result.messageStatus=="0"){
	    						  	$.msg_show.Init({
	    						        'msg':result.message,
	    						        'type':'error'
	    						    });
	    						}
	    					});
	    			 	}else{
	    			 		var rowData = $("#grid-table").getRowData(selectedIds[0]);
	    			 		var params = {"id":rowData.id};
	    					$.post("${ctx}/sys/user/delete", params,function(result){
	    					    if(result.messageStatus=="1"){
	    						  	$.msg_show.Init({
	    						        'msg':result.message,
	    						        'type':'success'
	    						    });
	    							$(grid_selector).trigger("reloadGrid");
	    							hideResult();
	    						}else if(result.messageStatus=="0"){
	    						  	$.msg_show.Init({
	    						        'msg':result.message,
	    						        'type':'error'
	    						    });
	    						    hideResult();
	    						}
	    					});
	    			 	}
	    		    },//这个参数可选，默认值：function(){} 空的方法体
	    		    'cancel_fn':function(){
	    		        //点击取消后要执行的操作
	    		    }//这个参数可选，默认值：function(){} 空的方法体
	    		});


			}

			function _edit(id){
				var params = {"id":id,"pageOfficeId":officeId,"pageOfficeName":officeName};
				$.post("${ctx}/sys/user/form",params, function(data,textStatus,object){
					$(".ui-dialog").remove();
					$("#editDivId").html(object.responseText).dialog({
						modal: true,
						width:800,
						height:560,
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-users'></i>&nbsp;用户维护</h4></div>",
						title_html: true,
						buttons: [
							{
								text: "保存",
								"class" : "btn btn-primary btn-minier",
								click: function() {
									$("#inputForm").bootstrapValidator('validate');
								}
							},
							{
								text: "取消",
								"class" : "btn btn-minier",
								click: function() {
									$(this).dialog("close");
								}
							}
						],
						open:function(event,ui){
							var select2 = $('.select2');
							var select2width = select2.parent().width();
							$('.select2').css('width',select2width).select2({allowClear:true});
							//选择框
							if(!ace.vars['touch']) {
								$('.chosen-select').chosen({allow_single_deselect:true});
								$(window).off('resize.chosen').on('resize.chosen', function() {
									$('.chosen-select,.select2').each(function() {
										 var $this = $(this);
										 $this.next().css({'width': $this.parent().width()});
									});
								}).trigger('resize.chosen');
							}
							$("#userDivId #loginName").off("focus.userloginName").on("focus.userloginName",function(event){
								 var bootstrapValidator = $('#inputForm').data('bootstrapValidator');
								bootstrapValidator.enableFieldValidators('loginName', true);
							});
						},
						create: function( event, ui ) {
							$("#selectOfficeMenu").on('click', function(e) {
								e.preventDefault();

								var dialog = $( "#selectOfficeTreeDiv" ).removeClass('hide').dialog({
									modal: true,
									width:300,
									height:400,
									title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-university'></i>&nbsp;选择部门</h4></div>",
									title_html: true,
									buttons: [
										{
											text: "确定",
											"class" : "btn btn-primary btn-minier",
											click: function() {
												$("#inputForm input#office\\.name").val($('#popuptreeview').attr('data-text'));
												$("#inputForm input#office\\.id").val($('#popuptreeview').attr('data-id'));
												$( this ).dialog( "close" );
											}
										},
										{
											text: "取消",
											"class" : "btn btn-minier",
											click: function() {
												$( this ).dialog( "close" );
											}
										}
									],
									open: function( event, ui ) {
										$.getJSON( "${ctx}/sys/office/getOfficetree",{"treetype":1},function(data) {
											$('#popuptreeview').treeview({
												data: data,
												levels: 3,
												showBorder:true,
												emptyIcon:'fa fa-file-o',
												collapseIcon:'fa fa-folder-open-o',
												expandIcon:'fa fa-folder-o',
												onNodeSelected: function(event, node) {
										           $('#popuptreeview').attr('data-id',node.id);
										           $('#popuptreeview').attr('data-text',node.text);
										        },
											});
										});
									}
								});
							});
						}
					});

					//字典页面维护表单验证
					$("#inputForm").bootstrapValidator({
						message : "请录入一个有效值",
						feedbackIcons : {
							valid : "glyphicon glyphicon-ok",
							invalid : "glyphicon glyphicon-remove",
							validating : "glyphicon glyphicon-refresh"
						},
						fields : {
							name : {
								validators : {
									notEmpty : {
										message : "姓名不能为空"
									}
								}
							},
							loginName : {
								enabled:false,
								validators : {
									remote : {
										message : "登录名不能重复",
										url:'${ctx}/sys/user/checkLoginNameAjax',
										data: function(validator){
											return {id : validator.getFieldElements('id').val()	};
										}
									}
								}
							},
							no : {
								validators : {
									notEmpty : {
										message : "工号不能为空"
									}
								}
							},
							email: {
				                validators: {
				                    emailAddress: {
				                        message: '邮箱地址不正确'
				                    }
				                }
				            }
						}
					}).on("success.form.bv",function(e) {
							// Prevent form submission
							e.preventDefault();

							// Get the form instance
							var $form = $(e.target);

							// Get the BootstrapValidator instance
							var bv = $form.data("bootstrapValidator");
							updateResult();
							// Use Ajax to submit form data
							$.post($form.attr("action"), $form.serialize(),function(result) {
								if(result.messageStatus=="1"){
								  	$.msg_show.Init({
								        'msg':result.message,
								        'type':'success'
								    });
									$("#editDivId").dialog("close");
									$(grid_selector).trigger("reloadGrid");
									hideResult();
								}else if(result.messageStatus=="0"){
								  	$.msg_show.Init({
								        'msg':result.message,
								        'type':'error'
								    });
								    hideResult();
								}
							}, "json");
					});
	            });

			}

			function resetPwd(params){
				//信息确认插件
				$.msg_confirm.Init({
					'msg': '确定要重置当前用户的密码吗？',//这个参数可选，默认值：'这是信息提示！'
					'confirm_fn':function(){
						updateResult();
						$.post("${ctx}/sys/user/resetPwd", params,function(result){
							if(result.messageStatus=="1"){
								$.msg_show.Init({
								'msg':result.message,
								'type':'success'
								});
								hideResult();
							}else if(result.messageStatus=="0"){
								$.msg_show.Init({
								'msg':result.message,
									'type':'error'
								});
								hideResult();
							}
						});
				    },//这个参数可选，默认值：function(){} 空的方法体
				    'cancel_fn':function(){
				        //点击取消后要执行的操作
				    }//这个参数可选，默认值：function(){} 空的方法体
				});
			}

			$(document).one('ajaxloadstart.page', function(e) {
				$.jgrid.gridUnload(grid_selector);
				$('.ui-jqdialog').remove();
				$('[class*=select2]').remove();
			});
		});
	});
	</script>
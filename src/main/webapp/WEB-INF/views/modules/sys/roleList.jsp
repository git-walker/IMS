<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctxStatic}/assets/css/shade.css" />
<title>角色管理</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
		<div class="widget-box" style="display:none" id="editDivId">

		</div>
		<div id="assignFunctionDiv" class="hide widget-body">
			<div class="widget-main padding-8">
				<div id="treeview" class="" data-id="" data-text=""></div>
			</div>
		</div>
		<div id="load"  vertical-align="middle" style="z-index:9999;" >
			<span style="width:100%;position:absolute;top:40%;" align="center">
				<img src="${ctxStatic}/images/loading5.gif" width="50" height="50" align="absmiddle" style="vertical-align:middle;"/>
			</span>
		</div>
	</div>
</div>
<script src="${ctxStatic}/assets/js/shade.js"></script>
<script type="text/javascript">
	var scripts = [ null, '${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js',null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {
		jQuery(function($) {
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
			//resize to fit page size
			var reSizeHeight = function(){
				var strs = $.getWindowSize().toString().split(",");
				var jqgrid_height = strs[0]-235;
				$(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};
			jQuery(grid_selector).jqGrid({
				datatype: "json", //将这里改为使用JSON数据
				url:'${ctx}/sys/role/searchPage', //这是数据的请求地址
				height: 'auto',
				autowidth:true,
				pgbuttons:false,//定义上一页，下一页4个如上图所示的箭头导航按钮是否显示
				pginput:false,//定义上图的“Page输入框 Of”是否显示

				jsonReader: {
					root: "rows",		// json中代表实际模型数据的入口
					page: "page",		// json中代表当前页码的数据
					total: "total",		// json中代表页码总数的数据
					records: "records",	// json中代表数据行总数的数据
					repeatitems: false
				},
				prmNames : {
					page:"pageNo",			// 表示请求页码的参数名称
					rows:"rows",			// 表示请求行数的参数名称
					sort: "sidx",			// 表示用于排序的列名的参数名称
					order: "sord",			// 表示采用的排序方式的参数名称
					search:"_search",		// 表示是否是搜索请求的参数名称
					nd:"nd",				// 表示已经发送请求的次数的参数名称
					id:"id",				// 表示当在编辑数据模块中发送数据时，使用的id的名称
					oper:"oper",			// operation参数名称（我暂时还没用到）
					editoper:"edit",		// 当在edit模式中提交数据时，操作的名称
					addoper:"add",			// 当在add模式中提交数据时，操作的名称
					deloper:"del",			// 当在delete模式中提交数据时，操作的名称
					subgridid:"id",			// 当点击以载入数据到子表时，传递的数据名称
					npage: null,
					totalrows:"totalrows"	// 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal
				},
				colNames:['角色名称','英文名称', '归属机构','数据范围','操作','值'],
				colModel:[
					{name:'name',index:'name', editable: true},
					{name:'enname',index:'enname', editable: true},
					{name:'office.name',index:'name', editable: true},
					{name:'dataScope',index:'dataScope', editable: true},
					{name:'Edit',index:'Edit', width:50},
					{name:'id',index:'id', width:60,hidden:true}
				],
				pager : pager_selector,
				altRows: true,
				rowNum:100,
				toppager: true,
				rownumbers: true,
				//multiselect: true,
				//multiboxonly: true,
				loadComplete : function() {
					$.changeGridTable.changeStyle(this);
					$(grid_selector+"_toppager_center").remove();
					$(grid_selector+"_toppager_right").remove();
					$(pager_selector+"_left table").remove();
				},
				editurl: "/dummy.html",
				caption: "角色管理",
				gridComplete: function () {
					var ids = jQuery(grid_selector).jqGrid('getDataIDs');
					for (var i = 0; i < ids.length; i++) {
						var id = ids[i];
						var rowData = $(grid_selector).getRowData(id);

						var dataScopeVal = getDictLabel(${fns:toJson(fns:getDictList('sys_data_scope'))}, rowData.dataScope);
						jQuery(grid_selector).jqGrid('setRowData', ids[i], { dataScope:dataScopeVal});
						var btn = '<div class="action-buttons" style="white-space:normal">\
							<a data-action="assign" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-info" data-rel="tooltip" title="分配功能点">\
							<i class="ace-icon fa fa-bars bigger-130"></i></a>\
							<a data-action="edit" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="修改">\
							<i class="ace-icon fa fa-pencil bigger-130"></i></a>\
							<a data-action="delete" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-error red" data-rel="tooltip" title="删除">\
							<i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
						jQuery(grid_selector).jqGrid('setRowData', ids[i], {Edit:btn});
					}
					$(grid_selector).find('a[data-action=assign]').on('click', function(event) {
						var id = $(this).attr('data-id');
						var params = {"id":id};
						assignFunction(params);
					});
					$(grid_selector).find('a[data-action=edit]').on('click', function(event) {
						var id = $(this).attr('data-id');
						_edit(id);
					});
					$(grid_selector).find('a[data-action=delete]').on('click', function(event) {
						var id = $(this).attr('data-id');
						deleteRow(id);
					});
				}
			});

			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);

			//navButtons
			jQuery(grid_selector).jqGrid('navGrid',pager_selector,
				{	//navbar options
					edit: false,
					add: true,
					addicon : 'ace-icon fa fa-plus-circle purple',
					addfunc : openDialogAdd,
					addtext:"新增",
					del: false,
					delicon : 'ace-icon fa fa-trash-o red',
					search: false,
					searchicon : 'ace-icon fa fa-search orange',
					refresh: true,
					refreshicon : 'ace-icon fa fa-refresh green',
					refreshtext:"刷新",
					refreshtitle:'',
					view: false,
					viewicon : 'ace-icon fa fa-search-plus grey',
					cloneToTop:true,
				}
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

			function assignFunction(params){
				var functions = [];
				var dialog = $( "#assignFunctionDiv" ).removeClass('hide').dialog({
					modal: true,
					width:300,
					height:600,
					title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-bars'></i>&nbsp;分配功能点</h4></div>",
					title_html: true,
					buttons: [
						{
							text: "确定",
							"class" : "btn btn-primary btn-minier",
							click: function() {
								var menus = functions.join(',');
								params.menus = menus;
								var that =$(this);
								updateResult();
								$.post("${ctx}/sys/role/saveMenus",params, function(result){
									if(result.messageStatus=="1"){
										$.msg_show.Init({
											'msg':result.message,
											'type':'success'
										});
										that.dialog( "close" );
                                        $(this).addClass('hide');
										// $( "#assignFunctionDiv" ).hide();
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
						},
						{
							text: "取消",
							"class" : "btn btn-minier",
							click: function() {
								$( this ).dialog( "close" );
                                $(this).addClass('hide');
								// $( "#assignFunctionDiv" ).hide();
							}
						}
					],
					open: function( event, ui ) {
						$.getJSON( "${ctx}/sys/role/getMenutree",params,function(data) {
							functions=data.selectList;
							functions.remove("");
							$checkableTree = $('#treeview').treeview({
								data: data.data,
								color: "#428bca",
								levels: 4,
								showBorder:true,
								showIcon: true,
								showCheckbox: true,
								onNodeChecked: function(event, node) {
									functions.push(node.id);
									/* if(node.nodes != undefined){
										$checkableTree.treeview('checkNode',[ node.nodes, { silent: true }]);
										setChildrenChecked(node,'checkNode');
									} */
								},
								onNodeUnchecked: function (event, node) {
									functions.remove(node.id);
									/* if(node.nodes != undefined){
										$checkableTree.treeview('uncheckNode',[ node.nodes, { silent: true }]);
										setChildrenChecked(node,'uncheckNode');
									} */
								}
							});
							/* function setChildrenChecked(node,status){
								$.each(node.nodes,function(i,n){
									if(n.nodes != undefined){
										$checkableTree.treeview(status,[ n.nodes, { silent: true }]);
										setChildrenChecked(n,status);
									}
								});
							} */
						});
					}
				});
			}

			function deleteRow(id){
				//信息确认插件
				$.msg_confirm.Init({
					'msg': '确认删除该角色吗？',//这个参数可选，默认值：'这是信息提示！'
					'confirm_fn':function(){
						updateResult();
						var url = "${ctx}/sys/role/doDelete?id="+id;
						$.get(url, function(result) {
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
						}, 'json');

					},//这个参数可选，默认值：function(){} 空的方法体
					'cancel_fn':function(){
					//点击取消后要执行的操作
					}//这个参数可选，默认值：function(){} 空的方法体
				});
			}

			function openDialogAdd(){
				_edit();
			}

			function _edit(id){
				var params = {"id":id};
				$.get("${ctx}/sys/role/form",params, function(data,textStatus,object){
					$(".ui-dialog").remove();
					$("#editDivId").html(object.responseText).dialog({
						modal: true,
						width:"800",
						height:"600",
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-cogs'></i>&nbsp;角色维护</h4></div>",
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
							$('.select2').select2({allowClear:true});
							/* $("#inputForm #name").off("change.name").on("change.name",function(event){
								debugger;
								var bootstrapValidator = $('#inputForm').data('bootstrapValidator');
								bootstrapValidator.enableFieldValidators('name', true);
							});
							$("#inputForm #enname").off("focus.enname").on("focus.enname",function(event){
								var bootstrapValidator = $('#inputForm').data('bootstrapValidator');
								bootstrapValidator.enableFieldValidators('enname', true);
							}); */
						},
						create: function( event, ui ) {
							$("#selectOfficeMenu").on('click', function(e) {
								e.preventDefault();

								var dialog = $( "#selectOfficeTreeDiv" ).removeClass('hide').dialog({
									modal: true,
									width:300,
									height:400,
									title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-university'></i>&nbsp;选择机构</h4></div>",
									title_html: true,
									buttons: [
										{
											text: "确定",
											"class" : "btn btn-primary btn-minier",
											click: function() {
												$("#inputForm input#office\\.name").val($('#popuptreeview').attr('data-text'));
												$("#inputForm input#office\\.id").val($('#popuptreeview').attr('data-id'));
												$( this ).dialog( "close" );
												$( "#selectOfficeTreeDiv" ).hide();
											}
										},
										{
											text: "取消",
											"class" : "btn btn-minier",
											click: function() {
												$( this ).dialog( "close" );
												$( "#selectOfficeTreeDiv" ).hide();
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
					//角色页面维护表单验证
					$("#inputForm").bootstrapValidator({
						message : "请录入一个有效值",
						feedbackIcons : {
							valid : "glyphicon glyphicon-ok",
							invalid : "glyphicon glyphicon-remove",
							validating : "glyphicon glyphicon-refresh"
						},
						fields : {
							name : {
								//enabled:false,
								validators : {
									notEmpty : {
										message : "角色名称不能为空"
									},
								}
							},
							enname : {
								//enabled:false,
								validators : {
									notEmpty : {
										message : "英文名称不能为空"
									},
								}
							}
						}
					}).on("success.form.bv",function(e) {
						// Prevent form submission
						e.preventDefault();
						// Get the form instance
						var $form = $(e.target);
						var name = $("#roleDivId #name").val();
						if(name == null || name==""){
							$.msg_show.Init({
								'msg':"角色名称不能为空",
								'type':'error'
							});
							return;
						}
						var enname = $("#roleDivId #enname").val();
						if(enname == null || enname==""){
							$.msg_show.Init({
								'msg':"英文名称不能为空",
								'type':'error'
							});
							return;
						}
						// Get the BootstrapValidator instance
						var bv = $form.data("bootstrapValidator");
						updateResult();
						// Use Ajax to submit form data
						$.post($form.attr("action"), $form.serialize(),function(result) {
							if(result.messageStatus=="1"){
								$("#editDivId").dialog("close");
								$(grid_selector).trigger("reloadGrid");
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
						}, "json");
					});
				});
			}
		});
	});
</script>

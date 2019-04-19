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
				<table id="userTable" lay-filter="userTable">
					<script type="text/html" id="topToolBar">
						<div class="layui-btn-group">
							<button lay-event="add" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-add-1"></i></button>
							<button lay-event="update" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-edit"></i></button>
							<button lay-event="delete" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-delete"></i></button>
						</div>
					</script>
					<script type="text/html" id="colToolBar">
						<div class="layui-btn-container">
							<a class="layui-btn layui-btn-xs" lay-event="resetPwd">重置密码</a>
							<a class="layui-btn layui-btn-xs" lay-event="login">禁止登录</a>
						</div>
					</script>
				</table>
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
    var scripts = ['${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js'];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        var layer ;
        var form ;
        var table ;
        var grid_selector = "#userTable";
        var officeId = '';
        var officeName ='';
        layui.use(['layer', 'form','table'], function() {
            layer = layui.layer;
            form = layui.form;
            table = layui.table;
            //渲染表格数据
            var tableObj = table.render({
                title:'用户列表',
                elem: grid_selector,
                url: '${ctx}/sys/user/searchPage',
                method:'post',
                contentType:'application/json',
                where: {
                    name: $("#name").val(),
                    loginName: $("#loginName").val()
                },
                page: true ,//开启分页
                toolbar: "#topToolBar",
                cols: [[ //表头
                    {field: 'serialNo', title: '',type:'checkbox'},
                    {field: 'name', title: '姓名'},
                    {field: 'loginName', title: '登录名', sort: true},
                    {field: 'role1', title: '角色' },
                    {field: 'office.name', title: '归属部门',sort:true,templet:function(rowData){
                            if (rowData.office.name){
                                return rowData.office.name;
                            }
                            return "";
                        }},
                    {field: 'mobile', title: '手机号码'},
                    {field: 'userType', title: '用户类型',templet:function(rowData){
                    var type = getDictLabel(${fns:toJson(fns:getDictList("sys_user_type"))}, rowData.userType);
                    if(type)return type;
                    return "";
                    }},
                    {field: 'loginFlag', title: '预先登录',templet:function(rowData){
                    var flag = getDictLabel(${fns:toJson(fns:getDictList("yes_no"))}, rowData.loginFlag);
                    return flag;
                    }},
                    {field: 'id', title: '操作',toolbar: '#colToolBar',width:160}
                ]]
            });

            //监听工具栏
            table.on('toolbar(userTable)', function(obj){
                switch(obj.event){
                    case 'add':
                        openDialogAdd();
                        break;
                    case 'delete':
                        doDelete();
                        break;
                    case 'update':
                        openDialogEdit();
                        break;
                }
            });
            //监听操作列
            table.on('tool(userTable)', function(obj){
                var rowData = obj.data;
                var layEvent = obj.event;

                if(layEvent === 'resetPwd'){
                    var params = {"id":rowData.id,"loginName":rowData.loginName};
                    resetPwd(params);
                }else if(layEvent === 'login'){
                    layer.confirm("确定禁止该用户登录吗？",function(){
                        $.ajax({
                            url:"${ctx}/sys/user/resetUserLogin",
                            dataType:"json",
                            data:{
                                id:rowData.id
                            },
                            success:function (resp) {
                                layer.msg(resp.msg);
                            },
                            complete:function () {
                                tableObj.reload();
                            }
                        })
                    })
                }
            });
            function openDialogAdd(){
                _edit();
            }

            function openDialogEdit(){
                var checkStatus = table.checkStatus("userTable");
                var selectedIds = checkStatus.data;
                if(selectedIds.length>1 || selectedIds.length<=0){
                    //失败
                    layer.msg("请您选择一条记录修改");
                }else{
                    _edit(selectedIds[0].id);
                }

            }
            function doDelete(){
                var checkStatus = table.checkStatus("userTable");
                if (checkStatus.data.length==0){
                    layer.msg("请先选择要删除的记录");
                    return;
                }
                //信息确认插件
                layer.confirm("要删除当前所选的用户吗？",function(){

                    var rowData = checkStatus.data;

                    if(rowData.length>1){
                        var arrayObj = [];
                        for (var i=0;i<rowData.length ;i++ ){
                            arrayObj.push(rowData[i].id);
                        }
                        var params = {"ids":arrayObj.join()};
                        $.post("${ctx}/sys/user/batchDelete", params,function(result){
                            if(result.messageStatus=="1"){
                                tableObj.reload();
                                layer.msg(result.message);
                            }else if(result.messageStatus=="0"){
                                layer.msg(result.message);
                            }
                        });
                    }else{
                        var rowData = checkStatus.data[0];
                        var params = {"id":rowData.id};
                        $.post("${ctx}/sys/user/delete", params,function(result){
                            if(result.messageStatus=="1"){
                                tableObj.reload();
                                layer.msg(result.message);
                            }else if(result.messageStatus=="0"){
                                layer.msg(result.message);
                            }
                        });
                    }
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
                                                }
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
                                layer.msg(result.message);
                                $("#editDivId").dialog("close");
                                tableObj.reload();
                                hideResult();
                            }else if(result.messageStatus=="0"){
                                layer.msg(result.message);
                                hideResult();
                            }
                        }, "json");
                    });
                });

            }

            function resetPwd(params){
                //信息确认插件
                layer.confirm("确定要重置当前用户的密码吗？",function(){
                    $.post("${ctx}/sys/user/resetPwd", params,function(result){
                        layer.msg(result.message);
                    });
                })
            }

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
                tableObj.reload({
                    where: param
                });
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

            reSizeHeight(true);
            $.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);
            //search list by condition
            $("#query").click(function(){
                var office1 = $("#treeview").attr("data-id");
                var param ={'name':$("#name").val(),'loginName':$("#loginName").val(),'office.id':office1};
                jqgridData(param);
            });

            //override dialog's title function to allow for HTML titles
            $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
                _title: function(title) {
                    var $title = this.options.title || '&nbsp;';
                    if( ("title_html" in this.options) && this.options.title_html == true )
                        title.html($title);
                    else title.text($title);
                }
            }));

            $(document).one('ajaxloadstart.page', function(e) {
                $.jgrid.gridUnload(grid_selector);
                $('.ui-jqdialog').remove();
                $('[class*=select2]').remove();
            });

        })

    });
</script>
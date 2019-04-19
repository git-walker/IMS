<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>供应商名录维护</title>
<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="widget-box widget-compact">
            <div class="widget-header widget-header-blue widget-header-flat">
                <h5 class="widget-title lighter">
                    查询条件
                </h5>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse"> <i
                            class="ace-icon fa fa-chevron-up"></i> </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="theme">名录名称：</label>
                            <input type="text" class="form-control" id="theme" placeholder="输入名录名称查询">
                            <label for="year">年度：</label>
                            <input type="text" class="form-control" id="year" placeholder="输入年度查询">
                        </div>

                        <input id="queryBtn" type="button" class="layui-btn layui-btn-sm" value="查询">
                        <input id="resetBtn" type="button" class="layui-btn layui-btn-sm" value="重置">
                    </form>
                </div>
            </div>
        </div>
        <div>
            <table id="mainTable" class="layui-table" lay-filter="mainTable">
                <script type="text/html" id="topToolBar">
                    <div class="layui-btn-group">
                        <button lay-event="add" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-add-1"></i></button>
                        <button lay-event="update" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-edit"></i></button>
                        <button lay-event="delete" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-delete"></i></button>
                     </div>
                </script>
                <script type="text/html" id="colToolBar">
                    <div class="layui-btn-container">
                        <a class="layui-btn layui-btn-xs" lay-event="qualified">合格名单维护</a>
                        <a class="layui-btn layui-btn-xs" lay-event="detail">查看合格名单</a>
                    </div>
                </script>
            </table>
        </div>
    </div>

</div>
<div id="addEditForm" style="display:none;"></div>
<script type="text/javascript">
    var scripts = [null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    });
    layui.use(['table','layer','form'], function(){
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        $("#queryBtn").on("click",function () {
            tableObj.reload({
                where: {
                    theme:$("#theme").val(),
                    year: $("#year").val()
                }
            });
        });
        $("#resetBtn").on("click",function () {
            $("#theme").val("");
            $("#year").val("");
            tableObj.reload({
                where: {
                    theme:"",
                    year: ""
                }
            });
        });
        //渲染表格数据
        var tableObj = table.render({
            title:'供方名录列表',
            elem: '#mainTable',
            url: '${ctx}/ims/purchase/supplierDir/supplierDirList',
            method:'post',
            contentType:'application/json',
            where: {
                supplierName: $("#supplierName").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar",
            cols: [[ //表头
                {field: 'serialNo', title: '',type:'checkbox'},
                {field: 'theme', title: '名录名称'},
                {field: 'year', title: '年度'},
                {field: 'createByName', title: '创建人'},
                {field: 'id', title: '操作',toolbar: '#colToolBar'}
            ]]
        });
        //监听工具栏
        table.on('toolbar(mainTable)', function(obj){
            switch(obj.event){
                case 'add':
                    initAdd();
                    break;
                case 'delete':
                    initDelete();
                    break;
                case 'update':
                    initUpdate();
                    break;
            }
        });
        //监听操作列
        table.on('tool(mainTable)', function(obj){
            var data = obj.data;
            var layEvent = obj.event;
            if(layEvent==='qualified'){
                initQualifiedSupplierEdit(data.id);
            }else if(layEvent === 'detail'){
                $.get("${ctx}/ims/purchase/supplierDir/initQualifiedSupplierList?dirId="+data.id,function (resp) {
                    $("#addEditForm").html(resp);
                    table.init("detailTable");
                    layer.open({
                        type: 1,
                        area: '800px',
                        title:"合格名单",
                        content: $("#addEditForm"),
                        btn:'关闭',
                        yes: function(index, layero){//关闭按钮
                            layer.close(index);
                        }
                    });

                });
            }
        });

        //合格的供方名录关系维护
        function initQualifiedSupplierEdit(dirId) {
            $.get("${ctx}/ims/purchase/supplierDir/initQualifiedSupplierEdit?dirId="+dirId,function (resp) {
                $("#addEditForm").html(resp);
                table.init("leftTable");
                table.init("rightTable");
                layer.open({
                    type: 1,
                    area: '1100px',
                    title:"合格名单",
                    content: $("#addEditForm"),
                    btn:'关闭',
                    yes: function(index, layero){//关闭按钮
                        layer.close(index);
                    }
                });
                $("#left2right").on("click",function(){
                    var checkStatus = table.checkStatus("leftTable");
                    var idArray=[];
                    for(var i=0;i<checkStatus.data.length;i++){
                        idArray.push(checkStatus.data[i].id);
                    }
                    checkQualifiedSupplier(idArray,dirId,true);
                })
                $("#right2left").on("click",function(){
                    var checkStatus = table.checkStatus("rightTable");
                    var idArray=[];
                    for(var i=0;i<checkStatus.data.length;i++){
                        idArray.push(checkStatus.data[i].id);
                    }
                    checkQualifiedSupplier(idArray,dirId,false);
                })
            });
        }

        //勾选合格供方，取消合格供方
        function checkQualifiedSupplier(idArray,supplierDirId,check,leftObj,rightObj) {
            $.ajax({
                url:"${ctx}/ims/purchase/supplierDir/checkQualifiedSupplier?check="+check,
                dataType:"json",
                type:'post',
                data:{
                    id:idArray.join(","),
                    supplierDirId:supplierDirId
                },
                success:function (resp) {
                    layer.msg(resp.msg);
                    table.reload("leftTable");
                    table.reload("rightTable");
                },
                complete:function (resp) {
                }
            })
        }

        function initAdd() {
            $.get("${ctx}/ims/purchase/supplierDir/initSupplierDirAdd",function (resp) {
                $("#addEditForm").html(resp);
                //初始化下拉框
                form.render("select");
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"添加名录",
                    content: $("#addEditForm")
                });
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(addForm)",function(){
                    $("#addForm").ajaxSubmit({
                        url:'${ctx}/ims/purchase/supplierDir/supplierDirAdd',
                        success:function(resp,textStatus,jqXHR,$form){
                            if (resp.success){
                                layer.close(index);
                                tableObj.reload();
                                layer.confirm('是否选择合格供应商？', {
                                    btn: ['是','否']
                                }, function(ind){
                                    layer.close(ind);
                                    initQualifiedSupplierEdit(resp.data);
                                });
                            }else{
                                layer.msg(resp.msg);
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                })


            })

        }

        function initDelete() {
            var checkStatus = table.checkStatus("mainTable");
            if (checkStatus.data.length==0){
                layer.msg("请先选择要删除的记录");
                return;
            }
            //向服务端发送删除指令
            layer.confirm('确定删除吗？', function(index){
                var idArray=[];
                for(var i=0;i<checkStatus.data.length;i++){
                    idArray.push(checkStatus.data[i].id);
                }
                $.ajax({
                    url:"${ctx}/ims/purchase/supplierDir/supplierDirDel",
                    dataType:"json",
                    data:{
                        id:idArray.join(",")
                    },
                    success:function (resp) {
                        layer.msg(resp.msg);
                    },
                    complete:function () {
                        layer.close(index);
                        tableObj.reload();
                    }
                })
            });
        }

        function initUpdate() {
            var checkStatus = table.checkStatus("mainTable");
            if (checkStatus.data.length!=1){
                layer.msg("请先选择1条要修改的记录");
                return;
            }
            var id=checkStatus.data[0].id;
            $.get("${ctx}/ims/purchase/supplierDir/initSupplierDirEdit?id="+id,function (resp) {
                $("#addEditForm").html(resp);
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"修改供应商",
                    content: $("#addEditForm")
                });
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(addForm)",function(){
                    $("#addForm").ajaxSubmit({
                        url:'${ctx}/ims/purchase/supplierDir/supplierDirUpdate',
                        success:function(data,textStatus,jqXHR,$form){
                            layer.msg(data.msg);
                            if (data.success){
                                layer.close(index);
                                tableObj.reload();
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                })
            });

        }


    });
</script>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>客户管理</title>
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
                            <label for="customerName">客户名称：</label>
                            <input type="text" class="form-control" id="customerName" placeholder="输入客户名称查询">
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
                        <a class="layui-btn layui-btn-xs" lay-event="detail">明细查看</a>
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
                    customerName: $("#customerName").val()
                }
            });
        });
        $("#resetBtn").on("click",function () {
            $("#customerName").val("");
            tableObj.reload({
                where: {
                    customerName: ""
                }
            });
        });
        //渲染表格数据
        var tableObj = table.render({
            title:'客户列表',
            elem: '#mainTable',
            url: '${ctx}/ims/sale/customer/customerList',
            method:'post',
            contentType:'application/json',
            where: {
                customerName: $("#customerName").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar",
            cols: [[ //表头
                {field: 'serialNo', title: '',type:'checkbox'},
                {field: 'customerName', title: '客户名称'},
                {field: 'linkMan', title: '联系人'},
                {field: 'phone', title: '联系方式'},
                {field: 'address', title: '联系地址'},
                {field: 'remarks', title: '备注'},
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
        table.on('tool(mainTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent === 'detail'){ //查看
                $.get("${ctx}/ims/sale/customer/initCustomerEdit?readonly=true&id="+data.id,function (resp) {
                    $("#addEditForm").html(resp);
                    layer.open({
                        type: 1,
                        area: '800px',
                        title:"客户信息明细",
                        content: $("#addEditForm"),
                        btn:'关闭',
                        yes: function(index, layero){//关闭按钮
                            layer.close(index);
                        }
                    });
                    form.render();
                });
            }
        });

        function initAdd() {
            $.get("${ctx}/ims/sale/customer/initCustomerAdd",function (resp) {
                $("#addEditForm").html(resp);
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"添加客户",
                    content: $("#addEditForm")
                });
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(addCustomerForm)",function(){
                    $("#addCustomerForm").ajaxSubmit({
                        url:'${ctx}/ims/sale/customer/customerAdd',
                        success:function(data,textStatus,jqXHR,$form){
                            layer.msg(data.msg);
                            if (data.success){
                                layer.close(index);
                                tableObj.reload();
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                });
            });

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
                    url:"${ctx}/ims/sale/customer/customersDelete",
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
                });
            });
        }

        function initUpdate() {
            var checkStatus = table.checkStatus("mainTable");
            if (checkStatus.data.length!=1){
                layer.msg("请先选择1条要修改的记录");
                return;
            }
            var id=checkStatus.data[0].id;
            $.get("${ctx}/ims/sale/customer/initCustomerEdit?id="+id,function (resp) {
                $("#addEditForm").html(resp);
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"修改客户信息",
                    content: $("#addEditForm")
                });
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(editCustomerForm)",function(){
                    $("#editCustomerForm").ajaxSubmit({
                        url:'${ctx}/ims/sale/customer/customerUpdate',
                        success:function(data,textStatus,jqXHR,$form){
                            layer.msg(data.msg);
                            if (data.success){
                                layer.close(index);
                                tableObj.reload();
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                });
            });

        }


    });
</script>
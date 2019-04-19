<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>采购订单</title>
<div class="row">
    <div class="col-xs-12" >
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
                <div class="widget-main no-padding">
                    <form id="query" class="layui-form">
                        <div class="form-group">
                            <label class="control-label" for="orderCode">订单编号:</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="orderCode" placeholder="输入订单编号查询">
                            </div>
                            <label class="control-label" for="repoId">仓库名称:</label>
                            <div class="layui-input-inline">
                                <select id="repoId" lay-filter="repoId" lay-search>
                                    <option value="">请选择...</option>
                                    <c:forEach items="${repoList}" var="repo">
                                        <option value="${repo.id}">${repo.repoName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="control-label" for="status">订单状态:</label>
                            <div class="layui-input-inline">
                                <select id="status" lay-filter="status" lay-search>
                                    <option value="">请选择...</option>
                                    <c:forEach items="${fns:getDictList('order_status')}" var="item">
                                        <option value="${item.value}">${item.label}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="startDate">开始日期:</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input"  id="startDate">
                            </div>
                            <label class="control-label" for="endDate">结束日期:</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input"  id="endDate">
                            </div>
                            <input id="queryBtn" type="button" class="layui-btn layui-btn-sm" value="查询">
                            <input id="resetBtn" type="button" class="layui-btn layui-btn-sm" value="重置">
                        </div>
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
                        <button lay-event="commit" class="layui-btn layui-btn-primary layui-btn-sm" title="提交审核"><i class="layui-icon layui-icon-release"></i>提交审核</button>
                    </div>
                </script>
                <script type="text/html" id="colToolBar">
                    <div class="layui-btn-container">
                        <a class="layui-btn layui-btn-xs" lay-event="detail">订单明细</a>
                    </div>
                </script>
            </table>
        </div>
    </div>
</div>
<div id="addEditForm" style="display: none;"></div>
<div id="addEditForm1" style="display: none;"></div>
<script type="text/javascript">
    var scripts = [null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    });
    layui.use(['layer', 'form','table','laydate'], function() {
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var laydate = layui.laydate;


        //常规用法
        laydate.render({
            elem: '#startDate'
            ,type: 'datetime'
        });
        //常规用法
        laydate.render({
            elem: '#endDate'
            ,type: 'datetime'
        });

        form.render();
        $("#queryBtn").on("click",function () {
            tableObj.reload({
                where: {
                    orderCode:$("#orderCode").val(),
                    repoId:$("#repoId option:selected").val(),
                    status:$("#status").val(),
                    startDate:$("#startDate").val(),
                    endDate:$("#endDate").val()
                }
            });
        });
        $("#resetBtn").on("click",function () {
           $("#query").resetForm();
            form.render("select");
            tableObj.reload({
                where: {
                    orderCode:"",
                    repoId: "",
                    status:"",
                    startDate:"",
                    endDate:""
                }
            });
        });

        //渲染表格数据
        var tableObj = table.render({
            title:'订单信息列表',
            elem: '#mainTable',
            url: '${ctx}/ims/purchase/order/purchaseOrderList',
            method:'post',
            contentType:'application/json',
            where: {
                orderCode:$("#orderCode").val(),
                repoId:$("#repoId option:selected").val(),
                status:$("#status").val(),
                startDate:$("#startDate").val(),
                endDate:$("#endDate").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar",
            cols: [[ //表头
                {field: 'serialNo', title: '',type:'checkbox'},
                {field: 'orderCode', title: '订单编号'},
                {field: 'orderType', title: '订单类型',templet:function(d){
                        var bgColor='';
                        if (d.orderType===0){//采购
                            bgColor='background-color:#5FB878;';//绿色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">采购订单</span>';
                        } else if (d.orderType===1){
                            bgColor='background-color:#FF5722;';//红色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">销售订单</span>';
                        }
                    },width:100},
                {field: 'orderRepo', title: '仓库'},
                {field: 'totalPrice', title: '订单总价/元',templet:function(d){
                    if (d.totalPrice){
                        return '<span>'+d.totalPrice+'</span>';
                    }else{
                        return '<span>订单录入中</span>';
                    }
                    },sort: true},
                {field: 'createDate', title: '创建日期',sort:true},
                {field: 'status', title: '订单状态',templet:function(d){
                        var bgColor='';
                        if (d.status===0){//录入
                            bgColor='background-color:#5FB878;';//绿色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">录入中</span>';
                        } else if (d.status===1){
                            bgColor='background-color:#FF5722;';//红色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">待审核</span>';
                        } else if (d.status===2){
                            bgColor='background-color:#2BB3D5;';//蓝色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核中</span>';
                        } else if (d.status===3){
                            bgColor='background-color:#ffbb22;'//橙色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核完成</span>';
                        }
                    },sort: true
                },
                {field: 'checkResult', title: '审核结果',templet:function(d){
                        var bgColor='';
                        if (d.checkResult==='Y'){//采购
                            bgColor='background-color:#5FB878;';//绿色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核通过</span>';
                        } else if (d.checkResult==='N'){
                            bgColor='background-color:#FF5722;';//红色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核未通过</span>';
                        }else {
                            return '<span>未提交审核</span>';
                        }
                    },width:110},
                {field:'checkManName', title:'审核人',templet:function(d){
                        if(d.checkManName){
                            return '<span>'+d.checkManName+'</span>';
                        }else{
                            return '<span>未提交审核</span>';
                        }
                    }},
                {field:'checkDate', title:'审核日期',templet:function(d){
                        if(d.checkDate){
                            return '<span>'+d.checkDate+'</span>';
                        }else{
                            return '<span>未提交审核</span>';
                        }
                    }},
                {field:'checkNote', title:'审核意见',templet:function(d){
                        if(d.checkNote){
                            return '<span>'+d.checkNote+'</span>';
                        }else{
                            return '<span>未提交审核</span>';
                        }
                    }},
                {field: 'remarks', title: '订单备注'},
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
                case 'commit':
                    initCommit();
                    break;
            }
        });
        //监听操作列
        table.on('tool(mainTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent==='detail'){//订单明细
                initOrderItemAdd(data.id);
            }
        });

        function initAdd() {
            $.get("${ctx}/ims/purchase/order/initAddOrder",function (resp) {
                $("#addEditForm").html(resp);

                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"添加采购订单",
                    content: $("#addEditForm")
                });
                form.render();
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(addOrderForm)",function(){
                    $("#addOrderForm").ajaxSubmit({
                        url:'${ctx}/ims/purchase/order/addOrder',
                        success:function(resp,textStatus,jqXHR,$form){
                            if (resp.success){
                                layer.close(index);
                                tableObj.reload();
                                layer.confirm('是否立即添加商品？', {
                                    btn: ['确定','稍后']
                                }, function(ind){
                                    layer.close(ind);
                                    initOrderItemAdd(resp.data);
                                });
                            }else{
                                layer.msg(resp.msg);
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                });
            })
        }

        function initOrderItemAdd(orderId){
            $.get("${ctx}/ims/purchase/order/orderGoodsList?orderId="+orderId,function (resp) {
                $.get("${ctx}/ims/purchase/order/getOrderStatus?orderId="+orderId,function (data){
                    if (data.status===0||data.status===1){
                        $("#addEditForm").html(resp);
                        layer.open({
                            type: 1,
                            area: ['800px','500px'],
                            title:"订单商品信息",
                            content: $("#addEditForm"),
                            btn: ['提交', '保存','关闭'] //可以无限个按钮
                            ,yes: function(index, layero){//按钮【按钮一】的回调
                                layer.confirm('确定完成录入并提交?', {
                                    btn: ['确定','稍后']
                                },function (ind) {
                                    layer.close(ind);
                                    $.get("${ctx}/ims/purchase/order/haveAnyGoods?orderId="+orderId,function (data) {//订单是否为空
                                        if (data.count===0){
                                            layer.msg("订单为空,请至少添加一件商品！");
                                        }else {
                                            completeOrder(orderId);
                                        }
                                    });
                                });
                            }
                            ,btn2: function(index, layero){//按钮【按钮二】的回调
                                $.get("${ctx}/ims/purchase/order/getOrderStatus?orderId="+orderId,function (data){
                                    if (data.status===0){
                                        layer.msg("请完成订单商品录入并尽快提交！");
                                    }
                                });
                                layer.close(index);//return false 开启该代码可禁止点击该按钮关闭
                            }
                            ,btn3: function (index, layero) {
                                layer.close(index);
                                tableObj.reload();
                            }
                        });
                    } else{
                        $("#addEditForm").html(resp);
                        layer.open({
                            type: 1,
                            area: ['800px','500px'],
                            title:"订单商品信息",
                            content: $("#addEditForm"),
                            btn: '关闭' //可以无限个按钮
                            ,yes: function(index, layero){//按钮【按钮一】的回调
                                layer.close(index);
                            }
                        });
                    }
                });
            });
        }

        //订单录入完成
        function completeOrder(orderId) {
            $.get('${ctx}/ims/purchase/order/completeOrder?orderId='+orderId,function (resp) {
                layer.msg(resp.msg);
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
                    url:"${ctx}/ims/purchase/order/delOrder",
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

        function initUpdate(){
            var checkStatus = table.checkStatus("mainTable");
            if (checkStatus.data.length!=1){
                layer.msg("请先选择1条要修改的记录");
                return;
            }
            var id=checkStatus.data[0].id;
            $.get("${ctx}/ims/purchase/order/initEditOrder?id="+id,function (resp) {
                $("#addEditForm").html(resp);
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"修改采购订单",
                    content: $("#addEditForm")
                });
                form.render();

                form.on("submit(editOrderForm)",function(){
                    $("#editOrderForm").ajaxSubmit({
                        url:'${ctx}/ims/purchase/order/updateOrder',
                        success:function(resp,textStatus,jqXHR,$form){
                            if (resp.success){
                                layer.close(index);
                                tableObj.reload();
                            }else{
                                layer.msg(resp.msg);
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                });
            });
        }

        function initCommit() {
            var checkStatus = table.checkStatus("mainTable");
            if (checkStatus.data.length==0){
                layer.msg("请先选择要提交审核的采购订单");
                return;
            }
            //向服务端发送提交审核指令
            layer.confirm('确定提交吗？', function(index){
                var idArray=[];
                for(var i=0;i<checkStatus.data.length;i++){
                    idArray.push(checkStatus.data[i].id);
                }
                $.ajax({
                    url:"${ctx}/ims/purchase/order/commitOrder",
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
    });
</script>
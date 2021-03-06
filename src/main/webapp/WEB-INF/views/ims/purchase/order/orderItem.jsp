<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>订单商品信息</title>
<table id="goodsTable" class="layui-table" lay-filter="goodsTable">
    <script type="text/html" id="topToolBar1">
        <div class="layui-btn-group">
            <button lay-event="add" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-add-1"></i></button>
            <button lay-event="update" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-edit"></i></button>
            <button lay-event="delete" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-delete"></i></button>
        </div>
    </script>
    <script type="text/html" id="coltoolBar">
        <div class="layui-btn-container">
            <a class="layui-btn layui-btn-xs" lay-event="detail">预留操作</a>
        </div>
    </script>
</table>
<<input type="hidden" id="orderId" value="${orderId}">
<div id="editForm" style="display:none;"></div>
<script type="text/javascript">
    var scripts = [null,'${ctxStatic}/assets/js/fuelux/fuelux.spinner.js',null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    });
    layui.use(['table','layer','form'], function(){
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;

        //渲染表格数据
        var tableObj1 = table.render({
            title:'订单商品信息',
            elem: '#goodsTable',
            height:360,
            url: '${ctx}/ims/purchase/order/goodsPageList',
            method:'post',
            contentType:'application/json',
            where: {
                order: $("#orderId").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar1",
            cols: [[ //表头
                {field: 'serialNo', title: '',type:'checkbox'},
                {field: 'goodsNm', title: '商品名称'},
                {field: 'supplierName', title: '供应商'},
                {field: 'goodsPrice', title: '商品进价',sort:true},
                {field: 'count', title: '数量',sort:true},
                {field: 'creater', title: '创建人'},
                {field: 'id', title: '操作',toolbar: '#coltoolBar'}
            ]]
        });
        //监听工具栏
        table.on('toolbar(goodsTable)', function(obj){
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
        table.on('tool(goodsTable)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if (layEvent === 'detail') {
                layer.msg("预留功能！");
            }
        });

        function initAdd() {
            var orderId = $("#orderId").val();
            $.get("${ctx}/ims/purchase/order/getOrderStatus?orderId="+orderId,function (data){
                if (data.status===0||data.status===1){
                    $.get("${ctx}/ims/purchase/order/initGoodsAdd", function (resp) {
                        $("#addEditForm1").html(resp);
                        form.render("select");
                        form.on('select(supplierSelect)', function(data){
                            getGoods(data.value);
                        });
                        var index=layer.open({
                            type: 1,
                            area: ['600px','300px'],
                            title:"商品添加",
                            content: $("#addEditForm1")
                        });
                        //监听表单提交事件，通过验证时才会进入回调方法
                        form.on("submit(editOrderGoodsForm)",function(){
                            var param={
                                orderId: $("#orderId").val(),
                                count:$("#count").val(),
                                supplierId:$("#supplierSelect").val(),
                                goodsId:$("#goodsSelect").val()
                            }
                            $.ajax({
                                url:"${ctx}/ims/purchase/order/addGoods",
                                dataType:"json",
                                contentType:'application/json',
                                method:'post',
                                data:JSON.stringify(param),
                                success:function (resp) {
                                    layer.msg(resp.msg);
                                },
                                complete:function () {
                                    layer.close(index);
                                    tableObj1.reload();
                                }
                            })
                            return false;//不触发form提交，否则页面跳转
                        })


                    })
                }else{
                    layer.msg("该订单不允许编辑！");
                }
            });
        }

        function initDelete() {
            var orderId = $("#orderId").val();
            $.get("${ctx}/ims/purchase/order/getOrderStatus?orderId="+orderId,function (data){
                if (data.status===0||data.status===1){
                    var checkStatus = table.checkStatus("goodsTable");
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
                            url:"${ctx}/ims/purchase/order/delGoods",
                            dataType:"json",
                            data:{
                                id:idArray.join(",")
                            },
                            success:function (resp) {
                                layer.msg(resp.msg);
                            },
                            complete:function () {
                                layer.close(index);
                                tableObj1.reload();
                            }
                        })
                    });
                } else{
                    layer.msg("该订单不允许编辑！");
                }
            });
        }

        function initUpdate() {
            var orderId = $("#orderId").val();
            $.get("${ctx}/ims/purchase/order/getOrderStatus?orderId="+orderId,function (data){
                if (data.status===0||data.status===1){
                    var checkStatus = table.checkStatus("goodsTable");
                    if (checkStatus.data.length!=1){
                        layer.msg("请先选择1条要修改的记录");
                        return;
                    }
                    var id=checkStatus.data[0].id;
                    $.get("${ctx}/ims/purchase/order/initGoodsEdit?id="+id+"", function (resp) {
                        $("#addEditForm1").html(resp);
                        form.render("select");
                        var supplierId = $("#supplierSelect").val();
                        getDefaultGoods(id,supplierId);
                        form.on('select(supplierSelect)', function(data){
                            getGoods(data.value);
                        });
                        var index=layer.open({
                            type: 1,
                            area: '600px',
                            title:"商品修改",
                            content: $("#addEditForm1")
                        });
                        //监听表单提交事件，通过验证时才会进入回调方法
                        form.on("submit(editOrderGoodsForm)",function(){
                            var param={
                                id:id,
                                orderId: $("#orderId").val(),
                                count:$("#count").val(),
                                supplierId:$("#supplierSelect").val(),
                                goodsId:$("#goodsSelect").val()
                            }
                            $.ajax({
                                url:"${ctx}/ims/purchase/order/updateGoods",
                                dataType:"json",
                                contentType:'application/json',
                                method:'post',
                                data:JSON.stringify(param),
                                success:function (resp) {
                                    layer.msg(resp.msg);
                                },
                                complete:function () {
                                    layer.close(index);
                                    tableObj1.reload();
                                }
                            })
                            return false;//不触发form提交，否则页面跳转
                        })
                    });
                } else{
                    layer.msg("该订单不允许编辑");
                }
            });
        }

        function getGoods(supplierId) {
            $.getJSON("${ctx}/ims/purchase/order/getGoods?supplierId="+supplierId, function(data){
                var optionstring = "";
                $.each(data, function(i,item){
                    optionstring += "<option value=\"" + item.id + "\" >" + item.goodsName + "</option>";
                });
                $("#goodsSelect").html('<option value="">请选择供应商后选择商品</option>' + optionstring);
                form.render('select'); //这个很重要
            });
        }

        function getDefaultGoods(id,supplierId) {
            $.getJSON("${ctx}/ims/purchase/order/getDefaultGoods?id="+id+"&supplierId="+supplierId, function(data){
                var optionstring = "";
                $.each(data, function(i,item){
                    if(item.flag){
                        optionstring += "<option value=\"" + item.id + "\" selected >" + item.goodsName + "</option>";
                    }else{
                        optionstring += "<option value=\"" + item.id + "\" >" + item.goodsName + "</option>";
                    }
                });
                $("#goodsSelect").html('<option value="">请选择供应商后选择商品</option>' + optionstring);
                form.render('select'); //这个很重要
            });
        }
    });
</script>
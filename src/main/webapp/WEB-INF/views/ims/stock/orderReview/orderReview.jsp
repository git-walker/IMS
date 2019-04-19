<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>订单审核</title>
<div class="row">
    <div class="col-xs-12 col-sm-12" >
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
                            <label for="orderType">订单类型:</label>
                            <div class="layui-input-inline">
                                <select id="orderType" lay-filter="orderType" lay-search>
                                    <option value="">请选择</option>
                                    <option value="0">采购订单</option>
                                    <option value="1">销售订单</option>
                                </select>
                            </div>
                            <label for="customerId">客户名称:</label>
                            <div class="layui-input-inline">
                                <select id="customerId" lay-filter="customerId" lay-search>
                                    <option value="">请选择......</option>
                                    <c:forEach items="${customerList}" var="customer">
                                        <option value="${customer.id}">${customer.customerName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label for="repoId">仓库名称:</label>
                            <div class="layui-input-inline">
                                <select id="repoId" lay-filter="repoId" lay-search>
                                    <option value="">请选择...</option>
                                    <c:forEach items="${repoList}" var="repo">
                                        <option value="${repo.id}">${repo.repoName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="orderCode">订单编号:</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="orderCode" placeholder="输入订单编号查询">
                            </div>
                            <label for="startDate">开始日期:</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input"  id="startDate">
                            </div>
                            <label for="endDate">结束日期:</label>
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
                <script type="text/html" id="colToolBar">
                    <div class="layui-btn-container">
                        <a class="layui-btn layui-btn-xs" lay-event="detail">明细</a>
                        <a class="layui-btn layui-btn-xs" lay-event="review">审核</a>
                        <a class="layui-btn layui-btn-xs" lay-event="reject">驳回</a>
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
            , type: 'datetime'
        });
        //常规用法
        laydate.render({
            elem: '#endDate'
            , type: 'datetime'
        });

        form.render();
        $("#queryBtn").on("click",function () {
            tableObj.reload({
                where: {
                    orderType:$("#orderType").val(),
                    orderCode:$("#orderCode").val(),
                    customerId:$("#customerId option:selected").val(),
                    repoId:$("#repoId option:selected").val(),
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
                    orderType:"",
                    orderCode:"",
                    customerId: "",
                    repoId: "",
                    startDate:"",
                    endDate:""
                }
            });
        });

        //渲染表格数据
        var tableObj = table.render({
            title:'订单信息列表',
            elem: '#mainTable',
            url: '${ctx}/ims/stockManage/orderReview/orderList',
            method:'post',
            contentType:'application/json',
            where: {
                orderType:$("#orderType").val(),
                orderCode:$("#orderCode").val(),
                customerId:$("#customerId option:selected").val(),
                repoId:$("#repoId option:selected").val(),
                startDate:$("#startDate").val(),
                endDate:$("#endDate").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar",
            cols: [[ //表头
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
                    },width: 100},
                {field: 'customer', title: '客户名称',templet:function (d) {
                        if (d.customer){
                            return '<span>'+d.customer+'</span>';
                        } else {
                            return '<span>本公司</span>';
                        }
                    }},
                {field: 'orderRepo', title: '仓库'},
                {field: 'totalPrice', title: '订单总价/元',templet:function(d){
                        if (d.totalPrice){
                            return '<span>'+d.totalPrice+'</span>';
                        }else{
                            return '<span>订单存在错误</span>';
                        }
                    },sort: true},
                {field: 'createDate', title: '创建日期',sort:true},
                {field: 'status', title: '订单状态',templet:function(d){
                        var bgColor='';
                        if (d.status===2){
                            bgColor='background-color:#2BB3D5;';//蓝色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核中</span>';
                        } else if (d.status===3){
                            bgColor='background-color:#ffbb22;'//橙色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核完成</span>';
                        }
                    },sort: true,width: 100
                },
                {field: 'checkResult', title: '审核结果',templet:function(d){
                        var bgColor='';
                        if (d.checkResult==='Y'){
                            bgColor='background-color:#5FB878;';//绿色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核通过</span>';
                        } else if (d.checkResult==='N'){
                            bgColor='background-color:#FF5722;';//红色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">审核未通过</span>';
                        }else {
                            return '<span>审核中</span>';
                        }
                    },width: 120},
                {field:'checkManName', title:'审核人',templet:function(d){
                    if(d.checkManName){
                        return '<span>'+d.checkManName+'</span>';
                    }else{
                        return '<span>审核中</span>';
                    }
                    }},
                {field:'checkDate', title:'审核日期',templet:function(d){
                        if(d.checkDate){
                            return '<span>'+d.checkDate+'</span>';
                        }else{
                            return '<span>审核中</span>';
                        }
                    }},
                {field:'checkNote', title:'审核意见',templet:function(d){
                        if(d.checkNote){
                            return '<span>'+d.checkNote+'</span>';
                        }else{
                            return '<span>审核中</span>';
                        }
                    }},
                {field: 'remarks', title: '订单备注'},
                {field: 'id', title: '操作',toolbar: '#colToolBar',width:160}
            ]]
        });

        //监听操作列
        table.on('tool(mainTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var orderId = data.id;
            if(layEvent==='detail'){//订单明细
                $.get("${ctx}/ims/stockManage/orderReview/orderGoodsList?orderId="+orderId,function (resp) {
                    $("#addEditForm").html(resp);
                    layer.open({
                        type: 1,
                        area: ['800px','500px'],
                        title:"订单明细",
                        content: $("#addEditForm"),
                        btn: '关闭' //可以无限个按钮
                        ,yes: function(index, layero){//按钮【按钮一】的回调
                            layer.close(index);
                        }
                    });
                    form.render();
                });
            }if (layEvent==='review'){
                $.get("${ctx}/ims/stockManage/orderReview/reviewForm?orderId="+orderId,function (resp) {
                    $("#addEditForm").html(resp);
                    var index = layer.open({
                        type: 1,
                        area: ['600px','300px'],
                        title:"订单审核",
                        content: $("#addEditForm")
                    });
                    form.render();
                    //监听表单提交事件，通过验证时才会进入回调方法
                    form.on("submit(reviewForm)",function(){
                        var param={
                            orderId: orderId,
                            checkResult:$("#resultSelect option:selected").val(),
                            checkNote:$("#checkNote").val()
                        }
                        $.ajax({
                            url:"${ctx}/ims/stockManage/orderReview/checkOrder",
                            dataType:"json",
                            contentType:'application/json',
                            method:'post',
                            data:JSON.stringify(param),
                            success:function (resp) {
                                layer.msg(resp.msg);
                            },
                            complete:function () {
                                layer.close(index);
                                tableObj.reload();
                            }
                        });
                        return false;//不触发form提交，否则页面跳转
                    });
                });
            }if (layEvent==='reject'){//订单驳回(审核中——>待审核)
                rejectOrder(orderId);
            }
        });

        function rejectOrder(orderId) {
            layer.confirm('确定驳回?', {
                btn: ['确定','稍后']
            },function (ind) {
                layer.close(ind);
                $.ajax("${ctx}/ims/stockManage/orderReview/rejectOrder?orderId="+orderId,function (resp){
                    layer.msg(resp.msg);
                });
                tableObj.reload();
            });
        }
    });
</script>
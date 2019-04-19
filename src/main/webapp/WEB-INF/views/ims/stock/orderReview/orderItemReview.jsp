<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>订单商品信息</title>
<table id="goodsTable" class="layui-table" lay-filter="goodsTable">
</table>
<<input type="hidden" id="orderId" value="${orderId}">
<script type="text/javascript">
    var scripts = [null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    });
    layui.use(['table'], function(){
        var table = layui.table;

        //渲染表格数据
        table.render({
            title:'订单商品信息',
            elem: '#goodsTable',
            height:360,
            url: '${ctx}/ims/stockManage/orderReview/goodsPageList',
            method:'post',
            contentType:'application/json',
            where: {
                order: $("#orderId").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar1",
            cols: [[ //表头
                {field: 'goodsNm', title: '商品名称'},
                {field: 'goodsPrice', title: '价格',sort:true},
                {field: 'count', title: '数量',sort:true},
                {field: 'creater', title: '创建人'}
            ]]
        });
    });
</script>

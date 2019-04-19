<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="editSaleOrderGoodsForm" id="editSaleOrderGoodsForm" method="post">
        <div class="layui-form-item">
            <label class="layui-form-label">商品名称</label>
            <div class="layui-input-block">
                <select id="goodsSelect" lay-filter="goodsSelect" name="goodsId" lay-verify="required" style="width:200px;" lay-search>
                    <option value="">请选择</option>
                    <c:forEach items="${goodsList}" var="item">
                        <option value="${item.id}"  <c:if test="${o.goodsId eq item.id}">selected</c:if> >${item.goodsName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">数量</label>
            <div class="layui-input-block">
            <input type="text" id="count" name="count" lay-verify="required" autocomplete="off" placeholder="请输入数量">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    jQuery(function ($) {
        $('#count').ace_spinner({
            value: 1,
            min: 1,
            max: 999,
            step: 1,
            on_sides: true,
            icon_up: 'ace-icon fa fa-plus bigger-110',
            icon_down: 'ace-icon fa fa-minus bigger-110',
            btn_up_class: 'btn-success',
            btn_down_class: 'btn-danger'
        });
    });
</script>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="editCustomerForm" id="editCustomerForm" method="post">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">客户名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="customerName" value="${customer.customerName}" lay-verify="required" autocomplete="off" placeholder="请输入客户名称" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">联系人</label>
                <div class="layui-input-inline">
                    <input type="text" name="linkMan" value="${customer.linkMan}" autocomplete="off" placeholder="请输入联系人" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">联系方式</label>
                <div class="layui-input-inline">
                    <input type="text" name="phone" value="${customer.phone}" autocomplete="off" placeholder="请输入联系方式" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">联系地址</label>
                <div class="layui-input-inline">
                    <input type="text" name="address" value="${customer.address}" autocomplete="off" placeholder="请输入联系地址" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea name="remarks" placeholder="请输入内容" class="layui-textarea">${customer.remarks}</textarea>
            </div>
        </div>
        <c:if test="${!readonly}">
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </c:if>
        <input type="hidden" name="id" value="${customer.id}">
    </form>
</div>


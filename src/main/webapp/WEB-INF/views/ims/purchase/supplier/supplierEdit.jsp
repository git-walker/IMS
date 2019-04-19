<%@ taglib prefix="fnt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="editSupplierForm" id="editSupplierForm" method="post">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">供应商全称</label>
                <div class="layui-input-inline">
                    <input type="text" value="${supplier.fullName}" name="fullName" lay-verify="required" placeholder="请输入供应商全称" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">供应商简称</label>
                <div class="layui-input-inline">
                    <input type="text" value="${supplier.briefName}"  name="briefName" placeholder="请输入供应商简称" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">联系人</label>
                <div class="layui-input-inline">
                    <input type="text" value="${supplier.dutyMan}" name="dutyMan" autocomplete="off" placeholder="请输入联系人" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">联系方式</label>
                <div class="layui-input-inline">
                    <input type="text" value="${supplier.phone}" name="phone" autocomplete="off" placeholder="请输入联系方式" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">联系地址</label>
                <div class="layui-input-inline">
                    <input type="text" value="${supplier.address}" name="address" autocomplete="off" placeholder="请输入联系地址" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">供方描述</label>
            <div class="layui-input-block">
                <textarea style="min-height: auto;" name="supplierDesc" placeholder="请输入供方描述" class="layui-textarea">${supplier.supplierDesc}</textarea>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea name="remarks" placeholder="请输入内容" class="layui-textarea">${supplier.remarks}</textarea>
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
        <input type="hidden" name="id" value="${supplier.id}">

    </form>
</div>


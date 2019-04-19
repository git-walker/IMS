<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="editOrderForm" id="editOrderForm" method="post">
        <div class="layui-form-item" >
            <label class="layui-form-label" >订单类型</label>
            <div class="layui-input-block">
                <input type="radio" name="orderType" value="0" title="采购订单" checked="" disabled>
                <input type="radio" name="orderType" value="1"  title="销售订单" disabled>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">订单编号</label>
                <div class="layui-input-inline">
                    <input type="text" value="${o.orderCode}" name="orderCode" lay-verify="required" autocomplete="off" placeholder="请输入订单编号" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">仓库名称</label>
                <div class="layui-input-inline">
                    <select name="orderRepoId" lay-verify="required" lay-filter="orderRepoId" lay-search>
                        <c:forEach items="${repoList}" var="repo">
                            <option value="${repo.id}" <c:if test="${o.orderRepoId eq repo.id}">selected</c:if> >${repo.repoName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea name="remarks" placeholder="请输入内容" class="layui-textarea">${o.remarks}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
        <input type="hidden" name="id" value="${o.id}">
    </form>
</div>
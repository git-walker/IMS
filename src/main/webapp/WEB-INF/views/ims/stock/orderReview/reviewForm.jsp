<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="reviewForm" id="reviewForm" method="post">
        <div class="layui-form-item">
            <label class="layui-form-label">是否通过</label>
            <div class="layui-input-block">
                <select id="resultSelect" name="checkResult" lay-verify="required" lay-search>
                    <option value="">请选择</option>
                    <option value="Y">审核通过</option>
                    <option value="N">审核不通过</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">审核结果</label>
            <div class="layui-input-block">
                <textarea id="checkNote" name="checkNote" placeholder="请输入审核意见" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
            </div>
        </div>
    </form>
</div>
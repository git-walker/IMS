<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="addForm" id="addForm" method="post">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">名录名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="theme" lay-verify="required" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">所属年度</label>
                <div class="layui-input-inline">
                    <select name="year" lay-verify="required" style="width:200px;">
                        <c:forEach items="${yearList}" var="item">
                            <option value="${item.year}">${item.year}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>

    </form>
</div>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="addRepoForm" id="addRepoForm" method="post">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">仓库名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="repoName" name="repoName" lay-verify="required" autocomplete="off" placeholder="请输入仓库名称" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">仓库编号</label>
                <div class="layui-input-inline">
                    <input type="text" id="repoCode" name="repoCode" lay-verify="required" autocomplete="off" placeholder="请输入仓库编号" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">仓库地址</label>
                <div class="layui-input-inline">
                    <input type="text" id="repoAddress" name="repoAddress" lay-verify="required" autocomplete="off" placeholder="请输入仓库地址" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">仓库管理员</label>
                <div class="layui-input-inline">
                    <select id="keeperId" name="keeperId" lay-verify="required" lay-filter="keeperId" lay-search>
                        <option value="">请选择</option>
                        <c:forEach items="${userList}" var="item">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item" >
            <label class="layui-form-label" >仓库状态</label>
            <div class="layui-input-block">
                <input type="radio" name="status" value="0" title="可用" checked="">
                <input type="radio" name="status" value="1"  title="不可用">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea id="remarks" name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
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

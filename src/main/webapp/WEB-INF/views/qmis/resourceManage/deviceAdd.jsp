<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div style="padding:5px;">
    <form class="layui-form  layui-form-pane" action="" lay-filter="addDeviceForm" id="addDeviceForm">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">设备名称</label>
                <div class="layui-input-block">
                    <input type="text" id="deviceName" name="deviceName" lay-verify="required" autocomplete="off" placeholder="请输入设备名称" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">设备编码</label>
                <div class="layui-input-block">
                    <input type="text" id="deviceCode" name="deviceCode" lay-verify="required" autocomplete="off" placeholder="请输入设备编码" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">所属车间</label>
                <div class="layui-input-block">
                    <select id="workShop" lay-verify="required" lay-search>
                        <option value="">请选择一个车间</option>
                        <option value="1e21b9e9aaff4a488583ae786b65cbbc">总装车间</option>
                        <option value="">钢结构车间</option>
                        <option value="">台车车间</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">设备类型</label>
                <div class="layui-input-block">
                    <select id="deviceType" lay-verify="required" lay-search>
                        <option value="">请选择设备类型</option>
                        <option value="2de7aac566174d06ac6d14d6532a2bb2">工装车类型</option>
                        <option value="">大铁路车种</option>
                        <option value="">动车组车种</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">技术参数</label>
                <div class="layui-input-inline">
                    <input type="text" id="techParam" name="techParam" lay-verify="required" autocomplete="off" placeholder="请输入设备技术参数" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">使用期限</label>
                <div class="layui-input-inline">
                    <input type="text" id="expireDate" name="expireDate" lay-verify="required" autocomplete="off" placeholder="请输入设备使用期限" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">设备管理员</label>
            <div class="layui-input-block">
                <select id="deviceAdmin" lay-verify="required" lay-search>
                    <option value="">请选择设备管理员</option>
                    <option value="1006A0100000000LYAGC">潘玲</option>
                    <option value="">潘丽</option>
                </select>
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
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>

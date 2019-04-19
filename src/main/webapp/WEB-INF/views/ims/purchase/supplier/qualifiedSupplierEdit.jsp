<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="addForm" id="addForm" method="post" onsubmit="return false" >
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">名录名称</label>
                <div class="layui-input-inline">
                    <div class="layui-form-mid layui-word-aux">${dir.theme}</div>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">所属年度</label>
                <div class="layui-input-inline">
                    <div class="layui-form-mid layui-word-aux">${dir.year}</div>
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">合格供方</label>
            <div class="layui-input-block">
                <div class="layui-col-md5">
                    <table id="leftTable" lay-filter="leftTable" lay-data="{height:315, url:'${ctx}/ims/purchase/supplierDir/unCheckedSupplierPageList?dirId=${dir.id}', page:true}">
                        <thead>
                        <tr>
                            <th lay-data="{field:'sn',title:'',type:'checkbox'}">选择</th>
                            <th lay-data="{field:'supplierName'}">供应商名称</th>
                            <th lay-data="{field:'id',hide:true}">主键</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="layui-col-md2" style="text-align: center;padding-top:100px;">
                    <div style="margin-bottom: 15px;">
                        <a id="left2right" class="layui-btn layui-btn-sm"><i class="layui-icon layui-icon-next"></i></a>
                    </div>
                    <div>
                        <a id="right2left" class="layui-btn layui-btn-sm"><i class="layui-icon layui-icon-prev"></i></a>
                    </div>
                </div>
                <div class="layui-col-md5">
                    <table id="rightTable" lay-filter="rightTable" lay-data="{height:315, url:'${ctx}/ims/purchase/supplierDir/supplierDirDetailPageList?dirId=${dir.id}', page:true}">
                        <thead>
                        <tr>
                            <th lay-data="{field:'sn',title:'',type:'checkbox'}">选择</th>
                            <th lay-data="{field:'supplierName'}">供应商名称</th>
                            <th lay-data="{field:'id',hide:true}">主键</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>

    </form>
</div>

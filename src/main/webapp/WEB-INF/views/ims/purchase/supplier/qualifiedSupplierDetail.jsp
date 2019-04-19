<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div style="padding:5px;">
    <div>
        <blockquote class="layui-elem-quote">${dir.theme}</blockquote>
        <table id="detailTable" lay-filter="detailTable" lay-data="{height:300,size:'sm', url:'${ctx}/ims/purchase/supplierDir/supplierDirDetailPageList?dirId=${dir.id}', page:true}">
            <thead>
                <tr>
                    <th lay-data="{field:'supplierName',sort:true}">供应商名称</th>
                    <th lay-data="{field:'createByName',width:100,sort:true}">批准人</th>
                    <th lay-data="{field:'createDate',width:170,sort:true}">批准时间</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

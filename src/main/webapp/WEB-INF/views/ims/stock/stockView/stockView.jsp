<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>库存查看</title>
<div class="row">
    <div class="col-xs-12 col-sm-12" >
        <div class="widget-box widget-compact">
            <div class="widget-header widget-header-blue widget-header-flat">
                <h5 class="widget-title lighter">
                    查询条件
                </h5>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse"> <i
                            class="ace-icon fa fa-chevron-up"></i> </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form id="query" class="layui-form form-inline">
                        <div class="form-group">
                            <label for="goodsName">商品名称：</label>
                            <input type="text" class="form-control" id="goodsName" placeholder="输入商品名称查询">
                            <label for="repoId">仓库名称:</label>
                            <div class="layui-input-inline">
                                <select id="repoId" lay-filter="repoId" lay-search>
                                    <option value="">请选择...</option>
                                    <c:forEach items="${repoList}" var="repo">
                                        <option value="${repo.id}">${repo.repoName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <input id="queryBtn" type="button" class="layui-btn layui-btn-sm" value="查询">
                        <input id="resetBtn" type="button" class="layui-btn layui-btn-sm" value="重置">
                    </form>
                </div>
            </div>
        </div>
        <div>
            <table id="mainTable" class="layui-table" lay-filter="mainTable">
                <script type="text/html" id="coltoolBar">
                    <div class="layui-btn-container">
                        <a class="layui-btn layui-btn-xs" lay-event="detail">预留操作</a>
                    </div>
                </script>
            </table>
        </div>
    </div>
</div>
<div id="addEditForm" style="display: none;"></div>
<script type="text/javascript">
    var scripts = [null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    });
    layui.use(['layer', 'form','table'], function() {
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;

        form.render();
        $("#queryBtn").on("click",function () {
            tableObj.reload({
                where: {
                    repoId:$("#repoId option:selected").val(),
                    goodsName:$("#goodsName").val()
                }
            });
        });
        $("#resetBtn").on("click",function () {
            $("#query").resetForm();
            form.render("select");
            tableObj.reload({
                where: {
                    repoId: "",
                    goodsName:""
                }
            });
        });

        //渲染表格数据
        var tableObj = table.render({
            title:'库存信息列表',
            elem: '#mainTable',
            url: '${ctx}/ims/stockManage/stockView/stockList',
            method:'post',
            contentType:'application/json',
            where: {
                repoId:$("#repoId option:selected").val(),
                goodsName:$("#goodsName").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar",
            cols: [[ //表头
                {field: 'repoName', title: '仓库'},
                {field: 'goodsName', title: '商品'},
                {field: 'count', title: '库存余量'},
                {field: 'id', title: '操作',toolbar: '#coltoolBar'}
            ]]
        });
        //监听操作列
        table.on('tool(mainTable)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if (layEvent === 'detail') {
                layer.msg("预留功能！");
            }
        });
    });
</script>
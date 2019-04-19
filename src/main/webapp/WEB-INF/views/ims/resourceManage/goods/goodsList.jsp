<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>商品维护</title>
<div class="row">
    <div class="col-xs-12 col-sm-12">
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
                    <form class="layui-form form-inline">
                        <div class="form-group">
                            <label for="goodsName">商品名称：</label>
                            <input type="text" class="form-control" id="goodsName" placeholder="输入商品名称查询">
                            <label for="goodsCode">商品编码：</label>
                            <input type="text" class="form-control" id="goodsCode" placeholder="输入商品编码查询">
                            <label for="goodsBrand">商品品牌：</label>
                            <div class="layui-input-inline">
                                <select id="goodsBrand" name="goodsBrand" lay-search>
                                    <option value="">请选择......</option>
                                    <c:forEach items="${supplierList}" var="supplier">
                                        <option value="${supplier.id}">${supplier.fullName}</option>
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
                <script type="text/html" id="topToolBar">
                    <div class="layui-btn-group">
                        <button lay-event="add" class="layui-btn layui-btn-primary layui-btn-sm" title="新增"><i class="layui-icon layui-icon-add-1"></i></button>
                        <button lay-event="update" class="layui-btn layui-btn-primary layui-btn-sm" title="修改"><i class="layui-icon layui-icon-edit"></i></button>
                        <button lay-event="delete" class="layui-btn layui-btn-primary layui-btn-sm" title="删除"><i class="layui-icon layui-icon-delete"></i></button>
                    </div>
                </script>
                <script type="text/html" id="colToolBar">
                    <div class="layui-btn-container">
                        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
                        <a class="layui-btn layui-btn-xs" lay-event="qrCode">查看二维码</a>
                    </div>
                </script>
            </table>
        </div>
    </div>
    <div id="editForm" style="display:none;"></div>
</div>
<script type="text/javascript">
    var scripts = [null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    });
    layui.config({
        base: '${ctxStatic}/layui-extend/'
    });
    layui.use(['layer', 'form','table','laydate','formhelp'], function() {
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var laydate = layui.laydate;
        var formhelp = layui.formhelp;

        form.render();
        $("#queryBtn").on("click",function () {
            tableObj.reload({
                where: {
                    goodsName: $("#goodsName").val(),
                    goodsCode: $("#goodsCode").val(),
                    goodsBrand: $("#goodsBrand option:selected").val()
                }
            });
        });
        $("#resetBtn").on("click",function () {
            $("#goodsName").val("");
            $("#goodsCode").val("");
            $("#goodsBrand").find("option:selected").attr("selected", false);
            $("#goodsBrand").find("option").first().attr('selected',true);
            form.render("select");
            tableObj.reload({
                where: {
                    goodsName: "",
                    goodsCode: "",
                    goodsBrand: ""
                }
            });
        });

        //渲染表格数据
        var tableObj = table.render({
            title:'商品信息列表',
            elem: '#mainTable',
            url: '${ctx}/ims/resourceManage/goods/goodsList',
            method:'post',
            contentType:'application/json',
            where: {
                goodsName: $("#goodsName").val(),
                goodsCode: $("#goodsCode").val(),
                goodsBrand: $("#goodsBrand").val()
            },
            page: true ,//开启分页
            toolbar: "#topToolBar",
            cols: [[ //表头
                {field: 'serialNo', title: '',type:'checkbox'},
                {field: 'goodsName', title: '商品名称',sort:true},
                {field: 'goodsCode', title: '商品编码'},
                {field: 'goodsType', title: '商品类型'},
                {field: 'brandName', title: '品牌',sort:true},
                {field: 'specification', title: '规格型号'},
                {field: 'buyPrice', title: '预计进价/元',sort:true},
                {field: 'salePrice', title: '预计售价/元',sort:true},
                {field: 'status', title: '商品状态',templet:function(d){
                        var bgColor='';
                        if (d.status===0){//在售
                            bgColor='background-color:#5FB878;';//绿色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">在售</span>';
                        } else if (d.status===1){
                            bgColor='background-color:#FF5722;';//红色
                            return '<span style="border-radius:2px;padding:2px 4px;'+bgColor+'color:#FFFFFF;">停售</span>';
                        }
                     }
                },
                {field: 'picture', title: '商品图片'},
                {field: 'remarks', title: '备注'},
                {field: 'id', title: '操作',toolbar: '#colToolBar',width:140}
            ]]
        });
        //监听工具栏
        table.on('toolbar(mainTable)', function(obj){
            switch(obj.event){
                case 'add':
                    initAdd();
                    break;
                case 'delete':
                    initDelete();
                    break;
                case 'update':
                    initUpdate();
                    break;
            }
        });
        //监听操作列
        table.on('tool(mainTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent === 'detail'){ //查看
                $.get("${ctx}/ims/resourceManage/goods/initEditGoods?readonly=true&id="+data.id,function (resp) {
                    $("#editForm").html(resp);
                    layer.open({
                        type: 1,
                        area: '800px',
                        title:"商品信息明细",
                        content: $("#editForm"),
                        btn:'关闭',
                        yes: function(index, layero){//关闭按钮
                            layer.close(index);
                        }
                    });
                    form.render();
                });
            }
            if(layEvent === 'qrCode'){ //查看
                $.get("${ctx}/ims/resourceManage/goods/qrCodeForm?id="+data.id,function (resp) {
                    $("#editForm").html(resp);
                    layer.open({
                        type: 1,
                        area: ['800px','450px'],
                        title:"商品二维码查看",
                        content: $("#editForm"),
                        btn:['导出','关闭',],
                        yes: function(index, layero){

                        },
                        btn2: function(index, layero){//关闭按钮
                            layer.close(index);
                        }
                    });
                });
            }
        });

        function initAdd() {
            $.get("${ctx}/ims/resourceManage/goods/initAddGoods",function (resp) {
                $("#editForm").html(resp);

                formhelp.render({
                    elem:"#imgAttach",
                    fieldName:'imgAttach',
                    showFileDesc:true
                });
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"添加商品信息",
                    content: $("#editForm")
                });
                form.render();
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(addGoodsForm)",function(){
                    $("#addGoodsForm").ajaxSubmit({
                        url:'${ctx}/ims/resourceManage/goods/addGoods',
                        success:function(resp,textStatus,jqXHR,$form){
                            if (resp.success){
                                layer.close(index);
                                tableObj.reload();
                            }else{
                                layer.msg(resp.msg);
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                });
            })
        }

        function initDelete() {
            var checkStatus = table.checkStatus("mainTable");
            if (checkStatus.data.length==0){
                layer.msg("请先选择要删除的记录");
                return;
            }
            //向服务端发送删除指令
            layer.confirm('确定删除吗？', function(index){
                var idArray=[];
                for(var i=0;i<checkStatus.data.length;i++){
                    idArray.push(checkStatus.data[i].id);
                }
                $.ajax({
                    url:"${ctx}/ims/resourceManage/goods/delGoods",
                    dataType:"json",
                    data:{
                        id:idArray.join(",")
                    },
                    success:function (resp) {
                        layer.msg(resp.msg);
                    },
                    complete:function () {
                        layer.close(index);
                        tableObj.reload();
                    }
                })
            });
        }

        function initUpdate(){
            var checkStatus = table.checkStatus("mainTable");
            if (checkStatus.data.length!=1){
                layer.msg("请先选择1条要修改的记录");
                return;
            }
            var id=checkStatus.data[0].id;
            $.get("${ctx}/ims/resourceManage/goods/initEditGoods?id="+id,function (resp) {
                $("#editForm").html(resp);
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"修改商品信息",
                    content: $("#editForm")
                });
                form.render();

                form.on("submit(editGoodsForm)",function(){
                    $("#editGoodsForm").ajaxSubmit({
                        url:'${ctx}/ims/resourceManage/goods/updateGoods',
                        success:function(resp,textStatus,jqXHR,$form){
                            if (resp.success){
                                layer.close(index);
                                tableObj.reload();
                            }else{
                                layer.msg(resp.msg);
                            }
                        }
                    });
                    return false;//不触发form提交，否则页面跳转
                });
            });
        }
    });
</script>
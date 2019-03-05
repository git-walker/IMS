<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>四日计划</title>
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
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="planName">计划名称：</label>
                            <input type="text" class="form-control" id="planName" placeholder="输入计划名称查询">
                        </div>

                        <input id="queryBtn" type="button" class="layui-btn layui-btn-sm" value="查询">
                        <input id="resetBtn" type="button" class="layui-btn layui-btn-sm" value="重置">
                    </form>
                </div>
            </div>
        </div>
        <div>
            <table id="planTable" class="layui-table" lay-filter="planTable">
                <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
                    </div>
                </script>
            </table>
        </div>
    </div>
    <div id="addEditForm" style="display:none;">
        <form class="form-inline">
            <div class="form-group">
                <label for="planName">计划名称：</label>
                <input type="text" class="form-control" id="planName2">
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var scripts = [null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    });
    layui.use(['layer', 'form','table'], function(){
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;

        $("#queryBtn").on("click",function () {
            tableObj.reload({
                where: {
                    planName: $("#planName").val()
                }
            });
        });
        $("#resetBtn").on("click",function () {
            $("#planName").val("");
            tableObj.reload({
                where: {
                    planName: ""
                }
            });
        });
        //渲染表格数据
        var tableObj = table.render({
            title:'四日计划列表',
            elem: '#planTable',
            url: '${ctx}/qmis/4dayplan/planList',
            method:'post',
            contentType:'application/json',
            where: {
                planName: $("#planName").val()
            },
            page: true ,//开启分页
            toolbar: "default",
            cols: [[ //表头
                {field: 'id', title: 'ID',hide:true},
                {field: 'serialNo', title: '',type:'checkbox'},
                {field: 'planName', title: '计划名称'},
                {field: 'createByName', title: '上传人', sort: true},
                {field: 'createDate', title: '上传时间' },
                {field: 'consistency', title: '计划执行重合度',sort:true},
                {field: 'remarks', title: '备注'},
                {field: 'id', title: '操作',toolbar: '#toolbarDemo'}
            ]]
        });
        //监听工具栏
        table.on('toolbar(planTable)', function(obj){
            switch(obj.event){
                case 'add':
                    $("#planName2").val("");
                    layer.open({
                        type: 1,
                        width: '300px',
                        title:"添加四日计划",
                        content: $("#addEditForm"),
                        btn:['保存'],
                        yes: function(index, layero){
                            var param={
                                planName:$("#planName2").val()
                            }
                            $.ajax({
                                url:"${ctx}/qmis/4dayplan/addPlan",
                                dataType:"json",
                                contentType:'application/json',
                                method:'post',
                                data:JSON.stringify(param),
                                success:function (resp) {
                                    layer.msg(resp.msg);
                                },
                                complete:function () {
                                    layer.close(index);
                                    tableObj.reload();
                                }
                            })
                        }
                    });
                    break;
                case 'delete':
                    var checkStatus = table.checkStatus(obj.config.id);
                    if (checkStatus.data.length==0){
                        layer.msg("请先选择要删除的记录");
                        layer.close(index);
                        return;
                    }
                    //向服务端发送删除指令
                    layer.confirm('确定删除吗？', function(index){
                        var idArray=[];
                        for(var i=0;i<checkStatus.data.length;i++){
                            idArray.push(checkStatus.data[i].id);
                        }
                        $.ajax({
                            url:"${ctx}/qmis/4dayplan/delPlans",
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
                    break;
                case 'update':
                    var checkStatus = table.checkStatus(obj.config.id);
                    if (checkStatus.data.length!=1){
                        layer.msg("请先选择1条要修改的记录");
                        layer.close(index);
                        return;
                    }
                    $("#planName2").val(checkStatus.data[0].planName);
                    layer.open({
                        type: 1,
                        width: '300px',
                        title:"修改四日计划",
                        content: $("#addEditForm"),
                        btn:['保存'],
                        yes: function(index, layero){
                            var param={
                                id:checkStatus.data[0].id,
                                planName:$("#planName2").val()
                            }
                            $.ajax({
                                url:"${ctx}/qmis/4dayplan/updatePlan",
                                dataType:"json",
                                contentType:'application/json',
                                method:'post',
                                data:JSON.stringify(param),
                                success:function (resp) {
                                    layer.msg(resp.msg);
                                },
                                complete:function () {
                                    layer.close(index);
                                    tableObj.reload();
                                }
                            })
                        }
                    });
                    break;
            }
        });
        //监听操作列
        table.on('tool(planTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent === 'detail'){ //查看
                $("#planName2").val(data.planName);
                layer.open({
                    type: 1,
                    area: ['300px', '180px'],
                    title:"四日计划明细",
                    content: $("#addEditForm"),
                    btn:['确定']
                });
            }
        });


    });
</script>
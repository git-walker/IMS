<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>设备维护</title>
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
                            <label for="deviceName">设备名称：</label>
                            <input type="text" class="form-control" id="deviceName" placeholder="输入设备名称查询">
                            <label for="deviceCode">设备编码：</label>
                            <input type="text" class="form-control" id="deviceCode" placeholder="输入设备编码查询">
                            <label for="workShop">所属车间：</label>
                            <input type="text" class="form-control" id="workShop" placeholder="输入设备所属车间查询">
                        </div>

                        <input id="queryBtn" type="button" class="layui-btn layui-btn-sm" value="查询">
                        <input id="resetBtn" type="button" class="layui-btn layui-btn-sm" value="重置">
                    </form>
                </div>
            </div>
        </div>
        <div>
            <table id="deviceTable" class="layui-table" lay-filter="deviceTable">
                <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
                        <a class="layui-btn layui-btn-xs" lay-event="qrCodePrint">生成二维码</a>
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
    layui.use(['layer', 'form','table','laydate'], function(){
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var laydate = layui.laydate;

        $("#queryBtn").on("click",function () {
            tableObj.reload({
                where: {
                    deviceName: $("#deviceName").val(),
                    deviceCode: $("#deviceCode").val(),
                    workShop: $("#workShop").val()
                }
            });
        });
        $("#resetBtn").on("click",function () {
            $("#deviceName").val("");
            $("#deviceCode").val("");
            $("#workShop").val("");
            tableObj.reload({
                where: {
                    deviceName: "",
                    deviceCode: "",
                    workShop: ""
                }
            });
        });
        //渲染表格数据
        var tableObj = table.render({
            title:'设备信息列表',
            elem: '#deviceTable',
            url: '${ctx}/qmis/resourceManage/device/deviceList',
            method:'post',
            contentType:'application/json',
            where: {
                deviceName: $("#deviceName").val(),
                deviceCode: $("#deviceCode").val(),
                workShop: $("#workShop").val()
            },
            page: true ,//开启分页
            toolbar: "default",
            cols: [[ //表头
                {field: 'id', title: 'ID',hide:true},
                {field: 'serialNo', title: '',type:'checkbox'},
                {field: 'deviceName', title: '设备名称'},
                {field: 'deviceCode', title: '设备编码'},
                {field: 'deviceType', title: '设备类型'},
                {field: 'workShop', title: '所属车间'},
                {field: 'expireDate', title: '使用期限',sort:true},
                {field: 'remarks', title: '备注'},
                {field: 'id', title: '操作',toolbar: '#toolbarDemo'}
            ]]
        });
        //监听工具栏
        table.on('toolbar(deviceTable)', function(obj){
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
        table.on('tool(deviceTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent === 'detail'){ //查看
                $.get("${ctx}/qmis/resourceManage/device/initEditDevice?readonly=true&id="+data.id,function (resp) {
                    $("#editForm").html(resp);
                    layer.open({
                        type: 1,
                        area: '800px',
                        title:"设备信息明细",
                        content: $("#editForm"),
                        btn:'关闭',
                        yes: function(index, layero){//关闭按钮
                            layer.close(index);
                        }
                    });
                    form.render();
                });
            }else if(layEvent === 'qrCodePrint'){
                //生成二维码
            }
        });

        function initAdd() {
            $.get("${ctx}/qmis/resourceManage/device/initAddDevice",function (resp) {
                $("#editForm").html(resp);
                //初始化日历
                laydate.render({
                    elem: '#expireDate',
                    type: 'datetime'
                });
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"添加设备信息",
                    content: $("#editForm")
                });
                form.render();
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(addDeviceForm)",function(){
                    var param={
                        deviceName:$("#addDeviceForm #deviceName").val(),
                        deviceCode:$("#addDeviceForm #deviceCode").val(),
                        workShop:$("#addDeviceForm #workShop option:selected").val(),
                        deviceType:$("#addDeviceForm #deviceType option:selected").val(),
                        techParam:$("#addDeviceForm #techParam").val(),
                        expireDate:$("#addDeviceForm #expireDate").val(),
                        deviceAdmin:$("#addDeviceForm #deviceAdmin option:selected").val(),
                        remarks:$("#addDeviceForm #remarks").val()
                    }
                    $.ajax({
                        url:"${ctx}/qmis/resourceManage/device/addDevice",
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
                    });
                    return false;//不触发form提交，否则页面跳转
                });


            })
        }

        function initDelete() {
            var checkStatus = table.checkStatus("deviceTable");
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
                    url:"${ctx}/qmis/resourceManage/device/delDevice",
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
            var checkStatus = table.checkStatus("deviceTable");
            if (checkStatus.data.length!=1){
                layer.msg("请先选择1条要修改的记录");
                layer.close(index);
                return;
            }
            var id=checkStatus.data[0].id;
            $.get("${ctx}/qmis/resourceManage/device/initEditDevice?id="+id,function (resp) {
                $("#editForm").html(resp);
                laydate.render({
                    elem: '#expireDate',
                    type: 'datetime'
                });
                var index=layer.open({
                    type: 1,
                    area: '800px',
                    title:"修改设备信息",
                    content: $("#editForm")
                });
                form.render();
                //监听表单提交事件，通过验证时才会进入回调方法
                form.on("submit(editDeviceForm)",function(){
                    var param={
                        id:id,
                        deviceName:$("#editDeviceForm #deviceName").val(),
                        deviceCode:$("#editDeviceForm #deviceCode").val(),
                        workShop:$("#editDeviceForm #workShop option:selected").val(),
                        deviceType:$("#editDeviceForm #deviceType option:selected").val(),
                        techParam:$("#editDeviceForm #techParam").val(),
                        expireDate:$("#editDeviceForm #expireDate").val(),
                        deviceAdmin:$("#editDeviceForm #deviceAdmin option:selected").val(),
                        remarks:$("#editDeviceForm #remarks").val()
                    }
                    $.ajax({
                        url:"${ctx}/qmis/resourceManage/device/updateDevice",
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
                    });
                    return false;//不触发form提交，否则页面跳转
                });
            });
        }

    });
</script>
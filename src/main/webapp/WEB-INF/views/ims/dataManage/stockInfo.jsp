<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>库存详情</title>
<div class="row">
    <div class="col-xs-12" >
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
                    <form id="query" class="layui-form">
                        <div class="form-group">
                            <label class="control-label" for="repoId">仓库名称:</label>
                            <div class="layui-input-inline">
                                <select id="repoId" lay-filter="repoId" lay-search>
                                    <c:forEach items="${repoList}" var="repo">
                                        <option value="${repo.id}">${repo.repoName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div id="supplyquery" class="widget-box widget-compact">
            <div class="widget-header widget-header-blue widget-header-flat">
                <h5 class="widget-title lighter">
                    查询详情
                </h5>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="main" style="width: border-box;height:500px;"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var scripts = [null, '${ctxStatic}/echarts/echarts.min.js', null];
    var repoId = $("#repoId option:selected").val();
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        jQuery(function ($) {
            layui.use(['form'], function() {
                var form = layui.form;
                form.render('select');
                form.on('select(repoId)', function(data){
                    query(data.value);
                });
            });
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 显示标题，图例和空的坐标轴
            myChart.setOption({
                title: {
                    text: "库存商品数",
                },
                tooltip: {},
                legend: {
                    data: ['库存商品数']
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataView: {readOnly: false},
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    name : "商品",
                    data: []
                },
                yAxis: {
                    name :"库存量(个)"
                },
                series: [{
                    name: '库存商品数',
                    type: 'bar',
                    data: []
                }]
            });

            function query(repoId) {
                $.get("${ctx}/ims/dataManage/stock/info?repoId="+repoId,function (resp) {
                    var count = new Array();
                    var name = new Array();
                    var d= resp.data;
                    if(d){
                        for (var i = 0; i < d.length; i++) {
                            count.push(d[i].count);
                            name.push(d[i].goodsName);
                        }
                    }else {
                        $.msg_show.Init({
                            'msg': '该仓库为空',
                            'type': 'error'
                        });
                    }
                    myChart.hideLoading();
                    // 填入数据
                    myChart.setOption({
                        xAxis: {
                            data: name
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '库存商品数',
                            data: count,
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true, //开启显示
                                        position: 'top', //在上方显示
                                        textStyle: { //数值样式
                                            color: 'black',
                                            fontSize: 16
                                        }
                                    }
                                }
                            },
                        }]
                    });
                });
            }
            query(repoId);
        });
    });
</script>
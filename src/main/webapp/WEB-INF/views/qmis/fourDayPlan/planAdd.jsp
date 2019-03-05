<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>四日计划</title>
</head>
<body>
    <div class="row">
        <div class="col-xs-12 col-sm-12">
            <div class="widget-box widget-compact">
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-inline">
                            <div class="form-group">
                                <label style="width:200px;" for="planName" class="col-sm-2 control-label">计划名称：</label>
                                <input type="text" class="form-control" id="planName" placeholder="输入计划名称">
                            </div>
                            <div class="form-group">
                                <label style="width:200px;" for="planName" class="col-sm-2 control-label">重合度：</label>
                                <input type="text" class="form-control" id="consistency" placeholder="输入重合度">
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
<script type="text/javascript">


</script>
</html>
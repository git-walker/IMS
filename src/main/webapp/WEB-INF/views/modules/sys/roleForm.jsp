<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="widget-body" id="roleDivId">
	<div class="widget-main" >
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/doSave" method="post" class="form-horizontal">
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="name">角色名称:</label>
			<div class="col-xs-12 col-sm-10">
				<form:input id="name" path="name" autocomplete="off" htmlEscape="false" maxlength="100" class="form-control width-100" placeholder="请输入角色名称" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="enname">英文名称:</label>
			<div class="col-xs-12 col-sm-10">
				<form:input id="enname" path="enname" htmlEscape="false" maxlength="255" class="form-control width-100" placeholder="请输入英文名称"/>
				<span class="help-inline"> 用户组标识</span>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="office.name">归属机构:</label>
			<div class="col-xs-12 col-sm-10">
				<div class="clearfix check-out input-group">
					<input readonly="" type="text" id="office.name" name="office.name" value="${role.office.name}" class="form-control"/>
					<span class="input-group-btn">
						<button type="button" id="selectOfficeMenu" class="btn btn-purple btn-sm">
							<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
							选择
						</button>
					</span>
					<input type="hidden" id="office.id" name="office.id" value="${role.office.id}"/>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="roleType">角色类型:</label>
			<div class="col-xs-12 col-sm-10">
				<form:select path="roleType" class="select2 width-100" data-placeholder="点击选择...">
					<form:option value="assignment">任务分配</form:option>
					<form:option value="security-role">管理角色</form:option>
					<form:option value="user">普通角色</form:option>
				</form:select>
				<span class="help-inline">
					用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span>
			</div>
		</div>
		<%-- <div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="sysData">是否系统数据:</label>
			<div class="col-xs-12 col-sm-10">
				<div class="clearfix">
					<label>
						<input id="sysData" name="sysData" ${role.sysData=='1'?'checked':''} class="ace ace-switch ace-switch-4 btn-flat" type="checkbox" />
						<span class="lbl"></span>
					</label>
					<span class="help-inline">系统数据只有超级管理员能进行修改</span>
				</div> --%>
				<%-- <form:select path="sysData">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span> --%>
			<!-- </div>
		</div> -->
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="useable">是否可用</label>
			<div class="col-xs-12 col-sm-10">
				<div class="clearfix">
					<label>
						<input id="useable" name="useable" ${role.useable=='1'?'checked':''} class="ace ace-switch ace-switch-4 btn-flat" type="checkbox" />
						<span class="lbl"></span>
					</label>
					<span class="help-inline">设定角色是否可用</span>
				</div>
				<%-- <form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span> --%>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="dataScope">数据范围:</label>
			<div class="col-xs-12 col-sm-10">
				<form:select path="dataScope" class="select2 width-100" data-placeholder="点击选择...">
					<form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="remarks">备注:</label>
			<div class="col-xs-12 col-sm-10">
				<div class="clearfix">
					<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control width-100" placeholder="请输入备注信息" />
				</div>
			</div>
		</div>
		<%-- <div class="form-actions">
			<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
				<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> --%>
		<form:hidden path="id"/>
	</form:form>
	</div>
</div>
<div id="selectOfficeTreeDiv" class="hide widget-body">
	<div class="widget-main padding-8">
		<div id="popuptreeview" class="" data-id="" data-text=""></div>
	</div>
</div>
package cn.rootyu.ims.common.entity;

import java.util.Date;

public class CodeValue {
	
	public CodeValue(){}
	
	public CodeValue(String code ,String value, String param){
		this.code = code;
		this.value = value;
		this.param = param;
	}
	
	
	private String code;
	
	private String value;
	
	private String param;
	
	private String project;
	
	private String projectId;//项目id
	
	private String stationId;//工位id
	
	private String roleId;//角色id
	
	private String flag;//选中标志位

	private Date date;

	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

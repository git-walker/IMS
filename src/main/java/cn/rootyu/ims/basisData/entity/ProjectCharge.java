package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/**
 * 项目责任人映射表
 * @author lishanzhi
 *
 */
public class ProjectCharge extends DataEntity<MetroTarget> {
	
	private static final long serialVersionUID = 1L;
	
	private String projectId; // 项目ID
	private String chargeId; // 责任人ID
	private String type; // 责任人类型 1:质量接口人, 2:售后接口人
	private String officeId; // 部门id
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }
}

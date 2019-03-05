/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/**
 * 成本对象 Entity
 * @author DHC
 * @version 2017-01-10
 */
public class Target extends DataEntity<Target> {

	private static final long serialVersionUID = 1L;
	private  String invname;//成本对象名称
	private  String invmnecode;//编码
	private  String forinvname;//成本对象外文名称
	private  String projectName;//工位名称
	private  String projectId;//工位id
	private  String memo;//备注
	
	public Target(String id) {
		super(id);
	}
	
	public Target(){
		super();
	}

	public String getInvname() {
		return invname;
	}

	public void setInvname(String invname) {
		this.invname = invname;
	}

	public String getForinvname() {
		return forinvname;
	}

	public void setForinvname(String forinvname) {
		this.forinvname = forinvname;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInvmnecode() {
		return invmnecode;
	}

	public void setInvmnecode(String invmnecode) {
		this.invmnecode = invmnecode;
	}
	
}
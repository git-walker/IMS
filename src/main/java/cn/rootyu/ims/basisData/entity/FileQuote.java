/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/**
 * 文件关联表 Entity
 * 
 * @author DHC
 * @version 2016-11-14
 */
public class FileQuote extends DataEntity<FileQuote> {

	private static final long serialVersionUID = 1L;
	private String planId;// 引用文件id
	private String fileId;// 文件id
	private String remarks;// 备注
	private String updateReason;//更新原因
	private String planType;// 计划类型
	private String status;// 引用状态


	public FileQuote(String id) {
		super(id);
	}

	public FileQuote() {
		super();
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
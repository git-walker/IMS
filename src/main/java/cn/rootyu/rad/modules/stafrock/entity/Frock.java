/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.Office;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 工装管理Entity
 * @author jinkf
 * @version 2015-10-15
 */
public class Frock extends DataEntity<Frock> {
	
	private static final long serialVersionUID = 1L;
	private String frockNum;		// 工装编号
	private String vehicleType;		// 车型
	private String belongArea;		// 所属库区ID
	private String curTask;			// 当前配送任务
	private String curPos;			// 当前位置
	private String waitTime;		// 停留时间
	private String repairStatus;	// 检修状态
	private String repairUid;		// 检修人员
	private Date nextRepair;		// 下次检修时间
	private Long useRec;			// 使用记录
	private String vehicleModel;	// 工装车类型（A，B，C，D）
	private String status;			// 当前状态
	private String storageId;		// 属于仓库
	private String standard;		// 工装规格
	private String frockCustom;		// 定制工装
	private Long useNum;			// 工装使用次数
	private Office storeroom;		//所属库区对象

	
	public Frock() {
		super();
	}	
	public Frock(String id) {
		super(id);
	}

	public String getFrockNum() {
		return frockNum;
	}

	public void setFrockNum(String frockNum) {
		this.frockNum = frockNum;
	}
	
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public String getCurTask() {
		return curTask;
	}

	public void setCurTask(String curTask) {
		this.curTask = curTask;
	}
	
	public String getCurPos() {
		return curPos;
	}

	public void setCurPos(String curPos) {
		this.curPos = curPos;
	}
	
	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	
	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}
	
	public String getRepairUid() {
		return repairUid;
	}

	public void setRepairUid(String repairUid) {
		this.repairUid = repairUid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNextRepair() {
		return nextRepair;
	}

	public void setNextRepair(Date nextRepair) {
		this.nextRepair = nextRepair;
	}
	
	public Long getUseRec() {
		return useRec;
	}

	public void setUseRec(Long useRec) {
		this.useRec = useRec;
	}
	
	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	public String getFrockCustom() {
		return frockCustom;
	}

	public void setFrockCustom(String frockCustom) {
		this.frockCustom = frockCustom;
	}
	
	public Long getUseNum() {
		return useNum;
	}

	public void setUseNum(Long useNum) {
		this.useNum = useNum;
	}
	public Office getStoreroom() {
		return storeroom;
	}
	public void setStoreroom(Office storeroom) {
		this.storeroom = storeroom;
	}
	public String getBelongArea() {
		return belongArea;
	}
	public void setBelongArea(String belongArea) {
		this.belongArea = belongArea;
	}
	
}
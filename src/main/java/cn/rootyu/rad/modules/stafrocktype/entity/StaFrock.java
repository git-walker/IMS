/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/**
 * 定制工装与工位维护Entity
 * @author wangzhixin
 * @version 2015-10-20
 */
public class StaFrock extends DataEntity<StaFrock> {
	
	private static final long serialVersionUID = 1L;
	private String stationId;		// 工位ID
	private String frockId;		// 工装ID
	private String frockNum;    //工装编码
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getFrockId() {
		return frockId;
	}
	public void setFrockId(String frockId) {
		this.frockId = frockId;
	}
	public String getFrockNum() {
		return frockNum;
	}
	public void setFrockNum(String frockNum) {
		this.frockNum = frockNum;
	}
	
	
}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.stafrock.entity.Stainfo;
import org.hibernate.validator.constraints.Length;

/**
 * aaEntity
 * @author aa
 * @version 2015-10-16
 */
public class StaFrockType extends DataEntity<StaFrockType> {
	
	private static final long serialVersionUID = 1L;
	private String stationid;		// 工位
	private String assemblyLine;		// 流水线
	private String sort;		// 顺序号
	private String frockType;		// 工装类型
	private Stainfo stainfo;
	private String workstationcode;//工位编码
	

	public StaFrockType(String stationid, String assemblyLine, String sort, String frockType, Stainfo stainfo) {
		// TODO Auto-generated constructor stub
		super();
		this.stationid = stationid;
		this.assemblyLine = assemblyLine;
		this.frockType = frockType;
		this.stainfo = stainfo;
		this.sort = sort;
	}
	public StaFrockType() {
		super();
	}
	
	public StaFrockType(String id){
		super(id);
	}
	public StaFrockType(String stationid, String frockType){
		this.stationid = stationid;
		this.frockType = frockType;
	}
	@Length(min=0, max=64, message="工位长度必须介于 0 和 64 之间")
	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}
	
	@Length(min=0, max=64, message="流水线长度必须介于 0 和 64 之间")
	public String getAssemblyLine() {
		return assemblyLine;
	}

	public void setAssemblyLine(String assemblyLine) {
		this.assemblyLine = assemblyLine;
	}
	
	@Length(min=0, max=64, message="顺序号长度必须介于 0 和 64 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=64, message="工装长度必须介于 0 和 64 之间")
	public String getFrockType() {
		return frockType;
	}

	public void setFrockid(String frockType) {
		this.frockType = frockType;
	}

	public void setStainfo(Stainfo stainfo) {
		this.stainfo = stainfo;
	}

	public Stainfo getStainfo() {
		return stainfo;
	}
	public String getWorkstationcode() {
		return workstationcode;
	}
	public void setWorkstationcode(String workstationcode) {
		this.workstationcode = workstationcode;
	}
	public void setFrockType(String frockType) {
		this.frockType = frockType;
	}
	
}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 定制工装与工位规则维护Entity
 * @author zhixin.wang
 * @version 2015-10-19
 */
public class StaFrockRule extends DataEntity<StaFrockRule> {
	
	private static final long serialVersionUID = 1L;
	private String station;		// 工位
	private String matchPrinciple;		// 匹配原则
	private String matchField;		// 匹配字段
	private String workstationcode;//工位编码
	private String gzzxbm;

	public String getGzzxbm() {
		return gzzxbm;
	}

	public void setGzzxbm(String gzzxbm) {
		this.gzzxbm = gzzxbm;
	}

	public StaFrockRule() {
		super();
	}

	public StaFrockRule(String id){
		super(id);
	}

	@Length(min=0, max=64, message="工位长度必须介于 0 和 64 之间")
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	
	@Length(min=0, max=64, message="匹配原则长度必须介于 0 和 64 之间")
	public String getMatchPrinciple() {
		return matchPrinciple;
	}

	public void setMatchPrinciple(String matchPrinciple) {
		this.matchPrinciple = matchPrinciple;
	}
	
	@Length(min=0, max=64, message="匹配字段长度必须介于 0 和 64 之间")
	public String getMatchField() {
		return matchField;
	}

	public void setMatchField(String matchField) {
		this.matchField = matchField;
	}

	public String getWorkstationcode() {
		return workstationcode;
	}

	public void setWorkstationcode(String workstationcode) {
		this.workstationcode = workstationcode;
	}
	
}
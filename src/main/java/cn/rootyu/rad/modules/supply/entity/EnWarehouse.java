/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 入库主表Entity
 * @author maliang
 * @version 2015-10-17
 */
@Alias("SupplyEnWarehouse")
public class EnWarehouse extends DataEntity<EnWarehouse> {
	
	private static final long serialVersionUID = 1L;
	
	private List<EnWarehouseZ> ewzList = new ArrayList<EnWarehouseZ>();//采购订单子表
	private List<String> fbillflagList;//单据状态集合
	private StorDoc sd;//项目仓库
	
	public List<EnWarehouseZ> getEwzList() {
		return ewzList;
	}

	public void setEwzList(List<EnWarehouseZ> ewzList) {
		this.ewzList = ewzList;
	}
	
	public List<String> getFbillflagList() {
		return fbillflagList;
	}

	public void setFbillflagList(List<String> fbillflagList) {
		this.fbillflagList = fbillflagList;
	}
	
	public StorDoc getSd() {
		return sd;
	}

	public void setSd(StorDoc sd) {
		this.sd = sd;
	}

	//--------------------------Entity---------------------------------
	private String vbillcode;		// 单据号
	private Date dbilldate;		// 单据日期
	private String cwarehouseid;		// 仓库ID
	private String pkCalbody;		// 库存组织PK
	private String cbiztype;		// 业务类型ID
	private String cdispatcherid;		// 收发类型ID
	private String cwhsmanagerid;		// 入库管理员ID
	private String cdptid;		// 采购部门ID
	private String cbizid;		// 采购员ID
	private String cproviderid;		// 供应商ID
	private String vnote;		// 备注
	private String boutretflag;		// 是否退回
	private String freplenishflag; // 是否退库
	private String pkCorp;		// 公司ID
	private String coperatorid;		// 制单人
	private Date tmaketime;		// 制单时间
	private String cregister;		// 签字人
	private Date taccounttime;		// 签字时间
	private String cbilltypecode;		// 库存单据类型编码
	private String fbillflag;		// 单据状态（1.生成 2.下发 3.上架完毕）
	private String supplyemp;	//保供人员
	
	public EnWarehouse() {
		super();
	}

	public EnWarehouse(String id){
		super(id);
	}

	@Length(min=0, max=64, message="单据号长度必须介于 0 和 64 之间")
	public String getVbillcode() {
		return vbillcode;
	}

	public void setVbillcode(String vbillcode) {
		this.vbillcode = vbillcode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDbilldate() {
		return dbilldate;
	}

	public void setDbilldate(Date dbilldate) {
		this.dbilldate = dbilldate;
	}
	
	@Length(min=0, max=64, message="仓库ID长度必须介于 0 和 64 之间")
	public String getCwarehouseid() {
		return cwarehouseid;
	}

	public void setCwarehouseid(String cwarehouseid) {
		this.cwarehouseid = cwarehouseid;
	}
	
	@Length(min=0, max=64, message="库存组织PK长度必须介于 0 和 64 之间")
	public String getPkCalbody() {
		return pkCalbody;
	}

	public void setPkCalbody(String pkCalbody) {
		this.pkCalbody = pkCalbody;
	}
	
	@Length(min=0, max=64, message="业务类型ID长度必须介于 0 和 64 之间")
	public String getCbiztype() {
		return cbiztype;
	}

	public void setCbiztype(String cbiztype) {
		this.cbiztype = cbiztype;
	}
	
	@Length(min=0, max=64, message="收发类型ID长度必须介于 0 和 64 之间")
	public String getCdispatcherid() {
		return cdispatcherid;
	}

	public void setCdispatcherid(String cdispatcherid) {
		this.cdispatcherid = cdispatcherid;
	}
	
	@Length(min=0, max=64, message="库管员ID长度必须介于 0 和 64 之间")
	public String getCwhsmanagerid() {
		return cwhsmanagerid;
	}

	public void setCwhsmanagerid(String cwhsmanagerid) {
		this.cwhsmanagerid = cwhsmanagerid;
	}
	
	@Length(min=0, max=64, message="部门ID长度必须介于 0 和 64 之间")
	public String getCdptid() {
		return cdptid;
	}

	public void setCdptid(String cdptid) {
		this.cdptid = cdptid;
	}
	
	@Length(min=0, max=64, message="业务员ID长度必须介于 0 和 64 之间")
	public String getCbizid() {
		return cbizid;
	}

	public void setCbizid(String cbizid) {
		this.cbizid = cbizid;
	}
	
	@Length(min=0, max=64, message="供应商ID长度必须介于 0 和 64 之间")
	public String getCproviderid() {
		return cproviderid;
	}

	public void setCproviderid(String cproviderid) {
		this.cproviderid = cproviderid;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getVnote() {
		return vnote;
	}

	public void setVnote(String vnote) {
		this.vnote = vnote;
	}
	
	@Length(min=0, max=1, message="是否退回长度必须介于 0 和 1 之间")
	public String getBoutretflag() {
		return boutretflag;
	}

	public void setBoutretflag(String boutretflag) {
		this.boutretflag = boutretflag;
	}
	
	public String getFreplenishflag() {
		return freplenishflag;
	}

	public void setFreplenishflag(String freplenishflag) {
		this.freplenishflag = freplenishflag;
	}
	
	@Length(min=0, max=64, message="公司ID长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
	@Length(min=0, max=64, message="制单人长度必须介于 0 和 64 之间")
	public String getCoperatorid() {
		return coperatorid;
	}

	public void setCoperatorid(String coperatorid) {
		this.coperatorid = coperatorid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTmaketime() {
		return tmaketime;
	}

	public void setTmaketime(Date tmaketime) {
		this.tmaketime = tmaketime;
	}
	
	@Length(min=0, max=64, message="库房签字人长度必须介于 0 和 64 之间")
	public String getCregister() {
		return cregister;
	}

	public void setCregister(String cregister) {
		this.cregister = cregister;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaccounttime() {
		return taccounttime;
	}

	public void setTaccounttime(Date taccounttime) {
		this.taccounttime = taccounttime;
	}
	
	@Length(min=0, max=64, message="库存单据类型编码长度必须介于 0 和 64 之间")
	public String getCbilltypecode() {
		return cbilltypecode;
	}

	public void setCbilltypecode(String cbilltypecode) {
		this.cbilltypecode = cbilltypecode;
	}
	
	@Length(min=0, max=1, message="上架完毕长度必须介于 0 和 1之间")
	public String getFbillflag() {
		return fbillflag;
	}

	public void setFbillflag(String fbillflag) {
		this.fbillflag = fbillflag;
	}

	public String getSupplyemp() {
		return supplyemp;
	}

	public void setSupplyemp(String supplyemp) {
		this.supplyemp = supplyemp;
	}
	
}
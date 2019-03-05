/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购订单主表Entity
 * 
 * @author maliang
 * @version 2015-10-13
 */
@Alias("SupplyBuyOrder")
public class BuyOrder extends DataEntity<BuyOrder> {

	private static final long serialVersionUID = 1L;

	private List<BuyOrderB> bobList = new ArrayList<BuyOrderB>();// 采购订单子表
	private User cgemp; // 采购员
	private User zdemp;//制单人
	private Office cgdept; // 采购部门
	private CuBasDoc cbd;// 供应商
	private CuManDoc cmd;// 供应商
	private String iisactive; // 是否关闭（1关闭，0非关闭）

	public List<BuyOrderB> getBobList() {
		return bobList;
	}

	public void setBobList(List<BuyOrderB> bobList) {
		this.bobList = bobList;
	}

	public User getCgemp() {
		return cgemp;
	}

	public void setCgemp(User cgemp) {
		this.cgemp = cgemp;
	}

	public User getZdemp() {
		return zdemp;
	}

	public void setZdemp(User zdemp) {
		this.zdemp = zdemp;
	}

	public Office getCgdept() {
		return cgdept;
	}

	public void setCgdept(Office cgdept) {
		this.cgdept = cgdept;
	}

	public CuBasDoc getCbd() {
		return cbd;
	}

	public void setCbd(CuBasDoc cbd) {
		this.cbd = cbd;
	}

	public CuManDoc getCmd() {
		return cmd;
	}

	public void setCmd(CuManDoc cmd) {
		this.cmd = cmd;
	}

	public String getIisactive() {
		return iisactive;
	}

	public void setIisactive(String iisactive) {
		this.iisactive = iisactive;
	}

	// --------------------------Entity---------------------------------
	private String vordercode; // 订单编号
	private String nversion; // 版本信息
	private String pkCorp; // 公司主键
	private String cbiztype; // 业务类型ID
	private String bisreplenish; // 补货标志
	private Date dorderdate; // 订单日期
	private String cpurorganization; // 采购组织ID
	private String cvendormangid; // 供应商管理ID
	private String cfreecustid; // 散户ID
	private String cemployeeid; // 采购员ID
	private String cdeptid; // 采购部门ID
	private String breturn; // 是否退货
	private String vmemo; // 备注
	private String pkCorp1; // 对应公司主键
	private String coperator; // 制单人ID
	private Date tmaketime; // 制单时间
	private String cauditpsn; // 审批人ID
	private Date taudittime; // 审批时间
	private String forderstatus; // 订单状态（0自由态，2正在审核，3审核态）
	private String ctransmodeid; // 运输方式

	public BuyOrder() {
		super();
	}

	public BuyOrder(String id) {
		super(id);
	}

	@Length(min = 0, max = 64, message = "订单编号长度必须介于 0 和 64 之间")
	public String getVordercode() {
		return vordercode;
	}

	public void setVordercode(String vordercode) {
		this.vordercode = vordercode;
	}

	@Length(min = 0, max = 32, message = "版本信息长度必须介于 0 和 32 之间")
	public String getNversion() {
		return nversion;
	}

	public void setNversion(String nversion) {
		this.nversion = nversion;
	}

	@Length(min = 0, max = 64, message = "公司主键长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}

	@Length(min = 0, max = 64, message = "业务类型ID长度必须介于 0 和 64 之间")
	public String getCbiztype() {
		return cbiztype;
	}

	public void setCbiztype(String cbiztype) {
		this.cbiztype = cbiztype;
	}

	@Length(min = 0, max = 1, message = "补货标志长度必须介于 0 和 1 之间")
	public String getBisreplenish() {
		return bisreplenish;
	}

	public void setBisreplenish(String bisreplenish) {
		this.bisreplenish = bisreplenish;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDorderdate() {
		return dorderdate;
	}

	public void setDorderdate(Date dorderdate) {
		this.dorderdate = dorderdate;
	}

	@Length(min = 0, max = 64, message = "采购组织ID长度必须介于 0 和 64 之间")
	public String getCpurorganization() {
		return cpurorganization;
	}

	public void setCpurorganization(String cpurorganization) {
		this.cpurorganization = cpurorganization;
	}

	@Length(min = 0, max = 64, message = "供应商管理ID长度必须介于 0 和 64 之间")
	public String getCvendormangid() {
		return cvendormangid;
	}

	public void setCvendormangid(String cvendormangid) {
		this.cvendormangid = cvendormangid;
	}

	@Length(min = 0, max = 64, message = "散户ID长度必须介于 0 和 64 之间")
	public String getCfreecustid() {
		return cfreecustid;
	}

	public void setCfreecustid(String cfreecustid) {
		this.cfreecustid = cfreecustid;
	}

	@Length(min = 0, max = 64, message = "采购员ID长度必须介于 0 和 64 之间")
	public String getCemployeeid() {
		return cemployeeid;
	}

	public void setCemployeeid(String cemployeeid) {
		this.cemployeeid = cemployeeid;
	}

	@Length(min = 0, max = 64, message = "采购部门ID长度必须介于 0 和 64 之间")
	public String getCdeptid() {
		return cdeptid;
	}

	public void setCdeptid(String cdeptid) {
		this.cdeptid = cdeptid;
	}

	@Length(min = 0, max = 1, message = "是否退货长度必须介于 0 和 1 之间")
	public String getBreturn() {
		return breturn;
	}

	public void setBreturn(String breturn) {
		this.breturn = breturn;
	}

	@Length(min = 0, max = 64, message = "备注长度必须介于 0 和 64 之间")
	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	@Length(min = 0, max = 64, message = "对应公司主键长度必须介于 0 和 64 之间")
	public String getPkCorp1() {
		return pkCorp1;
	}

	public void setPkCorp1(String pkCorp1) {
		this.pkCorp1 = pkCorp1;
	}

	@Length(min = 0, max = 64, message = "制单人ID长度必须介于 0 和 64 之间")
	public String getCoperator() {
		return coperator;
	}

	public void setCoperator(String coperator) {
		this.coperator = coperator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTmaketime() {
		return tmaketime;
	}

	public void setTmaketime(Date tmaketime) {
		this.tmaketime = tmaketime;
	}

	@Length(min = 0, max = 64, message = "审批人ID长度必须介于 0 和 64 之间")
	public String getCauditpsn() {
		return cauditpsn;
	}

	public void setCauditpsn(String cauditpsn) {
		this.cauditpsn = cauditpsn;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaudittime() {
		return taudittime;
	}

	public void setTaudittime(Date taudittime) {
		this.taudittime = taudittime;
	}

	@Length(min = 0, max = 1, message = "订单状态长度必须介于 0 和 1 之间")
	public String getForderstatus() {
		return forderstatus;
	}

	public void setForderstatus(String forderstatus) {
		this.forderstatus = forderstatus;
	}

	public String getCtransmodeid() {
		return ctransmodeid;
	}

	public void setCtransmodeid(String ctransmodeid) {
		this.ctransmodeid = ctransmodeid;
	}

}
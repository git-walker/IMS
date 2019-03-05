/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 到货单主表Entity
 * 
 * @author maliang
 * @version 2015-10-13
 */
@Alias("SupplyArriveOrder")
public class ArriveOrder extends DataEntity<ArriveOrder> {

	private static final long serialVersionUID = 1L;

	private List<ArriveOrderB> aobList = new ArrayList<ArriveOrderB>();// 到货单子表
	private User cg;// 采购人
	private User sh;// 收货人
	private User zj;// 质检人
	private User rk;// 入库人
	private CuBasDoc cbd;// 供应商
	private CuManDoc cmd;// 供应商
	private String estatus; //异常状态

	public List<ArriveOrderB> getAobList() {
		return aobList;
	}

	public void setAobList(List<ArriveOrderB> aobList) {
		this.aobList = aobList;
	}

	public User getCg() {
		return cg;
	}

	public void setCg(User cg) {
		this.cg = cg;
	}

	public User getSh() {
		return sh;
	}

	public void setSh(User sh) {
		this.sh = sh;
	}

	public User getZj() {
		return zj;
	}

	public void setZj(User zj) {
		this.zj = zj;
	}

	public User getRk() {
		return rk;
	}

	public void setRk(User rk) {
		this.rk = rk;
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
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	// --------------------------Entity---------------------------------
	private String varrordercode; // 到货单号
	private Date dreceivedate; // 到货日期
	private String cbiztype; // 业务类型ID
	private String cvendormangid; // 供应商管理ID
	private String cemployeeid; // 采购员ID
	private String cdeptid; // 采购部门ID
	private String ctransmodeid; // 发运方式ID
	private String creceivepsn; // 收货人ID
	private String cstoreorganization; // 库存组织ID
	private String bisback; // 是否退货
	private String vbackreasonh; // 退货理由
	private String vmemo; // 备注
	private String coperator; // 制单人ID
	private Date tmaketime; // 制单时间
	private String cauditpsn; // 审批人ID
	private Date taudittime; // 审批时间
	private String pkCorp; // 公司主键
	private String ibillstatus; // 单据状态
	private String receiveflag; // 是否收货
	private Date infQcDate; // 通知质检时间
	private String pkZjmanage; // 质检员
	private String zjsignState; // 质检员签字状态
	private Date zjdate; // 质检签字时间
	private String pkRkmanage; // 入库管理员
	private String rksignState; // 入库管理员签字状态
	private Date rkdate; // 入库管理员签字时间
	private String hclosed;// 单据是否关闭

	public ArriveOrder() {
		super();
	}

	public ArriveOrder(String id) {
		super(id);
	}

	@Length(min = 0, max = 64, message = "到货单号长度必须介于 0 和 64 之间")
	public String getVarrordercode() {
		return varrordercode;
	}

	public void setVarrordercode(String varrordercode) {
		this.varrordercode = varrordercode;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDreceivedate() {
		return dreceivedate;
	}

	public void setDreceivedate(Date dreceivedate) {
		this.dreceivedate = dreceivedate;
	}

	@Length(min = 0, max = 64, message = "业务类型ID长度必须介于 0 和 64 之间")
	public String getCbiztype() {
		return cbiztype;
	}

	public void setCbiztype(String cbiztype) {
		this.cbiztype = cbiztype;
	}

	@Length(min = 0, max = 64, message = "供应商管理ID长度必须介于 0 和 64 之间")
	public String getCvendormangid() {
		return cvendormangid;
	}

	public void setCvendormangid(String cvendormangid) {
		this.cvendormangid = cvendormangid;
	}

	@Length(min = 0, max = 64, message = "业务员ID长度必须介于 0 和 64 之间")
	public String getCemployeeid() {
		return cemployeeid;
	}

	public void setCemployeeid(String cemployeeid) {
		this.cemployeeid = cemployeeid;
	}
	
	public String getCdeptid() {
		return cdeptid;
	}

	public void setCdeptid(String cdeptid) {
		this.cdeptid = cdeptid;
	}

	@Length(min = 0, max = 64, message = "发运方式ID长度必须介于 0 和 64 之间")
	public String getCtransmodeid() {
		return ctransmodeid;
	}

	public void setCtransmodeid(String ctransmodeid) {
		this.ctransmodeid = ctransmodeid;
	}

	@Length(min = 0, max = 64, message = "收货人ID长度必须介于 0 和 64 之间")
	public String getCreceivepsn() {
		return creceivepsn;
	}

	public void setCreceivepsn(String creceivepsn) {
		this.creceivepsn = creceivepsn;
	}

	@Length(min = 0, max = 64, message = "库存组织ID长度必须介于 0 和 64 之间")
	public String getCstoreorganization() {
		return cstoreorganization;
	}

	public void setCstoreorganization(String cstoreorganization) {
		this.cstoreorganization = cstoreorganization;
	}

	@Length(min = 0, max = 1, message = "是否退货长度必须介于 0 和 1 之间")
	public String getBisback() {
		return bisback;
	}

	public void setBisback(String bisback) {
		this.bisback = bisback;
	}

	@Length(min = 0, max = 256, message = "退货理由长度必须介于 0 和 256 之间")
	public String getVbackreasonh() {
		return vbackreasonh;
	}

	public void setVbackreasonh(String vbackreasonh) {
		this.vbackreasonh = vbackreasonh;
	}

	@Length(min = 0, max = 256, message = "备注长度必须介于 0 和 256 之间")
	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
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

	@Length(min = 0, max = 64, message = "公司主键长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}

	@Length(min = 0, max = 64, message = "单据状态长度必须介于 0 和 64 之间")
	public String getIbillstatus() {
		return ibillstatus;
	}

	public void setIbillstatus(String ibillstatus) {
		this.ibillstatus = ibillstatus;
	}
	
	@Length(min = 0, max = 1, message = "是否收货长度必须介于 0 和 1 之间")
	public String getReceiveflag() {
		return receiveflag;
	}

	public void setReceiveflag(String receiveflag) {
		this.receiveflag = receiveflag;
	}
	
	public Date getInfQcDate() {
		return infQcDate;
	}

	public void setInfQcDate(Date infQcDate) {
		this.infQcDate = infQcDate;
	}

	@Length(min = 0, max = 64, message = "质检员长度必须介于 0 和 64 之间")
	public String getPkZjmanage() {
		return pkZjmanage;
	}

	public void setPkZjmanage(String pkZjmanage) {
		this.pkZjmanage = pkZjmanage;
	}

	@Length(min = 0, max = 1, message = "质检员签字状态长度必须介于 0 和 1 之间")
	public String getZjsignState() {
		return zjsignState;
	}

	public void setZjsignState(String zjsignState) {
		this.zjsignState = zjsignState;
	}
	
	public Date getZjdate() {
		return zjdate;
	}

	public void setZjdate(Date zjdate) {
		this.zjdate = zjdate;
	}
	
	@Length(min = 0, max = 64, message = "入库管理员长度必须介于 0 和 64 之间")
	public String getPkRkmanage() {
		return pkRkmanage;
	}

	public void setPkRkmanage(String pkRkmanage) {
		this.pkRkmanage = pkRkmanage;
	}

	@Length(min = 0, max = 1, message = "入库管理员签字状态长度必须介于 0 和 1 之间")
	public String getRksignState() {
		return rksignState;
	}

	public void setRksignState(String rksignState) {
		this.rksignState = rksignState;
	}

	public Date getRkdate() {
		return rkdate;
	}

	public void setRkdate(Date rkdate) {
		this.rkdate = rkdate;
	}
	
	public String getHclosed() {
		return hclosed;
	}

	public void setHclosed(String hclosed) {
		this.hclosed = hclosed;
	}

}
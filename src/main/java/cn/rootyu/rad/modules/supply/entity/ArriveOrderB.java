/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

/**
 * 到货单子表Entity
 * @author maliang
 * @version 2015-10-14
 */
@Alias("SupplyArriveOrderB")
public class ArriveOrderB extends DataEntity<ArriveOrderB> {
	
	private static final long serialVersionUID = 1L;

	private InvBasDoc ibd;//关联存货表
	private InvManDoc imd;//关联存货管理档案
	private StorDoc sd;//项目仓库

	public InvBasDoc getIbd() {
		return ibd;
	}

	public void setIbd(InvBasDoc ibd) {
		this.ibd = ibd;
	}

	public InvManDoc getImd() {
		return imd;
	}

	public void setImd(InvManDoc imd) {
		this.imd = imd;
	}
	
	public StorDoc getSd() {
		return sd;
	}

	public void setSd(StorDoc sd) {
		this.sd = sd;
	}
	
	//--------------------------Entity---------------------------------
	private String carriveorderid;		// 到货单ID
	private String cbaseid;		// 存货基础ID
	private String cmangid;		// 存货管理ID
	private String blargess;	//是否赠品
	private String blargessuprow;	//来源订单行是否赠品
	private Double narrvnum;		// 数量
	private Double nelignum;		// 合格数量
	private Double nnotelignum;		// 不合格数量
	private String cwarehouseid;		// 仓库ID
	private String vproducenum;		// 批号
	private String csourcebillid;		// 来源单据ID
	private String csourcebillrowid;		// 来源单据行ID
	private String cprojectid;		// 项目ID
	private String crowno;		// 行号
	private Double naccumwarehousenum;		// 累计入库数量
	private Double nprice;		// 本币单价
	private Double nmoney;		// 本币金额
	private String invstatus;		// 物料状态（0.正常 1.紧急物料）
	private Double overstocknum;		// 超库存数量
	private String overstockflag;		// 是否超库存（1.超库存）
	private Double nowNinnum;		// 本次实入数量
	
	public ArriveOrderB() {
		super();
	}

	public ArriveOrderB(String id){
		super(id);
	}

	@Length(min=0, max=64, message="到货单ID长度必须介于 0 和 64 之间")
	public String getCarriveorderid() {
		return carriveorderid;
	}

	public void setCarriveorderid(String carriveorderid) {
		this.carriveorderid = carriveorderid;
	}
	
	@Length(min=0, max=64, message="存货基础ID长度必须介于 0 和 64 之间")
	public String getCbaseid() {
		return cbaseid;
	}

	public void setCbaseid(String cbaseid) {
		this.cbaseid = cbaseid;
	}
	
	public String getCmangid() {
		return cmangid;
	}

	public void setCmangid(String cmangid) {
		this.cmangid = cmangid;
	}

	public String getBlargess() {
		return blargess;
	}

	public void setBlargess(String blargess) {
		this.blargess = blargess;
	}

	public String getBlargessuprow() {
		return blargessuprow;
	}

	public void setBlargessuprow(String blargessuprow) {
		this.blargessuprow = blargessuprow;
	}

	public Double getNarrvnum() {
		return narrvnum;
	}

	public void setNarrvnum(Double narrvnum) {
		this.narrvnum = narrvnum;
	}
	
	public Double getNelignum() {
		return nelignum;
	}

	public void setNelignum(Double nelignum) {
		this.nelignum = nelignum;
	}
	
	public Double getNnotelignum() {
		return nnotelignum;
	}

	public void setNnotelignum(Double nnotelignum) {
		this.nnotelignum = nnotelignum;
	}
	
	@Length(min=0, max=64, message="仓库ID长度必须介于 0 和 64 之间")
	public String getCwarehouseid() {
		return cwarehouseid;
	}

	public void setCwarehouseid(String cwarehouseid) {
		this.cwarehouseid = cwarehouseid;
	}
	
	@Length(min=0, max=64, message="批号长度必须介于 0 和 64 之间")
	public String getVproducenum() {
		return vproducenum;
	}

	public void setVproducenum(String vproducenum) {
		this.vproducenum = vproducenum;
	}
	
	@Length(min=0, max=64, message="来源单据ID长度必须介于 0 和 64 之间")
	public String getCsourcebillid() {
		return csourcebillid;
	}

	public void setCsourcebillid(String csourcebillid) {
		this.csourcebillid = csourcebillid;
	}
	
	public String getCsourcebillrowid() {
		return csourcebillrowid;
	}

	public void setCsourcebillrowid(String csourcebillrowid) {
		this.csourcebillrowid = csourcebillrowid;
	}

	@Length(min=0, max=64, message="项目ID长度必须介于 0 和 64 之间")
	public String getCprojectid() {
		return cprojectid;
	}

	public void setCprojectid(String cprojectid) {
		this.cprojectid = cprojectid;
	}
	
	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	public Double getNaccumwarehousenum() {
		return naccumwarehousenum;
	}

	public void setNaccumwarehousenum(Double naccumwarehousenum) {
		this.naccumwarehousenum = naccumwarehousenum;
	}
	
	public Double getNprice() {
		return nprice;
	}

	public void setNprice(Double nprice) {
		this.nprice = nprice;
	}
	
	public Double getNmoney() {
		return nmoney;
	}

	public void setNmoney(Double nmoney) {
		this.nmoney = nmoney;
	}
	
	@Length(min=0, max=1, message="紧急物料长度必须介于 0 和 1 之间")
	public String getInvstatus() {
		return invstatus;
	}

	public void setInvstatus(String invstatus) {
		this.invstatus = invstatus;
	}
	
	public Double getOverstocknum() {
		return overstocknum;
	}

	public void setOverstocknum(Double overstocknum) {
		this.overstocknum = overstocknum;
	}
	
	@Length(min=0, max=1, message="超库存长度必须介于 0 和 1 之间")
	public String getOverstockflag() {
		return overstockflag;
	}

	public void setOverstockflag(String overstockflag) {
		this.overstockflag = overstockflag;
	}

	public Double getNowNinnum() {
		return nowNinnum;
	}

	public void setNowNinnum(Double nowNinnum) {
		this.nowNinnum = nowNinnum;
	}

}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

/**
 * 采购订单子表Entity
 * @author maliang
 * @version 2015-10-17
 */
@Alias("SupplyBuyOrderB")
public class BuyOrderB extends DataEntity<BuyOrderB> {
	
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
	private String corderid;		// 采购订单ID
	private String pkArrvstoorg;		// 收货库存组织
	private String crowno;		// 行号
	private String ccontractid;		// 合同号
	private String cbaseid;		// 存货基础ID
	private String blargess;	//是否赠品
	private String vproducenum;		// 批次号
	private Double nordernum;		// 数量
	private Double norgtaxprice;		// 原币含税单价
	private Double noriginalcurprice;		// 原币无税单价
	private Double ntaxrate;		// 税率
	private Double noriginalcurmny;		// 原币净无税金额
	private Double norigtaxcurmny;		// 原币含税金额
	private Double noriginalnetprice;	//原币净无税单价
	private String cprojectid;		// 项目ID
	private String pkCorp;		// 公司主键
	private Double naccumstorenum;		// 累计入库数量
	private String cwarehouseid;		// 收货仓库ID
	private String iisactive;		// 是否关闭（1关闭，0非关闭）
	private Double naccumarrvnum;//累计到货数量
	
	public BuyOrderB() {
		super();
	}

	public BuyOrderB(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="采购订单ID长度必须介于 0 和 64 之间")
	public String getCorderid() {
		return corderid;
	}

	public void setCorderid(String corderid) {
		this.corderid = corderid;
	}
	
	@Length(min=0, max=64, message="收货库存组织长度必须介于 0 和 64 之间")
	public String getPkArrvstoorg() {
		return pkArrvstoorg;
	}

	public void setPkArrvstoorg(String pkArrvstoorg) {
		this.pkArrvstoorg = pkArrvstoorg;
	}
	
	@Length(min=0, max=64, message="行号长度必须介于 0 和 64 之间")
	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	@Length(min=0, max=64, message="合同号长度必须介于 0 和 64 之间")
	public String getCcontractid() {
		return ccontractid;
	}

	public void setCcontractid(String ccontractid) {
		this.ccontractid = ccontractid;
	}
	
	@Length(min=0, max=64, message="存货基础ID长度必须介于 0 和 64 之间")
	public String getCbaseid() {
		return cbaseid;
	}

	public void setCbaseid(String cbaseid) {
		this.cbaseid = cbaseid;
	}
	
	public String getBlargess() {
		return blargess;
	}

	public void setBlargess(String blargess) {
		this.blargess = blargess;
	}

	@Length(min=0, max=64, message="批次号长度必须介于 0 和 64 之间")
	public String getVproducenum() {
		return vproducenum;
	}

	public void setVproducenum(String vproducenum) {
		this.vproducenum = vproducenum;
	}
	
	public Double getNordernum() {
		return nordernum;
	}

	public void setNordernum(Double nordernum) {
		this.nordernum = nordernum;
	}
	
	public Double getNorgtaxprice() {
		return norgtaxprice;
	}

	public void setNorgtaxprice(Double norgtaxprice) {
		this.norgtaxprice = norgtaxprice;
	}
	
	public Double getNoriginalcurprice() {
		return noriginalcurprice;
	}

	public void setNoriginalcurprice(Double noriginalcurprice) {
		this.noriginalcurprice = noriginalcurprice;
	}
	
	public Double getNtaxrate() {
		return ntaxrate;
	}

	public void setNtaxrate(Double ntaxrate) {
		this.ntaxrate = ntaxrate;
	}
	
	public Double getNoriginalcurmny() {
		return noriginalcurmny;
	}

	public void setNoriginalcurmny(Double noriginalcurmny) {
		this.noriginalcurmny = noriginalcurmny;
	}
	
	public Double getNorigtaxcurmny() {
		return norigtaxcurmny;
	}

	public void setNorigtaxcurmny(Double norigtaxcurmny) {
		this.norigtaxcurmny = norigtaxcurmny;
	}
	
	public Double getNoriginalnetprice() {
		return noriginalnetprice;
	}

	public void setNoriginalnetprice(Double noriginalnetprice) {
		this.noriginalnetprice = noriginalnetprice;
	}

	@Length(min=0, max=64, message="项目ID长度必须介于 0 和 64 之间")
	public String getCprojectid() {
		return cprojectid;
	}

	public void setCprojectid(String cprojectid) {
		this.cprojectid = cprojectid;
	}
	
	@Length(min=0, max=64, message="公司主键长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
	public Double getNaccumstorenum() {
		return naccumstorenum;
	}

	public void setNaccumstorenum(Double naccumstorenum) {
		this.naccumstorenum = naccumstorenum;
	}
	
	@Length(min=0, max=64, message="收货仓库ID长度必须介于 0 和 64 之间")
	public String getCwarehouseid() {
		return cwarehouseid;
	}

	public void setCwarehouseid(String cwarehouseid) {
		this.cwarehouseid = cwarehouseid;
	}
	
	@Length(min=0, max=1, message="是否关闭（1关闭，0非关闭）长度必须介于 0 和 1 之间")
	public String getIisactive() {
		return iisactive;
	}

	public void setIisactive(String iisactive) {
		this.iisactive = iisactive;
	}

	public Double getNaccumarrvnum() {
		return naccumarrvnum;
	}

	public void setNaccumarrvnum(Double naccumarrvnum) {
		this.naccumarrvnum = naccumarrvnum;
	}
	
}
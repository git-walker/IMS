/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 入库子表Entity
 * @author maliang
 * @version 2015-10-17
 */
@Alias("SupplyEnWarehouseZ")
public class EnWarehouseZ extends DataEntity<EnWarehouseZ> implements Cloneable {
	
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		EnWarehouseZ ewz = null;
		try {
			ewz = (EnWarehouseZ) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ewz;
	}

	private InvBasDoc ibd;//关联存货表
	private Station sta;//工位
	private CargDoc cd;//货位
	private User kgy;//库管员（上架人员）

	public InvBasDoc getIbd() {
		return ibd;
	}

	public void setIbd(InvBasDoc ibd) {
		this.ibd = ibd;
	}

	public Station getSta() {
		return sta;
	}

	public void setSta(Station sta) {
		this.sta = sta;
	}

	public CargDoc getCd() {
		return cd;
	}

	public void setCd(CargDoc cd) {
		this.cd = cd;
	}

	public User getKgy() {
		return kgy;
	}

	public void setKgy(User kgy) {
		this.kgy = kgy;
	}

	private static final long serialVersionUID = 1L;
	
	//--------------------------Entity---------------------------------
	private String pkEwhid;		// 入库单主表ID
	private String cinvbasid;	//存货基本ID
	private String cinventoryid;		// 存货管理ID
	private Double ninnum;		// 实入数量
	private Double naccuminnum;	//	累计上架数量
	private Double naccumactinnum;//累计实际上架数量
	private String flargess;		// 是否赠品
	private String bsourcelargess;	//上游是否赠品行
	private Date dbizdate;		// 入库日期
	private String cfirstbillhid;		// 订单ID
	private String csourcebillhid;		// 到货单ID
	private String cfirstbillbid;		// 订单体ID
	private String csourcebillbid;		// 到货体ID
	private String cprojectid;		// 项目ID
	private String crowno;		// 行号
	private String pkStationid;		// 工位信息
	private String pkCspaceid;		// 货位ID
	private String batchcode;		// 批次号
	private String batchcodenum;		// 序列号
	private String shelvesflag;		// 上架标识
	private Double ninsumnum;		// 实到总数量
	private String invstatus;		// 物料状态（0.正常 1.紧急物料）
	private String pkStokeepid;		// 库管员（上架人员）
	private Double nprice;		// 本币单价
	private Double nmoney;		// 本币金额
	
	public EnWarehouseZ() {
		super();
	}

	public EnWarehouseZ(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="入库单主表ID长度必须介于 0 和 64 之间")
	public String getPkEwhid() {
		return pkEwhid;
	}

	public void setPkEwhid(String pkEwhid) {
		this.pkEwhid = pkEwhid;
	}

	public String getCinvbasid() {
		return cinvbasid;
	}

	public void setCinvbasid(String cinvbasid) {
		this.cinvbasid = cinvbasid;
	}

	@Length(min=0, max=64, message="存货ID长度必须介于 0 和 64 之间")
	public String getCinventoryid() {
		return cinventoryid;
	}

	public void setCinventoryid(String cinventoryid) {
		this.cinventoryid = cinventoryid;
	}
	
	public Double getNinnum() {
		return ninnum;
	}

	public void setNinnum(Double ninnum) {
		this.ninnum = ninnum;
	}
	
	public Double getNaccuminnum() {
		return naccuminnum;
	}

	public void setNaccuminnum(Double naccuminnum) {
		this.naccuminnum = naccuminnum;
	}

	public Double getNaccumactinnum() {
		return naccumactinnum;
	}

	public void setNaccumactinnum(Double naccumactinnum) {
		this.naccumactinnum = naccumactinnum;
	}

	@Length(min=0, max=1, message="是否赠品长度必须介于 0 和 1 之间")
	public String getFlargess() {
		return flargess;
	}

	public void setFlargess(String flargess) {
		this.flargess = flargess;
	}
	
	public String getBsourcelargess() {
		return bsourcelargess;
	}

	public void setBsourcelargess(String bsourcelargess) {
		this.bsourcelargess = bsourcelargess;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDbizdate() {
		return dbizdate;
	}

	public void setDbizdate(Date dbizdate) {
		this.dbizdate = dbizdate;
	}
	
	@Length(min=0, max=64, message="订单ID长度必须介于 0 和 64 之间")
	public String getCfirstbillhid() {
		return cfirstbillhid;
	}

	public void setCfirstbillhid(String cfirstbillhid) {
		this.cfirstbillhid = cfirstbillhid;
	}
	
	@Length(min=0, max=64, message="到货单ID长度必须介于 0 和 64 之间")
	public String getCsourcebillhid() {
		return csourcebillhid;
	}

	public void setCsourcebillhid(String csourcebillhid) {
		this.csourcebillhid = csourcebillhid;
	}

	public String getCfirstbillbid() {
		return cfirstbillbid;
	}

	public void setCfirstbillbid(String cfirstbillbid) {
		this.cfirstbillbid = cfirstbillbid;
	}
	
	public String getCsourcebillbid() {
		return csourcebillbid;
	}

	public void setCsourcebillbid(String csourcebillbid) {
		this.csourcebillbid = csourcebillbid;
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

	@Length(min=0, max=64, message="工位信息长度必须介于 0 和 64 之间")
	public String getPkStationid() {
		return pkStationid;
	}

	public void setPkStationid(String pkStationid) {
		this.pkStationid = pkStationid;
	}
	
	@Length(min=0, max=1024, message="货位ID长度必须介于 0 和 1024 之间")
	public String getPkCspaceid() {
		return pkCspaceid;
	}

	public void setPkCspaceid(String pkCspaceid) {
		this.pkCspaceid = pkCspaceid;
	}
	
	@Length(min=0, max=64, message="批次号长度必须介于 0 和 64 之间")
	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	
	@Length(min=0, max=64, message="序列号长度必须介于 0 和 64 之间")
	public String getBatchcodenum() {
		return batchcodenum;
	}

	public void setBatchcodenum(String batchcodenum) {
		this.batchcodenum = batchcodenum;
	}
	
	@Length(min=0, max=1, message="上架标识长度必须介于 0 和 1 之间")
	public String getShelvesflag() {
		return shelvesflag;
	}

	public void setShelvesflag(String shelvesflag) {
		this.shelvesflag = shelvesflag;
	}
	
	public Double getNinsumnum() {
		return ninsumnum;
	}

	public void setNinsumnum(Double ninsumnum) {
		this.ninsumnum = ninsumnum;
	}
	
	@Length(min=0, max=1, message="紧急物料长度必须介于 0 和 1 之间")
	public String getInvstatus() {
		return invstatus;
	}

	public void setInvstatus(String invstatus) {
		this.invstatus = invstatus;
	}
	
	@Length(min=0, max=64, message="库管员（上架人员）长度必须介于 0 和 64 之间")
	public String getPkStokeepid() {
		return pkStokeepid;
	}

	public void setPkStokeepid(String pkStokeepid) {
		this.pkStokeepid = pkStokeepid;
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
	
}
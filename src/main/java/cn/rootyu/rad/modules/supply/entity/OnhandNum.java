/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 现存量表Entity
 * @author maliang
 * @version 2015-10-21
 */
@Alias("SupplyOnhandNum")
public class OnhandNum extends DataEntity<OnhandNum> {
	
	private static final long serialVersionUID = 1L;
	
	private Double balance;//差额
	private List<String> pkInvbasdocList;//存货基本档案主键集合
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<String> getPkInvbasdocList() {
		return pkInvbasdocList;
	}

	public void setPkInvbasdocList(List<String> pkInvbasdocList) {
		this.pkInvbasdocList = pkInvbasdocList;
	}

	//--------------------------Entity---------------------------------
	private String pkInvbasdoc;		// 存货基本档案主键
	private String pkInvmandoc;		// 存货管理档案主键
	private String sealflag;		// 封存标志
	private Double safetystocknum;		// 安全库存
	private Double lowstocknum;		// 最低库存
	private Double maxstornum;		// 最高库存
	private String pkStordoc;		// 主仓库
	private String pkCorp;		// 公司主键
	private Double currentamount;		// 库存存量
	private String oldstore;		// 老区主仓库
	private String pkStationid;		// 工位信息
	private String pkGoodid;		// 货位信息
	private String goodcode;		// 货位编码
	
	public OnhandNum() {
		super();
	}

	public OnhandNum(String id){
		super(id);
	}

	@Length(min=0, max=64, message="存货基本档案主键长度必须介于 0 和 64 之间")
	public String getPkInvbasdoc() {
		return pkInvbasdoc;
	}

	public void setPkInvbasdoc(String pkInvbasdoc) {
		this.pkInvbasdoc = pkInvbasdoc;
	}
	
	@Length(min=0, max=64, message="存货管理档案主键长度必须介于 0 和 64 之间")
	public String getPkInvmandoc() {
		return pkInvmandoc;
	}

	public void setPkInvmandoc(String pkInvmandoc) {
		this.pkInvmandoc = pkInvmandoc;
	}
	
	@Length(min=0, max=1, message="封存标志长度必须介于 0 和 1 之间")
	public String getSealflag() {
		return sealflag;
	}

	public void setSealflag(String sealflag) {
		this.sealflag = sealflag;
	}
	
	public Double getSafetystocknum() {
		return safetystocknum;
	}

	public void setSafetystocknum(Double safetystocknum) {
		this.safetystocknum = safetystocknum;
	}
	
	public Double getLowstocknum() {
		return lowstocknum;
	}

	public void setLowstocknum(Double lowstocknum) {
		this.lowstocknum = lowstocknum;
	}
	
	public Double getMaxstornum() {
		return maxstornum;
	}

	public void setMaxstornum(Double maxstornum) {
		this.maxstornum = maxstornum;
	}
	
	@Length(min=0, max=64, message="主仓库长度必须介于 0 和 64 之间")
	public String getPkStordoc() {
		return pkStordoc;
	}

	public void setPkStordoc(String pkStordoc) {
		this.pkStordoc = pkStordoc;
	}
	
	@Length(min=0, max=64, message="公司主键长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
	public Double getCurrentamount() {
		return currentamount;
	}

	public void setCurrentamount(Double currentamount) {
		this.currentamount = currentamount;
	}
	
	@Length(min=0, max=1, message="老区主仓库长度必须介于 0 和 1 之间")
	public String getOldstore() {
		return oldstore;
	}

	public void setOldstore(String oldstore) {
		this.oldstore = oldstore;
	}
	
	@Length(min=0, max=64, message="工位信息长度必须介于 0 和 64 之间")
	public String getPkStationid() {
		return pkStationid;
	}

	public void setPkStationid(String pkStationid) {
		this.pkStationid = pkStationid;
	}
	
	@Length(min=0, max=64, message="货位信息长度必须介于 0 和 64 之间")
	public String getPkGoodid() {
		return pkGoodid;
	}

	public void setPkGoodid(String pkGoodid) {
		this.pkGoodid = pkGoodid;
	}
	
	@Length(min=0, max=64, message="货位编码长度必须介于 0 和 64 之间")
	public String getGoodcode() {
		return goodcode;
	}

	public void setGoodcode(String goodcode) {
		this.goodcode = goodcode;
	}
	
}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * aaEntity
 * @author aa
 * @version 2015-10-22
 */
public class Stordoc extends DataEntity<Stordoc> {
	
	private static final long serialVersionUID = 1L;
	private String principalphone;		// 负责人电话
	private String gubflag;		// 是否废品库
	private String isatpaffected;		// 是否影响ATP
	private String iscalculatedinvcost;		// 是否进行存货成本计算
	private String memo;		// 备注
	private String pkAddress;		// 地点档案主键
	private String pkCalbody;		// 库存组织主键
	private String pkCorp;		// 公司主键
	private String principalname;		// 负责人名称
	private String proflag;		// 是否生产仓库
	private String sealflag;		// 暂封标志
	private String storaddr;		// 仓库地址
	private String storcode;		// 仓库编码
	private String storname;		// 仓库名称
	private String supbalflag;		// 是否参与物资需求平衡
	private String storageflag;		// 是否货位管理
	private String canceled;		// 暂封标志
	private String addresscode;		// 地点编码
	private String stornamed;    //被借仓库名称
	
	
	public String getStornamed() {
		return stornamed;
	}

	public void setStornamed(String stornamed) {
		this.stornamed = stornamed;
	}

	public Stordoc() {
		super();
	}

	public Stordoc(String id){
		super(id);
	}

	@Length(min=0, max=64, message="负责人电话长度必须介于 0 和 64 之间")
	public String getPrincipalphone() {
		return principalphone;
	}

	public void setPrincipalphone(String principalphone) {
		this.principalphone = principalphone;
	}
	
	@Length(min=0, max=1, message="是否废品库长度必须介于 0 和 1 之间")
	public String getGubflag() {
		return gubflag;
	}

	public void setGubflag(String gubflag) {
		this.gubflag = gubflag;
	}
	
	@Length(min=0, max=1, message="是否影响ATP长度必须介于 0 和 1 之间")
	public String getIsatpaffected() {
		return isatpaffected;
	}

	public void setIsatpaffected(String isatpaffected) {
		this.isatpaffected = isatpaffected;
	}
	
	@Length(min=0, max=1, message="是否进行存货成本计算长度必须介于 0 和 1 之间")
	public String getIscalculatedinvcost() {
		return iscalculatedinvcost;
	}

	public void setIscalculatedinvcost(String iscalculatedinvcost) {
		this.iscalculatedinvcost = iscalculatedinvcost;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=64, message="地点档案主键长度必须介于 0 和 64 之间")
	public String getPkAddress() {
		return pkAddress;
	}

	public void setPkAddress(String pkAddress) {
		this.pkAddress = pkAddress;
	}
	
	@Length(min=0, max=64, message="库存组织主键长度必须介于 0 和 64 之间")
	public String getPkCalbody() {
		return pkCalbody;
	}

	public void setPkCalbody(String pkCalbody) {
		this.pkCalbody = pkCalbody;
	}
	
	@Length(min=0, max=64, message="公司主键长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
	@Length(min=0, max=256, message="负责人名称长度必须介于 0 和 256 之间")
	public String getPrincipalname() {
		return principalname;
	}

	public void setPrincipalname(String principalname) {
		this.principalname = principalname;
	}
	
	@Length(min=0, max=1, message="是否生产仓库长度必须介于 0 和 1 之间")
	public String getProflag() {
		return proflag;
	}

	public void setProflag(String proflag) {
		this.proflag = proflag;
	}
	
	@Length(min=0, max=1, message="暂封标志长度必须介于 0 和 1 之间")
	public String getSealflag() {
		return sealflag;
	}

	public void setSealflag(String sealflag) {
		this.sealflag = sealflag;
	}
	
	@Length(min=0, max=256, message="仓库地址长度必须介于 0 和 256 之间")
	public String getStoraddr() {
		return storaddr;
	}

	public void setStoraddr(String storaddr) {
		this.storaddr = storaddr;
	}
	
	@Length(min=0, max=64, message="仓库编码长度必须介于 0 和 64 之间")
	public String getStorcode() {
		return storcode;
	}

	public void setStorcode(String storcode) {
		this.storcode = storcode;
	}
	
	@Length(min=0, max=256, message="仓库名称长度必须介于 0 和 256 之间")
	public String getStorname() {
		return storname;
	}

	public void setStorname(String storname) {
		this.storname = storname;
	}
	
	@Length(min=0, max=1, message="是否参与物资需求平衡长度必须介于 0 和 1 之间")
	public String getSupbalflag() {
		return supbalflag;
	}

	public void setSupbalflag(String supbalflag) {
		this.supbalflag = supbalflag;
	}
	
	@Length(min=0, max=1, message="是否货位管理长度必须介于 0 和 1 之间")
	public String getStorageflag() {
		return storageflag;
	}

	public void setStorageflag(String storageflag) {
		this.storageflag = storageflag;
	}
	
	@Length(min=0, max=1, message="暂封标志长度必须介于 0 和 1 之间")
	public String getCanceled() {
		return canceled;
	}

	public void setCanceled(String canceled) {
		this.canceled = canceled;
	}
	
	@Length(min=0, max=64, message="地点编码长度必须介于 0 和 64 之间")
	public String getAddresscode() {
		return addresscode;
	}

	public void setAddresscode(String addresscode) {
		this.addresscode = addresscode;
	}
	
}
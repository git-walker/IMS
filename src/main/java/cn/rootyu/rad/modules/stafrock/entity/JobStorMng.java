/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

/**
 * 项目仓库管理Entity
 * @author jinkf
 * @version 2015-10-23
 */
public class JobStorMng extends DataEntity<JobStorMng> {
	
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
	private String addresscode;		// 地点编码
	private String pkReservoirid;   //库区ID
	private Office office;
	private String vehiclemodelid ;
	private String vehiclemodel;



	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public JobStorMng() {
		super();
	}

	public JobStorMng(String id){
		super(id);
	}

	public String getPrincipalphone() {
		return principalphone;
	}

	public void setPrincipalphone(String principalphone) {
		this.principalphone = principalphone;
	}
	
	public String getGubflag() {
		return gubflag;
	}

	public void setGubflag(String gubflag) {
		this.gubflag = gubflag;
	}
	
	public String getIsatpaffected() {
		return isatpaffected;
	}

	public void setIsatpaffected(String isatpaffected) {
		this.isatpaffected = isatpaffected;
	}
	
	public String getIscalculatedinvcost() {
		return iscalculatedinvcost;
	}

	public void setIscalculatedinvcost(String iscalculatedinvcost) {
		this.iscalculatedinvcost = iscalculatedinvcost;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getPkAddress() {
		return pkAddress;
	}

	public void setPkAddress(String pkAddress) {
		this.pkAddress = pkAddress;
	}
	
	public String getPkCalbody() {
		return pkCalbody;
	}

	public void setPkCalbody(String pkCalbody) {
		this.pkCalbody = pkCalbody;
	}
	
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
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
	
	
	@Length(min=0, max=64, message="地点编码长度必须介于 0 和 64 之间")
	public String getAddresscode() {
		return addresscode;
	}

	public void setAddresscode(String addresscode) {
		this.addresscode = addresscode;
	}

	public void setPkReservoirid(String pkReservoirid) {
		this.pkReservoirid = pkReservoirid;
	}

	public String getPkReservoirid() {
		return pkReservoirid;
	}

	public String getVehiclemodelid() {
		return vehiclemodelid;
	}

	public void setVehiclemodelid(String vehiclemodelid) {
		this.vehiclemodelid = vehiclemodelid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
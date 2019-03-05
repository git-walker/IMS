/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

/**
 * 工位档案Entity
 * @author maliang
 * @version 2015-10-21
 */
@Alias("SupplyStaManFil")
public class StaManFil extends DataEntity<StaManFil> {
	
	private static final long serialVersionUID = 1L;
	
	//--------------------------Entity---------------------------------
	private String pkWkcenter;		// 所属工作中心
	private String pkCwarehouseid;		// 仓库ID
	private String pkStorekeeper;		// 仓库管理员
	private String frocktype;		// 工装类型
	
	public StaManFil() {
		super();
	}

	public StaManFil(String id){
		super(id);
	}

	@Length(min=0, max=64, message="所属工作中心长度必须介于 0 和 64 之间")
	public String getPkWkcenter() {
		return pkWkcenter;
	}

	public void setPkWkcenter(String pkWkcenter) {
		this.pkWkcenter = pkWkcenter;
	}
	
	@Length(min=0, max=64, message="仓库ID长度必须介于 0 和 64 之间")
	public String getPkCwarehouseid() {
		return pkCwarehouseid;
	}

	public void setPkCwarehouseid(String pkCwarehouseid) {
		this.pkCwarehouseid = pkCwarehouseid;
	}
	
	@Length(min=0, max=64, message="仓库管理员长度必须介于 0 和 64 之间")
	public String getPkStorekeeper() {
		return pkStorekeeper;
	}

	public void setPkStorekeeper(String pkStorekeeper) {
		this.pkStorekeeper = pkStorekeeper;
	}

	public String getFrocktype() {
		return frocktype;
	}

	public void setFrocktype(String frocktype) {
		this.frocktype = frocktype;
	}
	
}
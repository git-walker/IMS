/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 项目仓库Entity
 * @author maliang
 * @version 2015-11-07
 */
@Alias("SupplyStorDoc")
public class StorDoc extends DataEntity<StorDoc> {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> idList;//ID集合

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	//--------------------------Entity---------------------------------
	private String storcode;		// 项目仓库编码
	private String storname;		// 项目仓库名称
	private String pkReservoirid;		// 库区ID
	
	public StorDoc() {
		super();
	}

	public StorDoc(String id){
		super(id);
	}

	@Length(min=0, max=64, message="项目仓库编码长度必须介于 0 和 64 之间")
	public String getStorcode() {
		return storcode;
	}

	public void setStorcode(String storcode) {
		this.storcode = storcode;
	}
	
	@Length(min=0, max=256, message="项目仓库名称长度必须介于 0 和 256 之间")
	public String getStorname() {
		return storname;
	}

	public void setStorname(String storname) {
		this.storname = storname;
	}
	
	@Length(min=0, max=64, message="库区ID长度必须介于 0 和 64 之间")
	public String getPkReservoirid() {
		return pkReservoirid;
	}

	public void setPkReservoirid(String pkReservoirid) {
		this.pkReservoirid = pkReservoirid;
	}
	
}
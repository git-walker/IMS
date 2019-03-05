/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;

/**
 * 客商管理档案Entity
 * @author maliang
 * @version 2015-10-21
 */
@Alias("SupplyCuManDoc")
public class CuManDoc extends DataEntity<CuManDoc> {
	
	private static final long serialVersionUID = 1L;
		
	//--------------------------Entity---------------------------------
	private String pkCubasdoc; // 客商档案主键
	
	public CuManDoc() {
		super();
	}

	public CuManDoc(String id){
		super(id);
	}

	public String getPkCubasdoc() {
		return pkCubasdoc;
	}

	public void setPkCubasdoc(String pkCubasdoc) {
		this.pkCubasdoc = pkCubasdoc;
	}
	
}
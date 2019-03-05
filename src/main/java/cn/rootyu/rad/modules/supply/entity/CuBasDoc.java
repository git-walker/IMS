/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;

/**
 * 客商基本档案Entity
 * @author maliang
 * @version 2015-10-21
 */
@Alias("SupplyCuBasDoc")
public class CuBasDoc extends DataEntity<CuBasDoc> {
	
	private static final long serialVersionUID = 1L;
	
	//--------------------------Entity---------------------------------
	private String custcode; // 客商编号
	private String custname; // 客商名称
	private String custshortname; // 客商简称
	
	public CuBasDoc() {
		super();
	}

	public CuBasDoc(String id){
		super(id);
	}

	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getCustshortname() {
		return custshortname;
	}

	public void setCustshortname(String custshortname) {
		this.custshortname = custshortname;
	}
	
}
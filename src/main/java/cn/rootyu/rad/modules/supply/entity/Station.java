/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 工位档案Entity
 * @author maliang
 * @version 2015-10-21
 */
@Alias("SupplyStation")
public class Station extends DataEntity<Station> {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> idList;//ID集合
	
	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	
	//--------------------------Entity---------------------------------
	private String gzzxbm;		// 工作中心编码
	private String gzzxmc;		// 工作中心名称
	
	public Station() {
		super();
	}

	public Station(String id){
		super(id);
	}

	public String getGzzxbm() {
		return gzzxbm;
	}

	public void setGzzxbm(String gzzxbm) {
		this.gzzxbm = gzzxbm;
	}
	
	public String getGzzxmc() {
		return gzzxmc;
	}

	public void setGzzxmc(String gzzxmc) {
		this.gzzxmc = gzzxmc;
	}
	
}
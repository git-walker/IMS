/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;

/**
 * 
 * @类功能说明：货架列与项目仓库的关系
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-12-7 下午10:30:31  
 * @版本：V1.0
 */
@Alias("JobStorCarg")
public class StorCarg extends DataEntity<StorCarg> {

	private static final long serialVersionUID = 1L;

	// --------------------------Entity---------------------------------
	private String pkStordoc; // 仓库ID
	private String pkCargcol; // 货位列
	private String reservoirId;//库区ID
	public String getPkStordoc() {
		return pkStordoc;
	}
	public void setPkStordoc(String pkStordoc) {
		this.pkStordoc = pkStordoc;
	}
	public String getPkCargcol() {
		return pkCargcol;
	}
	public void setPkCargcol(String pkCargcol) {
		this.pkCargcol = pkCargcol;
	}
	public String getReservoirId() {
		return reservoirId;
	}
	public void setReservoirId(String reservoirId) {
		this.reservoirId = reservoirId;
	}



}
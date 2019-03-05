package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/**
 * 
 * @类功能说明： 工位管理档案和货位关系
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-12-11 下午04:46:24  
 * @版本：V1.0
 */
public class StaCarg  extends DataEntity<StorCarg> {
	private static final long serialVersionUID = 1L;
	private String pkStamanfil; // 工位管理档案ID
	private String pkCargdoc; // 货位ID
	
	public String getPkStamanfil() {
		return pkStamanfil;
	}
	public void setPkStamanfil(String pkStamanfil) {
		this.pkStamanfil = pkStamanfil;
	}
	public String getPkCargdoc() {
		return pkCargdoc;
	}
	public void setPkCargdoc(String pkCargdoc) {
		this.pkCargdoc = pkCargdoc;
	}
}

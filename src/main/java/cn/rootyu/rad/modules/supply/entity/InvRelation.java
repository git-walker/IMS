package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;

/**
 * 项目仓库，工位，物料之间的关系
 * @author maliang
 * @version 2015-11-26
 */
@Alias("SupplyInvRelation")
public class InvRelation extends DataEntity<InvRelation> {
	
	private static final long serialVersionUID = 1L;
	
	//--------------------------Entity---------------------------------
	private String pkInvbasdoc; // 存货基本档案主键
//	private List<String> pkInvbasdocList;//存货基本档案主键集合
//	private String pkJobmngfil;//项目管理档案
	private OnhandNum ohn;//库存量信息
	private StaManFil smf;//工位管理

	public String getPkInvbasdoc() {
		return pkInvbasdoc;
	}

	public void setPkInvbasdoc(String pkInvbasdoc) {
		this.pkInvbasdoc = pkInvbasdoc;
	}

//	public List<String> getPkInvbasdocList() {
//		return pkInvbasdocList;
//	}
//
//	public void setPkInvbasdocList(List<String> pkInvbasdocList) {
//		this.pkInvbasdocList = pkInvbasdocList;
//	}
//
//	public String getPkJobmngfil() {
//		return pkJobmngfil;
//	}
//
//	public void setPkJobmngfil(String pkJobmngfil) {
//		this.pkJobmngfil = pkJobmngfil;
//	}

	public OnhandNum getOhn() {
		return ohn;
	}

	public void setOhn(OnhandNum ohn) {
		this.ohn = ohn;
	}

	public StaManFil getSmf() {
		return smf;
	}

	public void setSmf(StaManFil smf) {
		this.smf = smf;
	}

}

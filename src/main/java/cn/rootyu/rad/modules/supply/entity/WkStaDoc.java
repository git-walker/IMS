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
@Alias("SupplyWkStaDoc")
public class WkStaDoc extends DataEntity<WkStaDoc> {
	
	private static final long serialVersionUID = 1L;
	
	private String[] idArr;//ID数组
	private String[] codeArr;//编码数组
	
	public String[] getIdArr() {
		return idArr;
	}

	public void setIdArr(String[] idArr) {
		this.idArr = idArr;
	}

	public String[] getCodeArr() {
		return codeArr;
	}

	public void setCodeArr(String[] codeArr) {
		this.codeArr = codeArr;
	}
	
	//--------------------------Entity---------------------------------
	private String ccalbodyid;		// 所属库存组织
	private String workstationcode;		// 工位编码
	private String workstationname;		// 工位名称
	private String pkProdtline;		// 所属生产线
	private String pkWkcenter;		// 所属工作中心
	private String pkCorp;		// 公司编码
	private String pkCwarehouseid;		// 仓库ID
	private String pkStorekeeper;		// 仓库管理员
	
	public WkStaDoc() {
		super();
	}

	public WkStaDoc(String id){
		super(id);
	}

	@Length(min=0, max=64, message="所属库存组织长度必须介于 0 和 64 之间")
	public String getCcalbodyid() {
		return ccalbodyid;
	}

	public void setCcalbodyid(String ccalbodyid) {
		this.ccalbodyid = ccalbodyid;
	}
	
	@Length(min=0, max=64, message="工位编码长度必须介于 0 和 64 之间")
	public String getWorkstationcode() {
		return workstationcode;
	}

	public void setWorkstationcode(String workstationcode) {
		this.workstationcode = workstationcode;
	}
	
	@Length(min=0, max=256, message="工位名称长度必须介于 0 和 256 之间")
	public String getWorkstationname() {
		return workstationname;
	}

	public void setWorkstationname(String workstationname) {
		this.workstationname = workstationname;
	}
	
	@Length(min=0, max=64, message="所属生产线长度必须介于 0 和 64 之间")
	public String getPkProdtline() {
		return pkProdtline;
	}

	public void setPkProdtline(String pkProdtline) {
		this.pkProdtline = pkProdtline;
	}
	
	@Length(min=0, max=64, message="所属工作中心长度必须介于 0 和 64 之间")
	public String getPkWkcenter() {
		return pkWkcenter;
	}

	public void setPkWkcenter(String pkWkcenter) {
		this.pkWkcenter = pkWkcenter;
	}
	
	@Length(min=0, max=64, message="公司编码长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
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
	
}
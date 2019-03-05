/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;


import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrock;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.Role;
import cn.rootyu.rad.modules.sys.entity.User;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 工位档案管理Entity
 * @author chunze.cao
 * @version 2015-10-14
 */
public class Stainfo extends DataEntity<Stainfo> {
	
	private static final long serialVersionUID = 1L;
	private String calbodyid;					//所属库存组织
	private String workstationcode;				//工位编码
	private String workstationname;				//工位名称
	private String pkWkcenter;					//所属工作中心（工位）
	private String pkCorp;						//公司编码
	private String pkCwarehouseid;				//项目仓库ID
	private String pkStorekeeper;				//仓库管理员 
	private User user;							//根据角色ID查询用户列表
	private Role role;							//根据仓库管理员ID查询角色ID
	private Stordoc stordoc;					//项目仓库编码
	private Wk wk;								//工作中心
	private String frockType;					//工位使用工装
	private Office office;						//库区
	private String matchPrinciple;				//匹配原则
	private String pkReservoirid;               //库区ID
	private List<StaFrock> staFrockList = Lists.newArrayList();
	private List<String> staFrockIdList = Lists.newArrayList();


//	@JsonIgnore
//	public List<String> getStaFrockIdList() {
//		List<String> staFrockIdList = Lists.newArrayList();
//		for (StaFrock staFrock : staFrockList) {
//			staFrockIdList.add(staFrock.getId());
//		}
//		return staFrockIdList;
//	}
//
//	public void setRoleIdList(List<String> staFrockIdList) {
//		staFrockList = Lists.newArrayList();
//		if(staFrockIdList != null){
//			for (String roleId : staFrockIdList) {
//				StaFrock staFrock = new StaFrock();
//				staFrock.setId(roleId);
//				staFrockList.add(staFrock);
//			}
//		}
//	}

	public String getPkReservoirid() {
		return pkReservoirid;
	}

	public void setPkReservoirid(String pkReservoirid) {
		this.pkReservoirid = pkReservoirid;
	}

	public String getMatchPrinciple() {
		return matchPrinciple;
	}

	public void setMatchPrinciple(String matchPrinciple) {
		this.matchPrinciple = matchPrinciple;
	}

	public Stainfo() {
		super();
	}

	public Stainfo(String id) {
		super(id);
	}

	public String getPkCwarehouseid() {
		return pkCwarehouseid;
	}

	public void setPkCwarehouseid(String pkCwarehouseid) {
		this.pkCwarehouseid = pkCwarehouseid;
	}

	public String getPkStorekeeper() {
		return pkStorekeeper;
	}

	public void setPkStorekeeper(String pkStorekeeper) {
		this.pkStorekeeper = pkStorekeeper;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Length(min=0, max=64, message="所属库存组织长度必须介于 0 和 64 之间")
	public String getCalbodyid() {
		return calbodyid;
	}

	public void setCalbodyid(String calbodyid) {
		this.calbodyid = calbodyid;
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

	public void setStordoc(Stordoc stordoc) {
		this.stordoc = stordoc;
	}

	public Stordoc getStordoc() {
		return stordoc;
	}

	public void setWk(Wk wk) {
		this.wk = wk;
	}

	public Wk getWk() {
		return wk;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Office getOffice() {
		return office;
	}

	public String getFrockType() {
		return frockType;
	}

	public void setFrockType(String frockType) {
		this.frockType = frockType;
	}

	public List<StaFrock> getStaFrockList() {
		return staFrockList;
	}

	public void setStaFrockList(List<StaFrock> staFrockList) {
		this.staFrockList = staFrockList;
	}

	public List<String> getStaFrockIdList() {
		return staFrockIdList;
	}

	public void setStaFrockIdList(List<String> staFrockIdList) {
		this.staFrockIdList = staFrockIdList;
	}


	
	
	
	
	

}

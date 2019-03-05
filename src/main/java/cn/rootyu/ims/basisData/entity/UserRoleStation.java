package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.Office;

import java.util.List;

public class UserRoleStation extends DataEntity<UserRoleStation> {
	
	private String userId;
	
	private String userName;
	
	private Office office;	// 归属部门
	
	private String no;		// 工号
	
	private String roleId;
	
	private String roleName;
	
	private String stationId;
	
	private String stationName;
	
	private String stationNumber;
	
	private String mobile;
	
	private List<UserRoleStation> stationList;

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public List<UserRoleStation> getStationList() {
		return stationList;
	}

	public void setStationList(List<UserRoleStation> stationList) {
		this.stationList = stationList;
	}
	
	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

}

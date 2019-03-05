package cn.rootyu.ims.basisData.dao;

import cn.rootyu.ims.basisData.entity.UserRoleStation;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;

@MyBatisDao
public interface UserRoleStationDao extends CrudDao<UserRoleStation> {
	
	//查询未选中工位list
	public List<UserRoleStation> findUnselectedList(UserRoleStation param);
	
	//查询选中工位list
	public List<UserRoleStation> findSelectedList(UserRoleStation param);

	//根据工位角色查user
	public List<UserRoleStation> findUserListByRoleStation(UserRoleStation param);
	
	//根据工位查数据
	public List<UserRoleStation> findListToo(UserRoleStation userRoleStation);


}

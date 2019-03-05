package cn.rootyu.ims.basisData.service;

import java.util.List;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rootyu.ims.basisData.dao.UserRoleStationDao;
import cn.rootyu.ims.basisData.entity.UserRoleStation;

@Service
@Transactional
public class UserRoleStationService extends CrudService<UserRoleStationDao, UserRoleStation> {
	@Override
	public void save(UserRoleStation inParam){
		//清空所有当前用户角色关联工位信息
		dao.delete(inParam);
		//遍历插入工位信息
		for (UserRoleStation station : inParam.getStationList()) {
			inParam.preInsert();
			inParam.setStationId(station.getStationName());
			dao.insert(inParam);			
		}		
	}
	
	//查询未选中工位list
	public List<UserRoleStation> findUnselectedList(UserRoleStation param){
		return dao.findUnselectedList(param);
	}
	
	//查询选中工位list
	public List<UserRoleStation> findSelectedList(UserRoleStation param){
		return dao.findSelectedList(param);
	}
	
	//根据工位角色查user
	public List<UserRoleStation> findUserListByRoleStation(UserRoleStation param){
		return dao.findUserListByRoleStation(param);
	}
	//进入页面时获取数据
	@Override
	public Page<UserRoleStation> findPage(Page<UserRoleStation> page, UserRoleStation userRoleStation) {
		userRoleStation.setPage(page);
		if(userRoleStation.getStationId()==null||"".equals(userRoleStation.getStationId())){
			page.setList(dao.findList(userRoleStation));
		}else{
			page.setList(dao.findListToo(userRoleStation));
		}
		return page;
	}
}

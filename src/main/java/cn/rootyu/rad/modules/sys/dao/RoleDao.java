package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.dao.CrudDao;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * @author yuhui
 * @version 1.0
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	Role getByName(Role role);
	
	Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	int deleteRoleMenu(Role role);

	int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	int deleteRoleOffice(Role role);

	int insertRoleOffice(Role role);

}

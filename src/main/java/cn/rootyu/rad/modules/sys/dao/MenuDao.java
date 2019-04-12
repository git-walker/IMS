package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.dao.CrudDao;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Menu;

import java.util.List;

/**
 * 菜单DAO接口
 * @author yuhui
 * @version 1.0
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	List<Menu> findByParentIdsLike(Menu menu);

	List<Menu> findByUserId(Menu menu);
	
	int updateParentIds(Menu menu);
	
	int updateSort(Menu menu);
	
	List<Menu> findMobileMenuByUserId(Menu menu);

	List<Menu> findMobileMenuByRole(Menu m);
	
}

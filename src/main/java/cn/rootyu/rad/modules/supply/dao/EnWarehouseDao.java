/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.dao;

import cn.rootyu.rad.common.persistence.BaseDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.supply.entity.EnWarehouse;
import cn.rootyu.rad.modules.supply.entity.EnWarehouseZ;

import java.util.List;

/**
 * 入库表DAO接口
 * @author maliang
 * @version 2015-10-17
 */
@MyBatisDao
public interface EnWarehouseDao extends BaseDao {
	
	/**
	 * 插入主表
	 * @param entity
	 * @return
	 * @author maliang
	 * @version 2015-10-17
	 */
	int insert(EnWarehouse entity);
	
	/**
	 * 插入子表
	 * @param entity
	 * @return
	 * @author maliang
	 * @version 2015-10-17
	 */
	int insertChild(EnWarehouseZ entity);
	
	/**
	 * 查询主表数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @author maliang
	 * @version 2015-10-13
	 */
	List<EnWarehouse> findList(EnWarehouse entity);
	
	/**
	 * 查询子表数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @author maliang
	 * @version 2015-10-15
	 */
	List<EnWarehouseZ> findChildList(EnWarehouseZ entity);
	
	/**
	 * 查询主表数据
	 * @author maliang
	 * @version 2015-10-20
	 */
	EnWarehouse findInfo(EnWarehouse entity);
	
	/**
	 * 更新主表
	 * @author maliang
	 * @version 2015-10-20
	 */
	int update(EnWarehouse entity);
	
	/**
	 * 更新子表
	 * @author maliang
	 * @version 2015-10-20
	 */
	int updateChild(EnWarehouseZ entity);
	
	/**
	 * 删除主表
	 * @author maliang
	 * @version 2016-03-07
	 */
	int delete(EnWarehouse entity);
	
	/**
	 * 删除子表
	 * @author maliang
	 * @version 2016-03-07
	 */
	int deleteChild(EnWarehouseZ entity);
	
}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.dao;

import cn.rootyu.rad.common.persistence.BaseDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.supply.entity.ArriveOrder;
import cn.rootyu.rad.modules.supply.entity.ArriveOrderB;

import java.util.List;

/**
 * 到货单DAO接口
 * @author maliang
 * @version 2015-10-14
 */
@MyBatisDao
public interface ArriveOrderDao extends BaseDao {
	
	/**
	 * 插入主表
	 * @author maliang
	 * @version 2015-10-15
	 */
	int insert(ArriveOrder entity);
	
	/**
	 * 插入子表
	 * @author maliang
	 * @version 2015-10-15
	 */
	int insertChild(ArriveOrderB entity);
	
	/**
	 * 查询到货单数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @author maliang
	 * @version 2015-10-13
	 */
	List<ArriveOrder> findList(ArriveOrder entity);
	
	/**
	 * 查询到货单对应的存货数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @author maliang
	 * @version 2015-10-15
	 */
	List<ArriveOrderB> findChildList(ArriveOrderB entity);
	
	/**
	 * 查询到货单
	 * @author maliang
	 * @version 2015-10-20
	 */
	ArriveOrder findInfo(ArriveOrder entity);
	
	/**
	 * 更新主表
	 * @author maliang
	 * @version 2015-10-20
	 */
	int update(ArriveOrder entity);
	
	/**
	 * 更新子表
	 * @author maliang
	 * @version 2015-10-20
	 */
	int updateChild(ArriveOrderB entity);
	
	/**
	 * 删除主表
	 * @author maliang
	 * @version 2015-10-20
	 */
	int delete(ArriveOrder entity);
	
	/**
	 * 删除子表
	 * @author maliang
	 * @version 2015-10-20
	 */
	int deleteChild(ArriveOrderB entity);
	
}
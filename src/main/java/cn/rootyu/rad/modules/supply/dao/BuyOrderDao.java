/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.dao;

import cn.rootyu.rad.common.persistence.BaseDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.supply.entity.BuyOrder;
import cn.rootyu.rad.modules.supply.entity.BuyOrderB;

import java.util.List;

/**
 * 采购订单DAO接口
 * @author maliang
 * @version 2015-10-13
 */
@MyBatisDao
public interface BuyOrderDao extends BaseDao {
	
	/**
	 * 根据采购订单单据号查询采购订单数据
	 * @author maliang
	 * @version 2015-10-15
	 */
	BuyOrder findInfo(BuyOrder entity);
	
	/**
	 * 查询历史到货单数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @author maliang
	 * @version 2015-10-19
	 */
	List<BuyOrder> findList(BuyOrder entity);
	
	/**
	 * 查询到货单对应的存货数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @author maliang
	 * @version 2015-10-19
	 */
	List<BuyOrderB> findChildList(BuyOrderB entity);
	
	/**
	 * 更新采购订单主表
	 * @author maliang
	 * @version 2015-10-16
	 */
	int update(BuyOrder entity);
	
	/**
	 * 更新采购订单子表
	 * @author maliang
	 * @version 2015-10-16
	 */
	int updateChild(BuyOrderB entity);
	
}
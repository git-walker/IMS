/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import cn.rootyu.rad.modules.stafrock.entity.StocktakingTask;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.stafrock.entity.Wk;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.User;

import java.util.List;


/**
 * 盘库管理Controller
 * @author zhixin.wang
 * @version 2015-12-16
 */
@MyBatisDao
public interface StocktakingTaskDao  extends CrudDao<StocktakingTask> {

	List<Wk> findWkList(Wk wks);

	List<Stordoc> findStordocList(Stordoc stordoc);

	List<User> getStorUserList();

	List<Office> getReservoirList(Office office);
	
	List<InvBasDoc> getInvBasDocList(InvBasDoc invbasdoc);

	List<Wk> findstocktakingStoridList(Wk wk);

	List<User> findStorList(String value);
	
	
}
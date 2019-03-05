/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.AllocateTask;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.stafrock.entity.Wk;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.User;

import java.util.List;


/**
 * 调拨管理Controller
 * @author zhixin.wang
 * @version 2016-1-12
 */
@MyBatisDao
public interface AllocateTaskDao  extends CrudDao<AllocateTask> {

	List<InvBasDoc> getInvBasDocList(InvBasDoc invbasdoc);

	List<Stordoc> findStordocList(Stordoc stordoc);

	List<Wk> findWkList(Wk wks);
	List<User> getAllocateUserList();
	List<Office> getReservoirList(Office office);

	List<Wk> findAllocateStoridList(Wk wk);

	List<Wk> findAllocatedStoridList(Wk wked);

	List<User> findStorList(String value);

}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.dao;

import cn.rootyu.rad.common.persistence.BaseDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.supply.entity.*;
import cn.rootyu.rad.modules.sys.entity.User;

import java.util.List;

/**
 * 来料管理所需数据DAO接口
 * @author maliang
 * @version 2015-10-14
 */
@MyBatisDao
public interface SupplyDataDao extends BaseDao {
	
	/**
	 * 查询物料关系
	 * @author maliang
	 * @version 2015-11-27
	 */
	List<InvRelation> findInvRelationList(InvRelation entity);
	
	/**
	 * 查询物料关系
	 * @author maliang
	 * @version 2016-03-05
	 */
	List<InvRelation> findInvRelationNoRtList(InvRelation entity);

	/**
	 * 查询现存量
	 * @author maliang
	 * @version 2015-11-27
	 */
	List<OnhandNum> findOnhandNumList(OnhandNum entity);
	
	/**
	 * 获取工位数据
	 * @author maliang
	 * @version 2016-3-2
	 */
	Station findStationInfo(Station entity);

	/**
	 * 查询工位
	 * @author maliang
	 * @version 2015-12-3
	 */
	List<Station> findStationList(Station entity);
	
	/**
	 * 获取货位数据
	 * @author maliang
	 * @version 2016-3-2
	 */
	CargDoc findCargDocInfo(CargDoc where);

	/**
	 * 查询货位
	 * @author maliang
	 * @version 2015-12-3
	 */
	List<CargDoc> findCargDocList(CargDoc entity);
	
	/**
	 * 查询项目仓库数据
	 * @author maliang
	 * @version 2016-01-09
	 */
	StorDoc findStorDocInfo(StorDoc entity);
	
	/**
	 * 查询项目仓库数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @author maliang
	 * @version 2015-11-12
	 */
	List<StorDoc> findStorDocList(StorDoc entity);
	
	/**
	 * 查询用户信息
	 * @author maliang
	 * @version 2015-11-13
	 */
	User findUserInfo(User entity);
	
	/**
	 * 查询用户信息
	 * @author maliang
	 * @version 2015-11-13
	 */
	List<User> findUserList(User entity);

	/**
	 * 获取物料数据
	 * @author maliang
	 * @version 2016-01-25
	 */
	InvBasDoc findInvBasDocInfo(InvBasDoc entity);

	/**
	 * 获取项目数据
	 * @author maliang
	 * @version 2016-01-25
	 */
	JobBasFil findJobBasFilInfo(JobBasFil entity);
	
}
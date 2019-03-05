/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.dao;



import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrockRule;

/**
 * 定制工装与工位规则维护DAO接口
 * @author zhixin.wang
 * @version 2015-10-19
 */
@MyBatisDao
public interface StaFrockRuleDao extends CrudDao<StaFrockRule> {
	
	StaFrockRule getStaFrockRuleByStationId(String station);

}
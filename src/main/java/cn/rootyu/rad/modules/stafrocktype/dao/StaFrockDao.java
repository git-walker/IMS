/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.dao;

import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrock;

import java.util.List;
import java.util.Map;

/**
 * 定制工装与工位维护DAO接口
 * @author wangzhixin
 * @version 2015-10-20
 */
@MyBatisDao
public interface StaFrockDao extends CrudDao<StaFrock> {
	List<Map<String,Object>>  getNotMatchFrockList(String workStationId);
	List<Map<String,Object>>  getMatchFrockList(String workStationId);
	int deleteMatch(String workStationId);
}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.dao;

import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;

/**
 * 配送策划管理DAO接口
 * @author chunze.cao
 * @version 2015-10-19
 */
@MyBatisDao
public interface InvBasDocDao extends CrudDao<InvBasDoc> {

	
	/**
	 * 根据ID查询存货信息
	 */		
	//InvBasDoc checkValues(String id);
	
}
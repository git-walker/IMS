/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.act.dao;

import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author DHC
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	int updateProcInsIdByBusinessId(Act act);
	
}

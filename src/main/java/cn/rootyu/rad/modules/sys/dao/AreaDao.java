/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.persistence.TreeDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author DHC
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}

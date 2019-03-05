/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.persistence.TreeDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Office;

import java.util.List;

/**
 * 机构DAO接口
 * @author DHC
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	List<Office> getCompanys(Office office);
	// 判断处置单位是否是采购部(true:采购部下某部门,false:工艺部下某部门)
	boolean isPurchas(String id);
}

package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.dao.TreeDao;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Office;

import java.util.List;

/**
 * 机构DAO接口
 * @author yuhui
 * @version 1.0
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	List<Office> getCompanys(Office office);
	// 判断处置单位是否是采购部(true:采购部下某部门,false:工艺部下某部门)
	boolean isPurchas(String id);
}

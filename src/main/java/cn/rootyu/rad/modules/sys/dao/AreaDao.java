package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.dao.TreeDao;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author yuhui
 * @version 1.0
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}

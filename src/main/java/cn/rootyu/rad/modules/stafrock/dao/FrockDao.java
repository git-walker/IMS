package cn.rootyu.rad.modules.stafrock.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.Frock;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.sys.entity.Office;

import java.util.List;


/**
 * 工装管理DAO接口
 * @author jinkf
 * @version 2015-10-14
 */
@MyBatisDao
public interface FrockDao extends CrudDao<Frock> {

	void deleteAll(String ids);

	Frock checkValues(String id);
	
	List<Stordoc> findStordocList(Stordoc stocdoc);

	List<Office> findOfficeList(Office office);

}

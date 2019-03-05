package cn.rootyu.rad.modules.stafrock.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.MaterialMng;

import java.util.List;

/**
 * 物料（存货档案）管理DAO接口
 * @author jinkf
 * @version 2015-10-14
 */
@MyBatisDao
public interface MaterialMngDao extends CrudDao<MaterialMng> {

	MaterialMng checkValues(String id);

	boolean update2Flag(MaterialMng entity);

	List<MaterialMng> findFlagList(MaterialMng materialmng);


}

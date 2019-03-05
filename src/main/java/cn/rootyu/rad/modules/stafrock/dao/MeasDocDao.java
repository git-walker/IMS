package cn.rootyu.rad.modules.stafrock.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.supply.entity.MeasDoc;

import java.util.List;

/**
 * 计量档案DAO接口
 * @author xuxiujun
 * @version 2015-10-27
 */
@MyBatisDao
public interface MeasDocDao extends CrudDao<MeasDoc> {
	
	List<MeasDoc> findmeasList();

}

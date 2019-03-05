/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.dao;

import cn.rootyu.ims.basisData.entity.MetroTarget;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;


@MyBatisDao
public interface MetroTargetDao extends CrudDao<MetroTarget> {

	List<String> findTypeList(MetroTarget metro);
	
	int batchDelete(List<String> list);
	
	public int findMaxCode();
	
}

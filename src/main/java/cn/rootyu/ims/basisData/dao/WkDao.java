/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.dao;

import cn.rootyu.ims.basisData.entity.Wkk;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * WK DAO接口
 * @author DHC
 * @version 2016-09-05
 */
@MyBatisDao
public interface WkDao extends CrudDao<Wkk> {

	
	List<Wkk> findTypeList(Wkk wk);
	
	int batchDelete(List<String> list);
	
}

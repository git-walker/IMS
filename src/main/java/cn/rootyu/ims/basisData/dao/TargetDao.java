/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.ims.basisData.entity.Target;

/**
 * 成本对象 DAO接口
 * @author DHC
 * @version 2017-01-10
 */
@MyBatisDao
public interface TargetDao extends CrudDao<Target> {

	
}

/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.service;

import cn.rootyu.rad.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rootyu.ims.basisData.dao.TargetDao;
import cn.rootyu.ims.basisData.entity.Target;

/**
 * 成本对象Service
 * @author DHC
 * @version 2017-01-10
 */
@Service
@Transactional(readOnly = true)
public class TargetService extends CrudService<TargetDao, Target> {

	public void batchDelete(String[] ids) {
		for(int i=0;i<ids.length;i++){
			Target target = new Target(ids[i]);
			super.delete(target);
		}
	}
	
	
}

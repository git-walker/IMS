/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.service;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrocktype.dao.StaFrockTypeDao;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrockType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典Service
 * @author DHC
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class StaFrockTypeService extends CrudService<StaFrockTypeDao, StaFrockType> {
	
	
	public StaFrockType get(String id) {
		return super.get(id);
	}
	public List<StaFrockType> findList(){
		return super.findList(new StaFrockType());
	}
	
	public Page<StaFrockType> findPage(Page<StaFrockType> page, StaFrockType staFrockType) {
		return super.findPage(page, staFrockType);
	}

	@Transactional(readOnly = false)
	public void save(StaFrockType staFrockType) {
		super.save(staFrockType);
	}

	@Transactional(readOnly = false)
	public void delete(String[] ids) {
    	for (int i = 0; i < ids.length; i++) {
    		StaFrockType staFrockType = new StaFrockType(ids[i]);
    		super.delete(staFrockType);
    	}
	}
}

/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.service;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrocktype.dao.StaFrockRuleDao;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrockRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定制工装与工位规则维护Service
 * @author zhixin.wang
 * @version 2015-10-19
 */
@Service
@Transactional(readOnly = true)
public class StaFrockRuleService extends CrudService<StaFrockRuleDao, StaFrockRule> {

	public StaFrockRule get(String id) {
		return super.get(id);
	}
	
	public List<StaFrockRule> findList(StaFrockRule staFrockRule) {
		return super.findList(staFrockRule);
	}
	
	public Page<StaFrockRule> findPage(Page<StaFrockRule> page, StaFrockRule staFrockRule) {
		return super.findPage(page, staFrockRule);
	}
	
	@Transactional(readOnly = true)
	public void save(StaFrockRule staFrockRule) {
		super.save(staFrockRule);
	}

	@Transactional(readOnly = true)
	public void delete(StaFrockRule staFrockRule) {
		super.delete(staFrockRule);
	}
	
	@Transactional(readOnly = true)
	public StaFrockRule getStaFrockRuleByStationId(String station){
		return dao.getStaFrockRuleByStationId(station);
	}


	}
	

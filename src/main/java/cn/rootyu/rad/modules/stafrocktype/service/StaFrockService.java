/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.service;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrocktype.dao.StaFrockDao;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 定制工装与工位维护Service
 * @author wangzhixin
 * @version 2015-10-20
 */
@Service
@Transactional(readOnly = true)
public class StaFrockService extends CrudService<StaFrockDao, StaFrock> {
	@Autowired
	private StaFrockDao staFrockDao;
	public StaFrock get(String id) {
		return super.get(id);
	}
	
	public List<StaFrock> findList(StaFrock staFrock) {
		return super.findList(staFrock);
	}
	
	public Page<StaFrock> findPage(Page<StaFrock> page, StaFrock staFrock) {
		return super.findPage(page, staFrock);
	}
	
	@Transactional(readOnly = false)
	public void save(StaFrock staFrock) {
		super.save(staFrock);
	}
	
	@Transactional(readOnly = true)
	public void batchSave(String[] matchFrock,String workStationId) {
		dao.deleteMatch(workStationId);
    	for (int i = 0; i < matchFrock.length; i++) {
    		StaFrock staFrock = new StaFrock();
    		staFrock.setFrockId(matchFrock[i]);
    		staFrock.setStationId(workStationId);
    		super.save(staFrock);
    	}
	}
		
	@Transactional(readOnly = false)
	public void delete(StaFrock staFrock) {
		super.delete(staFrock);
	}
	
	public List<Map<String,Object>> getNotMatchFrockList(String workStationId){
//		System.out.println(staFrockDao.getNotMatchFrockList());
		return staFrockDao.getNotMatchFrockList(workStationId);
	}
	
	public List<Map<String,Object>> getMatchFrockList(String workStationId){
//		System.out.println(staFrockDao.getNotMatchFrockList());
		return staFrockDao.getMatchFrockList(workStationId);
	}
	
}
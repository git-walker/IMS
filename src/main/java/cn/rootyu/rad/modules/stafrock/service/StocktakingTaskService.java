/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.StocktakingTaskDao;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import cn.rootyu.rad.modules.stafrock.entity.StocktakingTask;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.stafrock.entity.Wk;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 盘库管理Controller
 * @author zhixin.wang
 * @version 2015-12-16
 */
@Service
@Transactional(readOnly = true)
public class StocktakingTaskService extends CrudService<StocktakingTaskDao, StocktakingTask> {
	
	
	public StocktakingTask get(String id) {
		return super.get(id);
	}

	public List<Wk> findWkList(Wk wks) {
		// TODO Auto-generated method stub
		return dao.findWkList(wks);
	}
	public List<Stordoc> findStordocList(Stordoc stordoc){
		return dao.findStordocList(stordoc);
	}
	public List<InvBasDoc> getInvBasDocList(InvBasDoc invbasdoc) {
		// TODO Auto-generated method stub
		return dao.getInvBasDocList(invbasdoc);
	}

	/**
	 * 删除工位信息
	 */	
	@Transactional(readOnly = false)
	public void delete(String[] ids) {
		   for (int i = 0; i < ids.length; i++) {
			   StocktakingTask stocktakingTask = new StocktakingTask();
			   stocktakingTask.setId(ids[i]);
		    	super.delete(stocktakingTask);
		    }	
	}

	public List<Map<String,Object>> getStorUserList(){
		List<User> storUserList = Lists.newArrayList();
		List<Map<String,Object>> userlist = Lists.newArrayList();
		storUserList = dao.getStorUserList();
		if(storUserList.size()>0){
			for (User user:storUserList) {
				List<Office> officelist = Lists.newArrayList();
				officelist = dao.getReservoirList(user.getOffice());
				Map<String,Object> userMap = Maps.newHashMap();
				userMap.put("id", user.getId());
				userMap.put("name", user.getName());

				if(officelist.size()>0){
					userMap.put("reservoirid",officelist.get(0).getId());
				}
				
				
				userlist.add(userMap);
			}
		}
		return userlist;
	}

	public List<Wk> findstocktakingStoridList(Wk wk) {
		// TODO Auto-generated method stub
		return dao.findstocktakingStoridList(wk);
	}
	public List<User> findStorList(String value) {
		return dao.findStorList(value);
	}
	
}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.BorrowTaskDao;
import cn.rootyu.rad.modules.stafrock.entity.BorrowTask;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
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
 * 借料管理Controller
 * @author zhixin.wang
 * @version 2016-1-4
 */
@Service
@Transactional(readOnly = true)
public class BorrowTaskService extends CrudService<BorrowTaskDao, BorrowTask> {
	
	
	public BorrowTask get(String id) {
		return super.get(id);
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
			   BorrowTask borrowTask = new BorrowTask();
			   borrowTask.setId(ids[i]);
			   super.delete(borrowTask);
		    }	
	}

	public List<Stordoc> findStordocList(Stordoc stordoc){
		return dao.findStordocList(stordoc);
	}
	public List<Wk> findWkList(Wk wks) {
		// TODO Auto-generated method stub
		return dao.findWkList(wks);
	}
	public List<Map<String,Object>> getBorrowUserList(){
		List<User> BorrowUserList = Lists.newArrayList();
		List<Map<String,Object>> userlist = Lists.newArrayList();
		BorrowUserList = dao.getBorrowUserList();
		if(BorrowUserList.size()>0){
			for (User user:BorrowUserList) {
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
	/**
	 * 二级select
	 * @param stordoc
	 * @return
	 */
	public List<Wk> findBorrowStoridList(Wk wk){
		return dao.findBorrowStoridList(wk);
	}
	public List<Wk> findBorrowedStoridList(Wk wked){
		return dao.findBorrowedStoridList(wked);
	}
	public List<User> findStorList(String value) {
		return dao.findStorList(value);
	}
}
/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.service;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.InvBasDocDao;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配送策划管理Service
 * @author chunze.cao
 * @version 2015-10-19
 */
@Service
@Transactional(readOnly = true)
public class InvBasDocService extends CrudService<InvBasDocDao, InvBasDoc> {
	
	public InvBasDoc get(String id) {
		return super.get(id);
	}
	
	public List<InvBasDoc> findList(InvBasDoc invBasDoc) {
		return super.findList(invBasDoc);
	}
	/**
	 * 查询存货信息
	 */			
	public Page<InvBasDoc> findPage(Page<InvBasDoc> page, InvBasDoc invBasDoc) {
		return super.findPage(page, invBasDoc);
	}

	/**
	 * 保存或修改存货信息
	 */		
	@Transactional(readOnly = false)
	public void save(InvBasDoc invBasDoc) {
		super.save(invBasDoc);
		
	}
	/**
	 * 删除存货信息
	 */			
	@Transactional(readOnly = false)
	public void delete(String[] ids) {
	   for (int i = 0; i < ids.length; i++) {
		   InvBasDoc invBasDoc = new InvBasDoc(ids[i]);
	    		super.delete(invBasDoc);
	    }
	}	
	
}
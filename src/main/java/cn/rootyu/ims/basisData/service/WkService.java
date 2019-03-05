/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.service;

import java.util.List;

import cn.rootyu.rad.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.rootyu.ims.basisData.dao.WkDao;
import cn.rootyu.ims.basisData.entity.Wkk;

/**
 * 字典Service
 * @author DHC
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class WkService extends CrudService<WkDao, Wkk> {
	
	/**
	 * 查询所属部门id列表
	 * @return
	 */
	public List<Wkk> findTypeList(){
		return dao.findTypeList(new Wkk());
	}

	/**
	 * 插入
	 */
	@Transactional(readOnly = false)
	@Override
	public void save(Wkk Wkk) {
		super.save(Wkk);
	}

	/**
	 * 修改
	 */
	@Transactional(readOnly = false)
	public void update(Wkk Wkk) {
		dao.update(Wkk);
	}
	
	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	@Override
	public void delete(Wkk Wkk) {
		super.delete(Wkk);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
    	for (int i = 0; i < ids.length; i++) {
    		Wkk Wkk = new Wkk(ids[i]);
    		super.delete(Wkk);
    	}
	}
	
}

package cn.rootyu.rad.modules.stafrock.service;


import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.MaterialMngDao;
import cn.rootyu.rad.modules.stafrock.entity.MaterialMng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 物料（存货档案）Service
 * @author jinkf
 * @version 2015-10-19
 */
@Service
@Transactional
public class MaterialMngService extends CrudService<MaterialMngDao, MaterialMng> {

	public MaterialMng checkValues(String id) {
		MaterialMng resultList = dao.checkValues(id);
		return resultList;
	}

	@Transactional(readOnly = false)
	public void save(MaterialMng materialmng) {
		super.save(materialmng);
		
	}

	public void delete(String[] ids) {
		   for (int i = 0; i < ids.length; i++) {
			   MaterialMng materialmng = new MaterialMng(ids[i]);
		    		super.delete(materialmng);
		    }
		
	}

	public boolean update2Flag(MaterialMng entity) {
		
		return dao.update2Flag(entity);
	}

	public List<MaterialMng> findFlagList(MaterialMng materialmng) {
		// TODO Auto-generated method stub
		return dao.findFlagList(materialmng);
	}

}

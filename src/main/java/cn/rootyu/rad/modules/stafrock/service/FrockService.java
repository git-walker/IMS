package cn.rootyu.rad.modules.stafrock.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.FrockDao;
import cn.rootyu.rad.modules.stafrock.entity.Frock;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.sys.entity.Office;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 工装管理Service
 * @author jinkf
 * @version 2015-10-15
 */
@Service
@Transactional
public class FrockService extends CrudService<FrockDao, Frock> {

	@Transactional(readOnly = false)	
	public void delete(String[] ids) {
	   for (int i = 0; i < ids.length; i++) {
	    	Frock frock = new Frock(ids[i]);
	    		super.delete(frock);
	    }
	}

	public Frock checkValues(String id) {
		
		Frock resultList = dao.checkValues(id);
		return resultList;
	}

	public List<Stordoc> findStordocList(Stordoc stocdoc){
		return dao.findStordocList(stocdoc);
	}

	public List<Office> findOfficeList(Office office) {
		return dao.findOfficeList(office);
	}

}


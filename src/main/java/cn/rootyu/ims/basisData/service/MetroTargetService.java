package cn.rootyu.ims.basisData.service;

import java.util.List;

import cn.rootyu.rad.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rootyu.ims.basisData.dao.MetroTargetDao;
import cn.rootyu.ims.basisData.entity.MetroTarget;

@Service
@Transactional(readOnly = true)
public class MetroTargetService extends CrudService<MetroTargetDao, MetroTarget> {
	
	public List<String> findTypeList(){
		return dao.findTypeList(new MetroTarget());
	}

	@Transactional(readOnly = false)
	@Override
	public void save(MetroTarget metro) {	
		if(metro.getValue() == null || "".equals(metro.getValue())){
			int a = dao.findMaxCode();
			int max = 1;
			for(int i = 0;i < a; i++){
				max = max * 2;
			}
			metro.setValue(max+"");
		}
		super.save(metro);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(MetroTarget metro) {
		super.delete(metro);

	}
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
    	for (int i = 0; i < ids.length; i++) {
    		MetroTarget metro = new MetroTarget();
    		metro.setId(ids[i]);
    		super.delete(metro);
    	}
	}
	
	public int findMaxCode(){
		int maxcode = dao.findMaxCode();
		return maxcode;
	}
	
}

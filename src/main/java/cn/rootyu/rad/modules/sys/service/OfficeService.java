package cn.rootyu.rad.modules.sys.service;

import cn.rootyu.rad.common.service.TreeService;
import cn.rootyu.rad.modules.sys.dao.OfficeDao;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机构Service
 * @author yuhui
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}

	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_ALL_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_ALL_LIST);
	}

	public List<Office> getCompanys() {
		return UserUtils.getCompanys();
	}
}

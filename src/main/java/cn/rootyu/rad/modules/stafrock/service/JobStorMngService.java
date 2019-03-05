package cn.rootyu.rad.modules.stafrock.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.JobStorMngDao;
import cn.rootyu.rad.modules.stafrock.entity.CargDoc;
import cn.rootyu.rad.modules.stafrock.entity.JobStorMng;
import cn.rootyu.rad.modules.stafrock.entity.StorCarg;
import cn.rootyu.rad.modules.sys.entity.Office;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目仓库管理Service
 * @author jinkf
 * @version 2015-10-23
 */
@Service
@Transactional
public class JobStorMngService extends CrudService<JobStorMngDao, JobStorMng> {
	
	/**
	 * 匹配项目仓库信息
	 * @author jinkf
	 * @version 2015-10-30
	 */
	public JobStorMng jobsMtorMatch(String storid) {
		JobStorMng resultList = dao.jobsMtorMatch(storid);
		return resultList;
	}

	public void delete(String[] ids) {
		   for (int i = 0; i < ids.length; i++) {
			   JobStorMng jobstormng = new JobStorMng(ids[i]);
		    	super.delete(jobstormng);
		    }
		
	}

	public List<Office> getStoreRoomList(Office office) {
		return dao.getStoreRoomList(office);
	}

	public int updateStoreroom(JobStorMng jobStorMng) {
		return dao.updateStoreroom(jobStorMng);
		
	}

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-7
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param reservoirId
	 * @参数： @return
	 * @throws     
	 */
	public List<CargDoc> getAllStorDocTree(Map<String,Object> params) {
		List<CargDoc> cargDocList = Lists.newArrayList();
		List<CargDoc> result = new ArrayList<CargDoc>();
		cargDocList = dao.getAllStorDocTree(params);			
		Map<String, CargDoc> map = Maps.newHashMap();
		for(CargDoc cargDoc:cargDocList){
			map.put(cargDoc.getId(),cargDoc);
			String pid = cargDoc.getParent().getId();
			if(pid.equals("0")){
				result.add(cargDoc);
			}
		}
		for(CargDoc cargDoc:cargDocList){
			String pid = cargDoc.getParent().getId();
			if(map.get(pid) != null ){
				map.get(pid).getSubCargDoc().add(cargDoc);					
			}
		}
		cargDocList.clear();
		cargDocList = result;
	
		return cargDocList;
	}

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-7
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param reservoirId
	 * @参数： @param storDocId
	 * @参数： @return
	 * @throws     
	 */
	public List<String> getStorDocIdList(String reservoirId, String storDocId) {
		StorCarg storCarg = new StorCarg();
		storCarg.setPkStordoc(storDocId);
		storCarg.setReservoirId(reservoirId);
		return dao.getStorDocIdList(storCarg);
	}

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param storCargList
	 * @throws     
	 */
	@Transactional(readOnly = false)
	public void saveStorDocMenu(List<StorCarg> storCargList, String storDocId) {
		StorCarg storCargDel = new StorCarg();
		storCargDel.setPkStordoc(storDocId);
		
		dao.deleteStorDocMenu(storCargDel);
		if(storCargList.size()>0){
			
			for (StorCarg storCarg : storCargList) {
				
				storCarg.preInsert();
				dao.saveStorDocMenu(storCarg);
			}
			
		}
		
	}

	public List<JobStorMng> getVehiclemodelidList(JobStorMng JobStorMng) {
		// TODO Auto-generated method stub
		return dao.getVehiclemodelidList(JobStorMng);
	}

	public int updateVehiclemodelid(JobStorMng jobStorMng) {
		// TODO Auto-generated method stub
		return dao.updateVehiclemodelid(jobStorMng);
	}


}


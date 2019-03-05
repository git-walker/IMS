/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.service;

import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.stafrock.dao.StainfoDao;
import cn.rootyu.rad.modules.stafrock.entity.*;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrock;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 工位档案管理Service
 * @author chunze.cao
 * @version 2015-10-14
 */
@Service
@Transactional(readOnly = true)
public class StainfoService extends CrudService<StainfoDao, Stainfo> {
	
	
	public Stainfo get(String id) {
		return super.get(id);
	}
	
	
	public List<Stainfo> findList(Stainfo stainfo) {
		return super.findList( new Stainfo());
	}
	/**
	 * 查询工位信息
	 */	
//	public Page<Stainfo> findPage(Page<Stainfo> page, Stainfo stainfo) {
//		return super.findPage(page, stainfo);
//	}
	/**
	 * 添加或修改工位信息及工位对应的工装
	 */	
	@Transactional(readOnly = false)
	public void save(Stainfo stainfo) {
		List<StaFrock> stainfoList = stainfo.getStaFrockList();
		if(stainfoList.size()>0){
			dao.deleteStaFrock(stainfo);
			for (StaFrock staFrock:stainfoList) {
				staFrock.preInsert();
				dao.insertStaFrock(staFrock);
			}
			
		}
		
		super.save(stainfo);
	}
	/**
	 * 删除工位信息
	 */	
	@Transactional(readOnly = false)
	public void delete(String[] ids) {
		   for (int i = 0; i < ids.length; i++) {
			   Stainfo stainfo = new Stainfo(ids[i]);
		    	super.delete(stainfo);
		    }	
	}
	
	/**
	 * 查询仓库列表
	 */
	public List<Stordoc> findStordocList(Stordoc stordoc){
		return dao.findStordocList(stordoc);
	}

//	public List<Office> findOfficeList(){
//		return dao.findOfficeList();
//	}	
	/**
	 * 查询仓库人员列表
	 */
//	public List<User> findUserList(){
//		return dao.findUserList();
//	}
	/**
	 * 查询工作中心列表
	 */
	public List<Wk> findWkList(Wk wks) {
		
		return dao.findWkList(wks);
	}
	/**
	 * 查询库管员列表（局部刷新）
	 */
	public List<User> findStorList(String value) {
		return dao.findStorList(value);
	}



	public int updateStorekeeper(Stainfo stainfo) {
		return dao.updateStorekeeper(stainfo);
		
	}


	/**  
	 * 函数功能说明 :根据工位管理ID查找匹配规则及未选择对应工装车
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param id
	 * @参数： @return
	 * @throws     
	 */
	public List<StaFrock> getNoChooseFrock(String id) {
		// TODO Auto-generated method stub
		return dao.getNoChooseFrock(id);
	}


	/**  
	 * 函数功能说明 :根据工位管理ID查找匹配规则及选择对应工装车
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param id
	 * @参数： @return
	 * @throws     
	 */
	public List<String> getChooseFrock(String id) {
		// TODO Auto-generated method stub
		return dao.getChooseFrock(id);
	}


	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @return
	 * @throws     
	 */
	public List<StaFrock> getAllFrock() {
		// TODO Auto-generated method stub
		return dao.getAllFrock();
	}


	/**
	 * 函数功能说明 :获取仓库管理员集合
	 * 作者: fangzr  
	 * 创建时间：2015-12-9
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @return
	 * @throws
	 */
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


	/**
	 * 函数功能说明 :获取当前仓库的所有货位
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param storDocId 项目仓库
	 * @参数： @param wkStocId 工位
	 * @参数： @return
	 * @throws
	 */
	public List<CargDoc> getAllCargDocTree(String storDocId, String wkStocId) {
		List<CargDoc> cargDocList = Lists.newArrayList();
		List<CargDoc> result = new ArrayList<CargDoc>();

		Map<String,Object> params = Maps.newHashMap();
		params.put("storDocId", storDocId);
		params.put("wkStocId", wkStocId);
		cargDocList = dao.getAllCargDocTree(params);			
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
	 * 函数功能说明 :获取当前工位、货架已选货位
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param wkStocId
	 * @参数： @param storDocId
	 * @参数： @return
	 * @throws     
	 */
	public List<String> getCargDocIdList(String wkStocId, String storDocId) {
		StaCarg staCarg = new StaCarg();
		staCarg.setPkCargdoc(storDocId);
		staCarg.setPkStamanfil(wkStocId);
		return dao.getCargDocIdList(staCarg);
	}


	/**  
	 * 函数功能说明 :保存工位管理档案ID和货位的关系
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param staCargList
	 * @throws     
	 */
	@Transactional(readOnly = false)
	public void saveStaCargMenu(List<StaCarg> staCargList, String wkStocId) {
		StaCarg staCargDel =  new StaCarg();
		staCargDel.setPkStamanfil(wkStocId);
		dao.deleteStaCargMenu(staCargDel);
		if(staCargList.size()>0){		
			for (StaCarg staCarg : staCargList) {
				staCarg.preInsert();
				dao.saveStaCargMenu(staCarg);
			}
			
		}		
	}
	

}

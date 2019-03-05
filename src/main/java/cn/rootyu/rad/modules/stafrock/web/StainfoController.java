/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.*;
import cn.rootyu.rad.modules.stafrock.service.StainfoService;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrock;
import cn.rootyu.rad.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工位档案管理Controller
 * @author chunze.cao
 * @version 2015-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/stainfo/sta")
public class StainfoController extends BaseController {
	
	@Autowired
	private StainfoService stainfoService;
	
	@ModelAttribute
	public Stainfo get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return stainfoService.get(id);
		}else{
			return new Stainfo();
		}
	}
	
	@RequiresPermissions("stafrock:stainfo:view")	
	@RequestMapping(value = "form")
	public String form(Stainfo stainfo, Model model) {
		
		
		if (stainfo == null) {
			stainfo = new Stainfo();
		}
		model.addAttribute("userlist",stainfoService.getStorUserList());
		model.addAttribute("findStorList",stainfoService.findStordocList(new Stordoc()));//项目库
		model.addAttribute("wkList",stainfoService.findWkList(new Wk()));//工位
		model.addAttribute("stainfo", stainfo);
		return "modules/stafrock/stainfoForm";
	}
	/**
	 * 函数功能说明 :根据工位管理ID查找匹配规则及选择对应工装车
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param id
	 * @参数： @param model
	 * @参数： @return
	 * @throws
	 */
	@RequestMapping(value = "text")
	public String text(Stainfo stainfo, Model model) {
		List<StaFrock> noChooseFrockList = stainfoService.getNoChooseFrock(stainfo.getId());
		List<String> staFrockIdList = stainfoService.getChooseFrock(stainfo.getId());

		stainfo.setStaFrockIdList(staFrockIdList);
		model.addAttribute("stainfo",stainfo);
		model.addAttribute("noChooseFrockList",noChooseFrockList);
		
		return "modules/stafrock/stainfo";
	}
	/**
	 * 查询工位信息
	 */		
	@RequiresPermissions("stafrock:stainfo:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Stainfo stainfo, HttpServletRequest request, HttpServletResponse response) {
		Page<Stainfo> page = stainfoService.findPage(new Page<Stainfo>(request, response),stainfo);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
		return returnMap;
	}	
	
	/**
	 * 查询工位信息
	 */	
	@RequiresPermissions("stafrock:stainfo:view")	
	@RequestMapping(value = {"list", ""})
	public String list(Stainfo stainfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("stordocList",stainfoService.findStordocList(new Stordoc()));//项目库
		model.addAttribute("wkList",stainfoService.findWkList(new Wk()));//工位
		model.addAttribute("userlist",stainfoService.getStorUserList());
		return "modules/stafrock/stainfoList";
	}
	
	/**
	 * 添加或修改工位信息
	 */	
	@RequiresPermissions("stafrock:stainfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public  Map<String,Object> save(Stainfo stainfo, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else if (!beanValidatorAjax(returnMap,stainfo)){
			return returnMap;
		}else{
			
			List<StaFrock> staFrockList = Lists.newArrayList();
			List<String> staFrockIdList = stainfo.getStaFrockIdList();
			for (StaFrock r : stainfoService.getAllFrock()){
				if (staFrockIdList.contains(r.getId())){
					r.setStationId(stainfo.getId());
					r.setFrockId(r.getId());
					staFrockList.add(r);
				}
			}
			stainfo.setStaFrockList(staFrockList);
			if(stainfo.getIsNewRecord()){//插入操作
				stainfo.setCalbodyid(Stainfo.ERP_KCZZ_ID);
				stainfo.setPkCorp(Stainfo.ERP_CORP_ID);
			}
			stainfoService.save(stainfo);
			addMessageAjax(returnMap,"1","保存工位成功");
		}
		return returnMap;
	}	
	/**
	 * 删除工位信息
	 */	
	
	@RequiresPermissions("stafrock:stainfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(String ids[], RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			stainfoService.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
	}
	/**
	 *修改项目仓库库管员
	 */
	@RequestMapping(value = "updateUsername")
	@ResponseBody
	public Map<String,Object> updateStorekeeper(String name,String pk,String value,RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		Stainfo stainfo = new Stainfo(pk);
		stainfo.setPkStorekeeper(value);
		stainfoService.updateStorekeeper(stainfo);
		addMessageAjax(returnMap,"1","更新项目仓库库管员成功!'");
		return returnMap;
	}

	/**
	 * 查询仓库列表
	 */
	@RequiresPermissions("stafrock:stainfo:view")
	@ResponseBody
	@RequestMapping(value = "findStordocList")
	public List<Stordoc> findStordocList(){
		return stainfoService.findStordocList(new Stordoc());
	}

//	@RequiresPermissions("stafrock:stainfo:view")
//	@ResponseBody
//	@RequestMapping(value = "findOfficeList")
//	public List<Office> findOfficeList(){
//		return stainfoService.findOfficeList();
//	}	
	/**
	 * 查询仓库人员列表
	 */
//	@RequiresPermissions("stafrock:stainfo:view")
//	@ResponseBody
//	@RequestMapping(value = "findUserList")
//	public List<User> findUserList(){
//
//		return stainfoService.findUserList();
//	}
	
	/**
	 * 查询工作中心列表
	 */
	@RequiresPermissions("stafrock:stainfo:view")
	@ResponseBody
	@RequestMapping(value = "findWkList")
	public List<Wk> findWkList(){
		return stainfoService.findWkList(new Wk());
	}
	
	/**
	 * 查询库管员列表
	 */
	@RequiresPermissions("stafrock:stainfo:view")
	@ResponseBody
	@RequestMapping(value = "findStorList")
	public List<User> findStorList(String value){
		return stainfoService.findStorList(value);
	}
	/**
	 * 函数功能说明 :获取货位树
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param wkStocId 工位管理ID
	 * @参数： @param storDocId 项目仓库ID
	 * @参数： @param response
	 * @参数： @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "getCargDoctree")
	public Map<String, Object> getCargDoctree(String wkStocId,String storDocId,  HttpServletResponse response) {
		Map<String,Object> result = Maps.newHashMap();
		List<CargDoc> list = Lists.newArrayList();
		list = stainfoService.getAllCargDocTree(storDocId,wkStocId);
		List<String> selectList = Lists.newArrayList();
		selectList = stainfoService.getCargDocIdList(wkStocId,storDocId);
		result.put("data", convert2Tree(list,selectList));
		result.put("selectList", selectList);
		return result;
	}
	private List<Map<String, Object>> convert2Tree(List<CargDoc> list, List<String> selectList) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i=0; i<list.size(); i++){
			CargDoc e = list.get(i);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", e.getId());
			map.put("text", e.getName());

			if(selectList.size()>0&&selectList.contains(e.getId())){
				Map<String,Boolean> checked = Maps.newHashMap();
				checked.put("checked", true);
				//checked.put("selected", false);
				map.put("state",checked);
			}
			map.put("selectable", false);
			List<Map<String, Object>> nodes = convert2Tree(e.getSubCargDoc(),selectList);
			if(nodes.size()>0){
				map.put("nodes",nodes);
			}		
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 
	 * 函数功能说明 :保存工位管理档案ID和货位的关系
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param reservoirId
	 * @参数： @param storDocId
	 * @参数： @param menus
	 * @参数： @param redirectAttributes
	 * @参数： @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "saveStaCargMenus")
	public Map<String,Object> saveStaCargMenus(String wkStocId,String menus, RedirectAttributes redirectAttributes) {
		
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			
			String[] menuArray = menus.split(",");
			List<StaCarg> staCargList = Lists.newArrayList();
			if(menuArray.length!=0){
				for(String menu:menuArray){

					if(!menu.equals("")){
						StaCarg staCarg = new StaCarg();
						staCarg.setPkCargdoc(menu);
						staCarg.setPkStamanfil(wkStocId);
						staCargList.add(staCarg);
					}
				}
			}
			stainfoService.saveStaCargMenu(staCargList,wkStocId);
			addMessageAjax(returnMap,"1", "分配货位成功");
		}
		return returnMap;
	}
}

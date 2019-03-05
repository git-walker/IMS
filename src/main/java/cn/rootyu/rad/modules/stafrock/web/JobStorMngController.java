package cn.rootyu.rad.modules.stafrock.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.CargDoc;
import cn.rootyu.rad.modules.stafrock.entity.JobStorMng;
import cn.rootyu.rad.modules.stafrock.entity.StorCarg;
import cn.rootyu.rad.modules.stafrock.service.JobStorMngService;
import cn.rootyu.rad.modules.sys.entity.Office;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
 * 项目仓库管理Controller
 * @author jinkf
 * @version 2015-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/project/jobstormng")
public class JobStorMngController extends BaseController {
	
	@Autowired
	private JobStorMngService jobstormngservice;
	
	@ModelAttribute
	public JobStorMng get(@RequestParam(required=false) String id){
		if(StringUtils.isNotBlank(id)){
			return jobstormngservice.get(id);
		}else{
			return new JobStorMng();
		}
	}
	
	@RequestMapping(value = "form")
	public String form(JobStorMng jobstormng, Model model) {
		model.addAttribute("jobstormng", jobstormng);
		model.addAttribute("storeRoomList",jobstormngservice.getStoreRoomList(new Office()));
		return "modules/stafrock/jobStorMngForm";
	}
	
	@RequestMapping(value = "text")
	public String text(JobStorMng jobstormng, Model model) {
		model.addAttribute("jobstormng", jobstormng);
		return "modules/stafrock/jobStorMng";
	}	
	/**
	 * 查询项目仓库信息
	 * @author chunze.cao
	 * @version 2015-10-30
	 */
	@RequestMapping(value={"list",""})
	public String list(JobStorMng jobstormng, HttpServletRequest request, HttpServletResponse response, Model model){
//		Page<JobStorMng> page=jobstormngservice.findPage(new Page<JobStorMng>(request,response), jobstormng);
//		model.addAttribute("page",page);
		model.addAttribute("vehicleModelList",jobstormngservice.getVehiclemodelidList(new JobStorMng()));

		model.addAttribute("storeRoomList",jobstormngservice.getStoreRoomList(new Office()));
		return "modules/stafrock/jobStorMngList";
		
	}
	
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(JobStorMng jobstormng, HttpServletRequest request, HttpServletResponse response) {
		jobstormng.setSealflag(JobStorMng.SEAL_FLAG_N);
        Page<JobStorMng> page = jobstormngservice.findPage(new Page<JobStorMng>(request, response),jobstormng);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}	
	
	/**
	 *修改项目仓库所属库区
	 */
	@RequestMapping(value = "updateStoreroom")
	@ResponseBody
	public Map<String,Object> updateStoreroom(String name,String pk,String value,RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		JobStorMng jobStorMng = new JobStorMng(pk);
		jobStorMng.setPkReservoirid(value);
		jobstormngservice.updateStoreroom(jobStorMng);
		addMessageAjax(returnMap,"1","保存所属库区成功!'");
		return returnMap;
	}
	@RequestMapping(value = "updateVehiclemodelid")
	@ResponseBody
	public Map<String,Object> updateVehiclemodelid(String name,String pk,String value,RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		JobStorMng jobStorMng = new JobStorMng(pk);
		jobStorMng.setVehiclemodelid(value);
		jobstormngservice.updateVehiclemodelid(jobStorMng);
		addMessageAjax(returnMap,"1","保存工装车型成功!'");
		return returnMap;
	}
	
	/**
	 * 保存项目仓库信息
	 * @author chunze.cao
	 * @version 2015-10-30
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public  Map<String,Object> save(JobStorMng jobstormng, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
//			return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		}else if (!beanValidatorAjax(returnMap,jobstormng)){
	//		return form(dict, model);
			break out;	 
		}else{	
			jobstormngservice.save(jobstormng);
			addMessageAjax(returnMap,"1","保存成功");
		}
//		return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		return returnMap;
	}
	/**
	 * 删除项目仓库信息
	 * @author chunze.cao
	 * @version 2015-10-30
	 */	
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(String ids[], RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/dict/?repage";
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			jobstormngservice.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
//		return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getStorDoctree")
	public Map<String, Object> getStorDoctree(String reservoirId,String storDocId,  HttpServletResponse response) {
		Map<String,Object> result = Maps.newHashMap();
		Map<String,Object> params = Maps.newHashMap();
		params.put("reservoirId", reservoirId);
		params.put("storDocId", storDocId);
		List<CargDoc> list = jobstormngservice.getAllStorDocTree(params);
		List<String> selectList = jobstormngservice.getStorDocIdList(reservoirId,storDocId);
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

			if(selectList.contains(e.getId())){
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
	
	
	@ResponseBody
	@RequestMapping(value = "saveStorDocMenus")
	public Map<String,Object> saveStorDocMenus(String reservoirId,String storDocId,String menus, RedirectAttributes redirectAttributes) {
		
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			
			String[] menuArray = menus.split(",");
			List<StorCarg> storCargList = Lists.newArrayList();
			if(menuArray.length!=0){
				for(String menu:menuArray){

					if(!menu.equals("")){
						StorCarg cargDoc = new StorCarg();
						cargDoc.setPkCargcol(menu);
						cargDoc.setPkStordoc(storDocId);
						storCargList.add(cargDoc);
					}
				}
			}
			jobstormngservice.saveStorDocMenu(storCargList,storDocId);
			addMessageAjax(returnMap,"1", "分配货架列成功");
		}
		return returnMap;
	}
}	
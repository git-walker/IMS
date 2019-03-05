/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.Stainfo;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrock;
import cn.rootyu.rad.modules.stafrocktype.service.StaFrockService;
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
import java.util.Map;

/**
 * 定制工装与工位维护Controller
 * @author wangzhixin
 * @version 2015-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/stafrock")
public class StaFrockController extends BaseController {

	@Autowired
	private StaFrockService staFrockService;
	
	@ModelAttribute
	public StaFrock get(@RequestParam(required=false) String id) {
		StaFrock entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = staFrockService.get(id);
		}
		if (entity == null){
			entity = new StaFrock();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(StaFrock staFrock, HttpServletRequest request, HttpServletResponse response, Model model) {
	//	Page<StaFrock> page = staFrockService.findPage(new Page<StaFrock>(request, response), staFrock); 
		//model.addAttribute("page", page);
		return "modules/stafrocktype/stafrockList";
	}

	@RequestMapping(value = "form")
	public String form(String workStationId,String workStationCode, Model model) {
		StaFrock staFrock = new StaFrock();
		staFrock.setStationId(workStationId);
		Stainfo stainfo = new Stainfo();
		stainfo.setWorkstationcode(workStationCode);
//		staFrock.setStainfo(stainfo);
		model.addAttribute("staFrock", staFrock);
		return "modules/stafrocktype/stafrockForm";
	}

	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(String[] matchFrock,String workStationId, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
		}else if (matchFrock.length<=0){
			addMessageAjax(returnMap,"0","请选择至少一个匹配工装");
			break out;	 
		}else{	
			staFrockService.batchSave(matchFrock,workStationId);
			addMessageAjax(returnMap,"1","保存定制工装与工位成功");
		}
		return returnMap;
	}
	
	@RequestMapping(value = "delete")
	public String delete(StaFrock staFrock, RedirectAttributes redirectAttributes) {
		staFrockService.delete(staFrock);
		addMessage(redirectAttributes, "删除定制工装与工位维护成功");
		return "redirect:"+ Global.getAdminPath()+"/stafrock/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "getNotMatchFrockList")
	public Map<String,Object>  getMatchFrockList(String workStationId){
		Map<String,Object> map = Maps.newHashMap();
		map.put("notmatch", staFrockService.getNotMatchFrockList(workStationId));
		map.put("match", staFrockService.getMatchFrockList(workStationId));
		return map;
	}
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(StaFrock staFrock, HttpServletRequest request, HttpServletResponse response) {
        Page<StaFrock> page = staFrockService.findPage(new Page<StaFrock>(request, response),staFrock);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap; 
}}
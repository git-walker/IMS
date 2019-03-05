/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.web;


import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrockType;
import cn.rootyu.rad.modules.stafrocktype.service.StaFrockTypeService;
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
 * 工位档案管理Controller
 * @author DHC
 * @version 2015-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/stafrocktype")
public class StaFrockTypeController extends BaseController {
	
	@Autowired
	private StaFrockTypeService staFrockTypeService;
	
	@ModelAttribute
	public StaFrockType get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return staFrockTypeService.get(id);
		}else{
			return new StaFrockType();
		}
	}
	/**
	 * 显示列表页
	 * @param test
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(StaFrockType staFrockType, HttpServletRequest request, HttpServletResponse response, Model model) {
//		 Page<StaFrockType> page = staFrockTypeService.findPage(new Page<StaFrockType>(request, response), staFrockType); 
//	        model.addAttribute("page", page);
		return "modules/stafrocktype/stafrocktypeList";
	}
	/**
	 * 表单保存方法
	 * @param test
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(StaFrockType staFrockType, Model model) {
		model.addAttribute("staFrockType", staFrockType);
		return "modules/stafrocktype/stafrocktypeForm";
	}
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(StaFrockType staFrockType, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
//			return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		}else if (!beanValidatorAjax(returnMap,staFrockType)){
	//		return form(dict, model);
			break out;	 
		}else{	
			staFrockTypeService.save(staFrockType);
			addMessageAjax(returnMap,"1","保存字典'" + staFrockType.getStationid() + "'成功");
		}
//		return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		return returnMap;
	}
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(StaFrockType staFrockType, HttpServletRequest request, HttpServletResponse response) {
        Page<StaFrockType> page = staFrockTypeService.findPage(new Page<StaFrockType>(request, response),staFrockType);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{

			staFrockTypeService.delete(ids);
			addMessageAjax(returnMap,"1", "删除字典成功");
		}
		return returnMap;
	}
}
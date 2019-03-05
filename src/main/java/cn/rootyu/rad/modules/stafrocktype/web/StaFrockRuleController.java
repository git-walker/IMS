/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrocktype.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrockRule;
import cn.rootyu.rad.modules.stafrocktype.service.StaFrockRuleService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 定制工装与工位规则维护Controller
 * @author zhixin.wang
 * @version 2015-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/stafrockrule")
public class StaFrockRuleController extends BaseController {

	@Autowired
	private StaFrockRuleService staFrockRuleService;
	
	@RequestMapping(value = {"list", ""})
	public String list(StaFrockRule staFrockRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/stafrocktype/stafrockruleList";
	}

	@RequestMapping(value = "form")
	public String form(@RequestParam(required=true) String station, Model model) {
		StaFrockRule staFrockRule = staFrockRuleService.getStaFrockRuleByStationId(station);
		if (staFrockRule == null) {
			staFrockRule = new StaFrockRule();
		}
		model.addAttribute("staFrockRule", staFrockRule);
		return "modules/stafrocktype/stafrockruleForm";
	}
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(StaFrockRule staFrockRule, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
		}else if (!beanValidatorAjax(returnMap,staFrockRule)){
			break out;	 
		}else{	
			staFrockRuleService.save(staFrockRule);
			addMessageAjax(returnMap,"1","定制工装与工位规则");
		}
		return returnMap;
	}
	@RequestMapping(value = "delete")
	public String delete(StaFrockRule staFrockRule, RedirectAttributes redirectAttributes) {
		staFrockRuleService.delete(staFrockRule);
		addMessage(redirectAttributes, "删除定制工装与工位规则维护成功");
		return "redirect:"+ Global.getAdminPath()+"/stafrockrule/list";
	}

	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(StaFrockRule staFrockRule, HttpServletRequest request, HttpServletResponse response) {
        Page<StaFrockRule> page = staFrockRuleService.findPage(new Page<StaFrockRule>(request, response),staFrockRule);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap; 

	}
}
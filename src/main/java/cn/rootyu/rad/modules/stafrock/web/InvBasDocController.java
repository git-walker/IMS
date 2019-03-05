/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.web;


import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import cn.rootyu.rad.modules.stafrock.service.InvBasDocService;
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
import java.util.Map;

/**
 * 配送策划管理Controller
 * @author chunze.cao
 * @version 2015-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/stafrock/invBasDoc")
public class InvBasDocController extends BaseController {

	@Autowired
	private InvBasDocService invBasDocService;
	
	@ModelAttribute
	public InvBasDoc get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return invBasDocService.get(id);
		}else{
			return new InvBasDoc();
		}
	}
	
	
	
	@RequiresPermissions("stafrock:stainfo:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(InvBasDoc invBasDoc, HttpServletRequest request, HttpServletResponse response) {

        Page<InvBasDoc> page = invBasDocService.findPage(new Page<InvBasDoc>(request, response),invBasDoc);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}	
	/**
	 * 查询存货信息
	 */		
	@RequiresPermissions("stafrock:invbasdoc:view")	
	@RequestMapping(value = {"list", ""})
	public String list(InvBasDoc invBasDoc, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<InvBasDoc> page = invBasDocService.findPage(new Page<InvBasDoc>(request, response), invBasDoc); 
//		model.addAttribute("page", page);
		return "modules/stafrock/invBasDocList";
	}
	/**
	 * 根据ID查询存货信息
	 */		
	@RequiresPermissions("stafrock:invbasdoc:view")	
	@RequestMapping(value = "form")
	public String form(InvBasDoc invBasDoc, Model model) {
		model.addAttribute("invBasDoc", invBasDoc);
		return "modules/stafrock/invBasDocForm";
	}	
	/**
	 * 保存或修改存货信息
	 */			
	
	@RequiresPermissions("stafrock:invbasdoc:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public  Map<String,Object> save(InvBasDoc invBasDoc, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
//			return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		}else if (!beanValidatorAjax(returnMap,invBasDoc)){
	//		return form(dict, model);
			break out;	 
		}else{	
			invBasDocService.save(invBasDoc);
			addMessageAjax(returnMap,"1","匹配成功");
		}
//		return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		return returnMap;
	}	
	/**
	 * 删除存货信息
	 */		
	@RequiresPermissions("stafrock:invbasdoc:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(String ids[], RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");

		}else{
			invBasDocService.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
	}
	
}
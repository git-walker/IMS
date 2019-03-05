/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rootyu.ims.basisData.entity.Wkk;
import cn.rootyu.ims.basisData.service.WkService;
import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.jqgridSearch.JqGridHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;

/**
 * Wk Controller
 * @author DHC
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/qmis/wk")
public class WkController extends BaseController {

	@Autowired
	private WkService wkService;
	
	@ModelAttribute
	public Wkk get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return wkService.get(id);
		}else{
			return new Wkk();
		}
	}
	
	//@RequiresPermissions("ims:wk:view")
	@RequestMapping(value = {"list", ""})
	public String list(Wkk wk, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Wkk> typeList = wkService.findTypeList();
		model.addAttribute("typeList", typeList);
		return "qmis/basisData/WkList";
	}
	
	//@RequiresPermissions("ims:wk:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Wkk wk,HttpServletRequest request, HttpServletResponse response) {
		String where = new JqGridHandler(request).getWheres(null, true);
		System.out.println("sql:　　　　　　"+where);
        Page<Wkk> page = wkService.findPage(new Page<Wkk>(request, response),wk);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}
	
	//@RequiresPermissions("ims:wk:view")
	@RequestMapping(value = "form")
	public String form(Wkk wk, Model model) {
		List<Wkk> flidList = wkService.findTypeList();
		model.addAttribute("flidList", flidList);
		model.addAttribute("wkk", wk);
		return "qmis/basisData/WkForm";
	}
	
	
	//@RequiresPermissions("ims:wk:edit")
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(Wkk wk, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else if (beanValidatorAjax(returnMap,wk)){
			wkService.save(wk);
			addMessageAjax(returnMap,"1","保存工位'" + wk.getGzzxmc() + "'成功");
		}
		return returnMap;
	}
	
	//@RequiresPermissions("ims:wk:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(Wkk wk, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			wkService.delete(wk);
			addMessageAjax(returnMap,"1", "删除工位成功");
		}
		return returnMap;
	}

	/**
	 * 函数功能说明 :批量删除字典
	 * 作者: fangzr  
	 * 创建时间：2015-11-3
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param ids
	 * @参数： @param redirectAttributes
	 * @参数： @return
	 * @throws
	 */
	//@RequiresPermissions("ims:wk:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public  Map<String,Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{

			wkService.batchDelete(ids);
			addMessageAjax(returnMap,"1", "删除工位成功");
		}
		return returnMap;
	}

}

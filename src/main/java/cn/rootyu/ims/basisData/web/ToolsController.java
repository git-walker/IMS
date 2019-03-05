package cn.rootyu.ims.basisData.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rootyu.ims.basisData.service.ToolsService;
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

import cn.rootyu.ims.basisData.entity.ToolsBean;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;
@Controller
@RequestMapping(value = "${adminPath}/qmis/tools")
public class ToolsController extends BaseController {
	@Autowired
	//自动装载
	private ToolsService toolService;
	@ModelAttribute
	public ToolsBean get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return toolService.get(id);
		}else{
			return new ToolsBean();
		}
	}
	
	//@RequiresPermissions("ims:tools:view")
	@RequestMapping(value = {"list", ""})
	public String list(ToolsBean toolsBean, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = toolService.findTypeList();
		model.addAttribute("typeList", typeList);	
		return "qmis/basisData/ToolsList";
	}
	
	//@RequiresPermissions("ims:tools:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(ToolsBean tools,HttpServletRequest request, HttpServletResponse response) {
		String where = new JqGridHandler(request).getWheres(null, true);
		System.out.println("sql:　　　　　　"+where);
        Page<ToolsBean> page = toolService.findPage(new Page<ToolsBean>(request, response),tools);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}
	
	//@RequiresPermissions("ims:tools:view")
	@RequestMapping(value = "form")
	public String form(ToolsBean tools, Model model) {
		model.addAttribute("tools", tools);
		List<String> typeNewList = toolService.findTypeList();
		model.addAttribute("typeNewList", typeNewList);	
		return "qmis/basisData/ToolsForm";
	}
	
	@RequestMapping(value = "detail")
	public String detail(ToolsBean tools, Model model) {
		model.addAttribute("tools", tools);
		List<String> typeNewList = toolService.findTypeList();
		model.addAttribute("typeNewList", typeNewList);	
		return "qmis/basisData/ToolsDetail";
	}
	
	//@RequiresPermissions("ims:tools:edit")
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(ToolsBean tools, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else if (beanValidatorAjax(returnMap,tools)){
			toolService.save(tools);
			addMessageAjax(returnMap,"1","保存工具'" + tools.getToolsName() + "'成功");
		}
		return returnMap;
	}
	
	//@RequiresPermissions("ims:tools:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(ToolsBean tools, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			toolService.delete(tools);	
			addMessageAjax(returnMap,"1", "删除工具成功");
		}
		return returnMap;
	}
	
	//@RequiresPermissions("ims:tools:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public  Map<String,Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			toolService.batchDelete(ids);
			addMessageAjax(returnMap,"1", "删除工具成功");
		}
		return returnMap;
	}
	
}

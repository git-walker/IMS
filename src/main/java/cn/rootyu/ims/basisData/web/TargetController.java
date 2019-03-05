/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rootyu.ims.basisData.entity.Target;
import cn.rootyu.ims.basisData.service.TargetService;
import cn.rootyu.rad.common.persistence.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import cn.rootyu.ims.common.dao.CodeValueDao;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;

/**
 * 成本对象 维护 Controller
 * @author DHC
 * @version 2016-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/qmis/target")
public class TargetController extends BaseController {

	@Autowired
	private TargetService targetService;
	@Autowired
    private CodeValueDao codeValueDao;
	
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Target target, HttpServletRequest request, HttpServletResponse response) {
		Page<Target> page = targetService.findPage(new Page<Target>(request, response),target);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;                           
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Target target, HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("projectList", codeValueDao.findProjectList(null));
		return  "qmis/basisData/TargetList";
	}

	@RequestMapping(value = "form")
	public String form(Target target, Model model) {
		model.addAttribute("projectList", codeValueDao.findProjectList(null));
		Target bean = new Target();
		if (StringUtils.isNotBlank(target.getId())){
			bean=targetService.get(target);
		}
		model.addAttribute("targetEntity",bean);
		return "qmis/basisData/TargetForm";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public  Map<String,Object> save(Target target, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		targetService.save(target);
		addMessageAjax(returnMap,"1","保存成本对象成功");
		return returnMap;
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(Target target,RedirectAttributes redirectAttributes){
		Map<String,Object> returnMap = Maps.newHashMap();
		targetService.delete(target);
		addMessageAjax(returnMap,"1", "删除成本对象成功");
		return returnMap;

	}
	
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public  Map<String,Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		targetService.batchDelete(ids);
		addMessageAjax(returnMap,"1", "删除成功");
		return returnMap;
	}
}

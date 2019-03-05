/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.AllocateTask;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.stafrock.entity.Wk;
import cn.rootyu.rad.modules.stafrock.service.AllocateTaskService;
import cn.rootyu.rad.modules.sys.entity.User;
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
 * 盘库管理Controller
 * @author zhixin.wang
 * @version 2015-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/stafrock/allocatetask")
public class AllocateTaskController extends BaseController {
	
	@Autowired
	private AllocateTaskService allocateTaskService;
	
	@ModelAttribute
	public AllocateTask get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return allocateTaskService.get(id);
		}else{
			return new AllocateTask();
			}
		}
	@RequestMapping(value = {"list", ""})
	public String list(AllocateTask allocatetask, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("stordocList",allocateTaskService.findStordocList(new Stordoc()));
		model.addAttribute("wkList",allocateTaskService.findWkList(new Wk()));//工位
		model.addAttribute("userlist",allocateTaskService.getStorUserList());
		model.addAttribute("invbasdocList",allocateTaskService.getInvBasDocList(new InvBasDoc()));
		return "modules/stafrock/allocatetaskList";
	}
	@RequestMapping(value = "form")
	public String form(AllocateTask allocatetask, Model model) {
		if (allocatetask == null) {
			allocatetask = new AllocateTask();
		}
		model.addAttribute("userlist",allocateTaskService.getStorUserList());
		model.addAttribute("stordocList",allocateTaskService.findStordocList(new Stordoc()));//项目库
		model.addAttribute("wkList",allocateTaskService.findWkList(new Wk()));//工位
		model.addAttribute("invbasdocList",allocateTaskService.getInvBasDocList(new InvBasDoc()));
		model.addAttribute("allocatetask", allocatetask);
		return "modules/stafrock/allocatetaskForm";
	}
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(AllocateTask allocatetask, HttpServletRequest request, HttpServletResponse response) {
		Page<AllocateTask> page = allocateTaskService.findPage(new Page<AllocateTask>(request, response),allocatetask);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
		return returnMap;
	}
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(AllocateTask allocatetask, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
		}else if (!beanValidatorAjax(returnMap,allocatetask)){
			break out;	 
		}else{	
			allocateTaskService.save(allocatetask);
			addMessageAjax(returnMap,"1","保存字典'" + allocatetask.getId() + "'成功");
		}
		return returnMap;
	}
	/**
	 * 删除工位信息
	 */	
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(String ids[], RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			allocateTaskService.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "getInvBasDocList")
	public List<InvBasDoc> getInvBasDocList(){
		return allocateTaskService.getInvBasDocList(new InvBasDoc());
	
	}
	@ResponseBody
	@RequestMapping(value = "findStordocList")
	public List<Stordoc> findStordocList(){
		return allocateTaskService.findStordocList(new Stordoc());
	}
	/**
	 * 二级select
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findAllocateStoridList")
	public List<Wk> findAllocateStoridList(String allocStorid){
		Wk wk = new Wk();
		wk.setAllocStorid(allocStorid);
		return allocateTaskService.findAllocateStoridList(wk );
	}
	@ResponseBody
	@RequestMapping(value = "findAllocatedStoridList")
	public List<Wk> findAllocatedStoridList(String allocedStorid){
		Wk wked = new Wk();
		wked.setAllocedStorid(allocedStorid);
		return allocateTaskService.findAllocatedStoridList(wked);
	}
	@ResponseBody
	@RequestMapping(value = "findStorList")
	public List<User> findStorList(String value){
		return allocateTaskService.findStorList(value);
	}
	}
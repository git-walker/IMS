/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.BorrowTask;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.stafrock.entity.Wk;
import cn.rootyu.rad.modules.stafrock.service.BorrowTaskService;
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
 * 借料管理Controller
 * @author zhixin.wang
 * @version 2016-1-4
 */
@Controller
@RequestMapping(value = "${adminPath}/stafrock/borrowtask")
public class BorrowTaskController extends BaseController {
	
	@Autowired
	private BorrowTaskService borrowTaskService;
	
	@ModelAttribute
	public BorrowTask get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return borrowTaskService.get(id);
		}else{
			return new BorrowTask();
			}
		}
	@RequestMapping(value = {"list", ""})
	public String list(BorrowTask borrowtask, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("stordocList",borrowTaskService.findStordocList(new Stordoc()));
		model.addAttribute("invbasdocList",borrowTaskService.getInvBasDocList(new InvBasDoc()));
		model.addAttribute("wkList",borrowTaskService.findWkList(new Wk()));//工位
		model.addAttribute("userlist",borrowTaskService.getBorrowUserList());
		return "modules/stafrock/borrowtaskList";
	}
	@RequestMapping(value = "form")
	public String form(BorrowTask brrowtask, Model model) {
		if (brrowtask == null) {
			brrowtask = new BorrowTask();
		}
		model.addAttribute("stordocList",borrowTaskService.findStordocList(new Stordoc()));
		model.addAttribute("invbasdocList",borrowTaskService.getInvBasDocList(new InvBasDoc()));
		model.addAttribute("wkList",borrowTaskService.findWkList(new Wk()));//工位
		model.addAttribute("userlist",borrowTaskService.getBorrowUserList());
		model.addAttribute("borrowtask", brrowtask);
		return "modules/stafrock/borrowtaskForm";
	}
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(BorrowTask borrowtask, HttpServletRequest request, HttpServletResponse response) {
		Page<BorrowTask> page = borrowTaskService.findPage(new Page<BorrowTask>(request, response),borrowtask);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
		return returnMap;
	}
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(BorrowTask borrowtask, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
		}else if (!beanValidatorAjax(returnMap,borrowtask)){
			break out;	 
		}else{	
			borrowTaskService.save(borrowtask);
			addMessageAjax(returnMap,"1","保存字典'" + borrowtask.getId()+ "'成功");
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
			borrowTaskService.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
	}
	@ResponseBody
	@RequestMapping(value = "getInvBasDocList")
	public List<InvBasDoc> getInvBasDocList(){
		return borrowTaskService.getInvBasDocList(new InvBasDoc());
	
	}
	@ResponseBody
	@RequestMapping(value = "findStordocList")
	public List<Stordoc> findStordocList(){
		return borrowTaskService.findStordocList(new Stordoc());
	}
	/**
	 * 二级select
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findborrowStoridList")
	public List<Wk> findBorrowStoridList(String borrowStorid){
		Wk wk = new Wk();
		wk.setBorrowStorid(borrowStorid);
		return borrowTaskService.findBorrowStoridList(wk );
	}
	@ResponseBody
	@RequestMapping(value = "findborrowedStoridList")
	public List<Wk> findBorrowedStoridList(String borrowedStorid){
		Wk wked = new Wk();
		wked.setBorrowedStorid(borrowedStorid);
		return borrowTaskService.findBorrowedStoridList(wked);
	}
	@ResponseBody
	@RequestMapping(value = "findStorList")
	public List<User> findStorList(String value){
		return borrowTaskService.findStorList(value);
	}
}
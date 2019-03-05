/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.InvBasDoc;
import cn.rootyu.rad.modules.stafrock.entity.StocktakingTask;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.stafrock.entity.Wk;
import cn.rootyu.rad.modules.stafrock.service.StocktakingTaskService;
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
@RequestMapping(value = "${adminPath}/stafrock/stocktakingtask")
public class StocktakingTaskController extends BaseController {
	
	@Autowired
	private StocktakingTaskService stocktakingTaskService;
	
	@ModelAttribute
	public StocktakingTask get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return stocktakingTaskService.get(id);
		}else{
			return new StocktakingTask();
			}
		}
	@RequestMapping(value = {"list", ""})
	public String list(StocktakingTask stocktakingtask, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("stordocList",stocktakingTaskService.findStordocList(new Stordoc()));
		model.addAttribute("wkList",stocktakingTaskService.findWkList(new Wk()));//工位
		model.addAttribute("userlist",stocktakingTaskService.getStorUserList());
		model.addAttribute("invbasdocList",stocktakingTaskService.getInvBasDocList(new InvBasDoc()));
		return "modules/stafrock/stocktakingtaskList";
	}
	@RequestMapping(value = "form")
	public String form(StocktakingTask stocktakingtask, Model model) {
		if (stocktakingtask == null) {
			stocktakingtask = new StocktakingTask();
		}
		model.addAttribute("userlist",stocktakingTaskService.getStorUserList());
		model.addAttribute("stordocList",stocktakingTaskService.findStordocList(new Stordoc()));//项目库
		model.addAttribute("wkList",stocktakingTaskService.findWkList(new Wk()));//工位
		model.addAttribute("invbasdocList",stocktakingTaskService.getInvBasDocList(new InvBasDoc()));
		model.addAttribute("stocktakingtask", stocktakingtask);
		return "modules/stafrock/stocktakingtaskForm";
	}
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(StocktakingTask stocktakingtask, HttpServletRequest request, HttpServletResponse response) {
		Page<StocktakingTask> page = stocktakingTaskService.findPage(new Page<StocktakingTask>(request, response),stocktakingtask);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
		return returnMap;
	}
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(StocktakingTask stocktakingtask, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
		}else if (!beanValidatorAjax(returnMap,stocktakingtask)){
			break out;	 
		}else{	
			stocktakingTaskService.save(stocktakingtask);
			addMessageAjax(returnMap,"1","保存字典'" + stocktakingtask.getPkStationid() + "'成功");
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
			stocktakingTaskService.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
	}
	@ResponseBody
	@RequestMapping(value = "findWkList")
	public List<Wk> findWkList(){
		return stocktakingTaskService.findWkList(new Wk());
	}
	@ResponseBody
	@RequestMapping(value = "findStordocList")
	public List<Stordoc> findStordocList(){
		return stocktakingTaskService.findStordocList(new Stordoc());
	}
	@ResponseBody
	@RequestMapping(value = "getInvBasDocList")
	public List<InvBasDoc> getInvBasDocList(){
		return stocktakingTaskService.getInvBasDocList(new InvBasDoc());
	}
	@ResponseBody
	@RequestMapping(value = "findstocktakingStoridList")
	public List<Wk> findstocktakingStoridList(String pkStordoc){
		Wk wk = new Wk();
		wk.setPkStordoc(pkStordoc);
		return stocktakingTaskService.findstocktakingStoridList(wk);
	}
	@ResponseBody
	@RequestMapping(value = "findStorList")
	public List<User> findStorList(String value){
		return stocktakingTaskService.findStorList(value);
	}
	}
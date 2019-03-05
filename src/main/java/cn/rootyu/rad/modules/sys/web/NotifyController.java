/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.sys.web;


import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.oa.entity.OaNotify;
import cn.rootyu.rad.modules.sys.entity.Notify;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.rad.modules.sys.service.NotifyService;
import cn.rootyu.rad.modules.sys.service.SystemService;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 通知Controller
 * @author lijunjie
 * @version 2015-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/notify")
public class NotifyController extends BaseController {
 
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private SystemService systemService;
	
	//, method=RequestMethod.POST
	/**
	 *   自动添加ID
	 */		
//	@ModelAttribute
//	public InvShelves get(@RequestParam(required=false) String id) {
//		if (StringUtils.isNotBlank(id)){
//			return invShelvesService.get(id);
//		}else{
//			return new InvShelves();
//		}
//	}
	
	@ModelAttribute
	public Notify get(@RequestParam(required=false) String id) {
		Notify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = notifyService.get(id);
		}
		if (entity == null){
			entity = new Notify();
		}
		return entity;
	}	
		

	@RequestMapping(value = {"list", ""})
	public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/notifyList";
	}
	
	/**
	 *   通知信息列表
	 */	
	@RequestMapping(value = "notifyList")
	@ResponseBody
	public Map<String,Object> getNotifyList(Notify notify, HttpServletRequest request, HttpServletResponse response){
		//查询当前用户
		User user = UserUtils.getUser();
		if (user.getId() == null || user.getId().equals("")) {
			return null;
		}
		notify.setReceiver(user);
        Page<Notify> page = notifyService.findPage(new Page<Notify>(request, response),notify);
        
		Map<String,Object> returnMap = Maps.newHashMap();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        //returnMap.put("data", page.getList());
        returnMap.put("resultStatus", "200");
		return returnMap;
	}
	
	@RequestMapping(value = "save")
	public Map<String,Object>  save(Notify notify, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if (!beanValidatorAjax(returnMap,notify)){
			return returnMap;
		}
		notifyService.save(notify);
		addMessageAjax(returnMap,"1","保存通知'" + notify.getTitle() + "'成功");
		return returnMap;
	}
	
	/**
	 *   通知设置已读   接口
	 */	
	@RequestMapping(value = "setRead")
	@ResponseBody
	public Map<String,Object> readNotify(Notify notify, RedirectAttributes redirectAttributes){
		//request parameter:id[通知的ID] 不需要传用户ID，默认当前用户
		//查询当前用户
		User user = UserUtils.getUser();
		if (user.getId() == null || user.getId().equals("")) {
			return null;
		}
		notify.setReceiver(user);
		notify.setReadDate(new Date());
		notify.setReadFlag(Notify.READ_FLAG_YES);
		notify.preUpdate();
		
		int str = notifyService.readNotify(notify);		
		Map<String,Object> resulMap = Maps.newHashMap();
		if(str==1){
			resulMap.put("data","已浏览此通知" );
			resulMap.put("resultStatus", "200");
			return resulMap;
		}else{//TODO 此处应该也是执行成功了，只是标识阅读的通知已阅读罢了，返回操作失败和201是不正确的
//			resulMap.put("data", "操作失败");
			resulMap.put("resultStatus", "200");
			return resulMap;
		}
	}
	/**
	 * 获取未读通知数目
	 */
	@RequestMapping(value = "count")
	@ResponseBody
	public String count(Notify notify, Model model) {
		return systemService.unReadNotifyCount(Notify.READ_FLAG_NO);
	}
	/**
	 * APP接口功能： 轮询
	 * 获取全部通知数目、以及最新通知
	 */
	@RequestMapping(value = "countAll")
	@ResponseBody
	public Map<String,Object> countAll(Notify notify, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> resultMap= new HashMap<String, Object>();
		//查询当前用户
		User user = UserUtils.getUser();
		if (user.getId() == null || user.getId().equals("")) {
			return null;
		}
		notify.setReceiver(user);
		Page<Notify> page = notifyService.findPage(new Page<Notify>(request, response),notify);
		//通知总数
		long all=page.getCount();
		if(all<1){
			resultMap.put("resultStatus", "201");
			return resultMap;
		}
		//取得第一条通知
		Notify firstNotify= page.getList().get(0);
		//未读通知数
		String unReadNum=systemService.unReadNotifyCount(Notify.READ_FLAG_NO);

		resultMap.put("total", all);
		resultMap.put("unReadNum", unReadNum);
		resultMap.put("title", firstNotify.getTitle());
		resultMap.put("content", firstNotify.getContent());
		resultMap.put("resultStatus", "200");
		return resultMap;
	}
	
	
	
	@RequestMapping(value = {"showlist"})
	public String showList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("showOnly", "yes");
		return "modules/sys/newnotifyList";
	}
	
	/**
	 *   通知信息列表
	 */	
	@RequestMapping(value = "showNotifyList")
	@ResponseBody
	public Map<String,Object> getShowNotifyList(Notify notify, HttpServletRequest request, HttpServletResponse response){
		//查询当前用户
		User user = UserUtils.getUser();
		if (user.getId() == null || user.getId().equals("")) {
			return null;
		}
		notify.setReceiver(user);
		List<Notify> noList = notifyService.showFindList(notify);
		Map<String,Object> returnMap = Maps.newHashMap();
        returnMap.put("rows", noList);
		return returnMap;
	}
	
	
}

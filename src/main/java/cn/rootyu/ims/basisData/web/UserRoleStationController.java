/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rootyu.ims.basisData.entity.UserRoleStation;
import cn.rootyu.ims.basisData.service.UserRoleStationService;
import cn.rootyu.rad.common.persistence.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.rootyu.ims.common.dao.CodeValueDao;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;

/**
 * 人员角色工位Controller
 * @author DHC
 * @version 2016-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userRoleStation")
public class UserRoleStationController extends BaseController {

	@Autowired
	private UserRoleStationService userRoleStationService;
	
	@Autowired
	private CodeValueDao codeValueDao;
	
	@ModelAttribute
	public UserRoleStation get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return userRoleStationService.get(id);
		}else{
			return new UserRoleStation();
		}
	}
	
	@RequiresPermissions("sys:userRoleStation:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(UserRoleStation userRoleStation,HttpServletRequest request, HttpServletResponse response) {
		Page<UserRoleStation> page = userRoleStationService.findPage(new Page<UserRoleStation>(request, response),userRoleStation);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;                           
	}
	
	@RequiresPermissions("sys:userRoleStation:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserRoleStation userRoleStation, HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("UserList", codeValueDao.findUserList(null));//用户列表
		model.addAttribute("RoleList", codeValueDao.findRoleList(null));//角色列表
		model.addAttribute("stationList", codeValueDao.findStationList(null));//工位
		return  "qmis/basisData/UserRoleStationList";
	}

	@RequiresPermissions("sys:userRoleStation:view")
	@RequestMapping(value = "cognate")
	public String form(UserRoleStation userRoleStation, Model model) {
		model.addAttribute("selectedList",userRoleStationService.findSelectedList(userRoleStation));
		model.addAttribute("unselectedList",userRoleStationService.findUnselectedList(userRoleStation));
		model.addAttribute("userRoleStation",userRoleStation);
		return "qmis/basisData/UserRoleStationForm";
	}
	
	@RequiresPermissions("sys:userRoleStation:edit")
	@RequestMapping(value = "correlation")
	@ResponseBody
	public Map<String,Object> correlation(UserRoleStation userRoleStation, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		String stations = userRoleStation.getStationId();
		if(stations==null||"".equals(stations)){
			stations="";
		}
		String[] strs = stations.split(",");
		userRoleStation.setStationList(new ArrayList<UserRoleStation>());
		for (int i = 0;i < strs.length;i++) {
			if(strs[i]!=null&&!"".equals(strs[i])){
				UserRoleStation urs = new UserRoleStation();
				urs.setStationName(strs[i]);
				userRoleStation.getStationList().add(urs);
			}
		}
		userRoleStationService.save(userRoleStation);
		addMessageAjax(returnMap, "1", "关联成功");
		return returnMap;
	}

}

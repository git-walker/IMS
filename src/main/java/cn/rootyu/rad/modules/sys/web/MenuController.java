package cn.rootyu.rad.modules.sys.web;

import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.sys.entity.Menu;
import cn.rootyu.rad.modules.sys.service.SystemService;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
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

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MenuController
 * @Description 菜单Controller
 * @Authour yuhui
 * @Date 2019/3/11 9:56
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute("menu")
	public Menu get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getMenu(id);
		}else{
			return new Menu();
		}
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Menu> list = Lists.newArrayList();
		List<Menu> sourcelist = systemService.findAllMenu();
		Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("list", list);
		return "modules/sys/menuList";
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "form")
	public String form(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())){
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = systemService.findAllMenu();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0){
				menu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}
		model.addAttribute("menu", menu);
		return "modules/sys/menuForm";
	}

	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "save")
	public String save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能添加或修改数据！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/menu/";
		}
		if (!beanValidator(model, menu)){
			return form(menu, model);
		}
		systemService.saveMenu(menu);
		addMessage(redirectAttributes, "保存菜单'" + menu.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/menu/";
	}

	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "doSave")
	@ResponseBody
	public Map<String,Object> doSave(Menu menu, Model model, RedirectAttributes redirectAttributes) {
        Map<String,Object> returnMap = new HashMap<String,Object>();
		if(!UserUtils.getUser().isAdmin()){
			addMessageAjax(returnMap,"0","越权操作，只有超级管理员才能添加或修改数据！");
		}
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}
		if(menu.getIsShow()!=null && menu.getIsShow().equals("on")){
			menu.setIsShow("1");
		}else{
			menu.setIsShow("0");
		}
		if (!beanValidatorAjax(returnMap, menu)){
			return returnMap;
		}
		systemService.saveMenu(menu);
		addMessageAjax(returnMap,"1","保存菜单'" + menu.getName() + "'成功");
		return returnMap;
	}

	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "delete")
	public String delete(Menu menu, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/menu/";
		}
//		if (Menu.isRoot(id)){
//			addMessage(redirectAttributes, "删除菜单失败, 不允许删除顶级菜单或编号为空");
//		}else{
			systemService.deleteMenu(menu);
			addMessage(redirectAttributes, "删除菜单成功");
//		}
		return "redirect:" + adminPath + "/sys/menu/";
	}

	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "doDelete")
	@ResponseBody
	public Map<String,Object> doDelete(Menu menu, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		systemService.deleteMenu(menu);
		addMessageAjax(returnMap,"1","删除菜单成功");
		return returnMap;
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "tree")
	public String tree() {
		return "modules/sys/menuTree";
	}

//	@RequiresPermissions("user")
//	@RequestMapping(value = "treeselect")
//	public String treeselect(String parentId, Model model) {
//		model.addAttribute("parentId", parentId);
//		return "modules/sys/menuTreeselect";
//	}

	@RequiresPermissions("user")
	@RequestMapping(value = "iconselect")
	public String iconselect(Model model) {

		return "modules/sys/iconSelect";
	}
	
	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "updateSort")//@Valid 
	@ResponseBody
	public Map<String,Object> updateSort(String name,String pk,int value,RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		Menu menu = new Menu(pk);
		menu.setSort(value);
		systemService.updateMenuSort(menu);
		addMessageAjax(returnMap,"1","保存菜单排序成功!'");
		return returnMap;
	}
	
	/**
	 * isShowHide是否显示隐藏菜单
	 * @param extId
	 * @param isShowHidden
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenu();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getMenutree")
	public List<Map<String, Object>> getMenutree(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenuTree(false);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", "1");
		map.put("text","功能菜单");
		List<Map<String, Object>> nodes = convert2Tree(list);
		if(nodes.size()>0){
			map.put("nodes",nodes);
		}	
		mapList.add(map);
		return mapList;
	}

	private List<Map<String, Object>> convert2Tree(List<Menu> list) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if(e.getIsShow()!=null && e.getIsShow().equals("1")){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(e.getIcon() != null){
					if(e.getIcon().startsWith("glyphicon-")){
						map.put("icon", "glyphicon "+e.getIcon());
					}else if(e.getIcon().startsWith("fa-")){
						map.put("icon", "fa "+e.getIcon());
					}else{
						map.put("icon", e.getIcon());
					}
				}
				List<Map<String, Object>> nodes = convert2Tree(e.getSubMenu());
				if(nodes.size()>0){
					map.put("nodes",nodes);
				}		
				mapList.add(map);
			}
		}
		return mapList;
	}

}

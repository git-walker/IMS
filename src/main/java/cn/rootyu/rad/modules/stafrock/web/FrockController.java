package cn.rootyu.rad.modules.stafrock.web;


import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.mapper.JsonMapper;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.Frock;
import cn.rootyu.rad.modules.stafrock.entity.Stordoc;
import cn.rootyu.rad.modules.stafrock.service.FrockService;
import cn.rootyu.rad.modules.sys.entity.Office;
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
 * 工装管理Controller
 * @author jinkf
 * @version 2015-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/stafrock/frock")
public class FrockController extends BaseController {
	
	@Autowired
	private FrockService frockService;
	
	@ModelAttribute
	public Frock get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return frockService.get(id);
		}else{
			return new Frock();
		}
	}

	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Frock frock, HttpServletRequest request, HttpServletResponse response) {

        Page<Frock> page = frockService.findPage(new Page<Frock>(request, response),frock);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Frock frock, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("storeroomList",frockService.findOfficeList(new Office()));//库区列表
		return "modules/stafrock/frockList";
	}

	@RequestMapping(value = "form")
	public String form(Frock frock, Model model){
		model.addAttribute("frock", frock);
		model.addAttribute("storeroomList",frockService.findOfficeList(new Office()));//库区列表
		return "modules/stafrock/frockForm";
	}
	
	@RequestMapping(value = "forms")
	@ResponseBody
	public String checkValues(Frock frock, Model model, HttpServletRequest request){
		String id = request.getParameter("id");
		
		Frock resultMap = frockService.checkValues(id);

		System.out.println(JsonMapper.toJsonString(resultMap));
		return JsonMapper.toJsonString(resultMap);
	}
	

	@RequestMapping(value = "save")
	@ResponseBody
	public  Map<String,Object> save(Frock frock, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else if (!beanValidatorAjax(returnMap,frock)){
			return returnMap;
		}else{
			if(frock.getIsNewRecord()){
				frock.setStatus("1");
			}
			frockService.save(frock);
			addMessageAjax(returnMap,"1","保存工装成功");
		}
		return returnMap;
	}	

	
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(String ids[], RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");

		}else{
			frockService.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
	}	

	@ResponseBody
	@RequestMapping(value = "findStordocList")
	public List<Stordoc> findStordocList(){
		return frockService.findStordocList(new Stordoc());
	}
	
	@RequestMapping(value = "findOfficeList")
	@ResponseBody	
	public List<Office> findOfficeList(){
		return frockService.findOfficeList(new Office());
	}	
}

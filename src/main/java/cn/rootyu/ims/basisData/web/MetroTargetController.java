
package cn.rootyu.ims.basisData.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rootyu.ims.basisData.service.MetroTargetService;
import cn.rootyu.rad.common.persistence.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.rootyu.ims.basisData.entity.MetroTarget;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "${adminPath}/qmis/metrotarget")
public class MetroTargetController extends BaseController {

	@Autowired
	private MetroTargetService metroTargetService;
	
	@ModelAttribute
	public MetroTarget get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return metroTargetService.get(id);
		}else{
			return new MetroTarget();
		}
	}
	
	//@RequiresPermissions("ims:metrotarget:view")
	@RequestMapping(value = {"list", ""})
	public String list(MetroTarget metro, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = metroTargetService.findTypeList();
		model.addAttribute("typeList", typeList);
		return "qmis/basisData/metroTargetList";
	}

	//@RequiresPermissions("ims:metrotarget:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(MetroTarget metro,HttpServletRequest request, HttpServletResponse response) {
        Page<MetroTarget> page = metroTargetService.findPage(new Page<MetroTarget>(request, response),metro);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}

	
	//@RequiresPermissions("ims:metrotarget:view")
	@RequestMapping(value = "form")
	public String form(MetroTarget metro, Model model) {
		model.addAttribute("metro", metro);
		return "qmis/basisData/metroTargetForm";
	}

	//@RequiresPermissions("ims:metrotarget:edit")
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(MetroTarget metro, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();		
			metroTargetService.save(metro);
			addMessageAjax(returnMap,"1","保存'" + metro.getLabel() + "'成功");
		return returnMap;
	}
	
//	@RequiresPermissions("ims:metrotarget:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(MetroTarget metro, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
			metroTargetService.delete(metro);
			addMessageAjax(returnMap,"1", "删除车型成功");
		return returnMap;
	}


//	@RequiresPermissions("ims:metrotarget:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public  Map<String,Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();

			metroTargetService.batchDelete(ids);
			addMessageAjax(returnMap,"1", "删除车型成功");
		return returnMap;
	}
	
	
	
	

}

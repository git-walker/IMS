package cn.rootyu.rad.modules.stafrock.web;


import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.stafrock.entity.Invcl;
import cn.rootyu.rad.modules.stafrock.entity.MaterialMng;
import cn.rootyu.rad.modules.stafrock.service.InvclService;
import cn.rootyu.rad.modules.stafrock.service.MaterialMngService;
import cn.rootyu.rad.modules.stafrock.service.MeasDocService;
import cn.rootyu.rad.modules.supply.entity.MeasDoc;
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
 * 物料（存货档案）管理Controller
 * @author jinkf
 * @version 2015-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/produce/materialmng")
public class MaterialMngController extends BaseController {
	
	@Autowired
	private MaterialMngService materialmngService;
	@Autowired
	private MeasDocService measDocService;
	@Autowired
	private InvclService invclService;
	@ModelAttribute
	public MaterialMng get(@RequestParam(required=false) String id){
		if(StringUtils.isNotBlank(id)){
			return materialmngService.get(id);
		}else{
			return new MaterialMng();
		}
	}
	
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(MaterialMng materialmng, HttpServletRequest request, HttpServletResponse response) {

        Page<MaterialMng> page = materialmngService.findPage(new Page<MaterialMng>(request, response),materialmng);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(MaterialMng materialmng, HttpServletRequest request, HttpServletResponse response, Model model){
//		Page<MaterialMng> page = materialmngService.findPage(new Page<MaterialMng>(request, response), materialmng); 
//        model.addAttribute("page", page);
		//TODO 将存货分类信息放在缓存中读取
		model.addAttribute("flagList",materialmngService.findFlagList(new MaterialMng()));
		model.addAttribute("invclList",invclService.findList(new Invcl()));
		return  "modules/stafrock/materialMngList";
	}

	@RequestMapping(value = "form")
	public String form(MaterialMng materialmng, Model model) {
		model.addAttribute("measList",measDocService.findmeasList());
		model.addAttribute("invclList",invclService.findList(new Invcl()));
		model.addAttribute("materialmng", materialmng);
		return "modules/stafrock/materialMngForm";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public  Map<String,Object> save(MaterialMng materialmng, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
//			return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		}else if (!beanValidatorAjax(returnMap,materialmng)){
	//		return form(dict, model);
			addMessageAjax(returnMap,"0","保存物料失败");
			break out;	 
		}else{	
			materialmngService.save(materialmng);
			addMessageAjax(returnMap,"1","保存物料'" + materialmng.getInvcode() + "'成功");
		}
//		return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		return returnMap;
	}
	
	/**
	 * 删除信息
	 */		
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(String ids[], RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");

		}else{
			materialmngService.delete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		}
		return returnMap;
	}
	
	/**
	 * 查询单位列表
	 * @author chunze.cao
	 * @version 2015-10-30
	 */	
	@RequestMapping(value = "match")
	@ResponseBody
	public List<MeasDoc> match(){
		return measDocService.findmeasList();
	}
	/**
	 * 修改物料的序列批次号标志
	 */
	@RequestMapping(value = "updateFlag")
	@ResponseBody
	public Map<String, Object> updateFlag(Model model, RedirectAttributes redirectAttributes,HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		String id=request.getParameter("pk");//传递过来的物料id
		String type=request.getParameter("name"); //操作的是序列号or批次号
		String value=request.getParameter("value"); //修改后的值
		MaterialMng find=get(id);
		MaterialMng entity=new MaterialMng();
		entity.setId(id);
		if(type.equals("serialmanaflag")){ //序列号
			if(value.equals(find.getSerialmanaflag())){ //当没有修改时
				addMessageAjax(m,"0","操作无效");
				return m;
			}
			if(value.equals("1")){
				entity.setSerialmanaflag("1");
				entity.setWholemanaflag("0");
			}else{
				entity.setSerialmanaflag("0");
			}	
		}else{							  //批次号的
			if(value.equals(find.getWholemanaflag())){ //当没有修改时
				addMessageAjax(m,"0","操作无效");
				return m;
			}
			if(value.equals("1")){
				entity.setSerialmanaflag("0");
				entity.setWholemanaflag("1");
			}else{
				entity.setWholemanaflag("0");
			}	
		}
		try {
			boolean b = materialmngService.update2Flag(entity);//更新序列批次号的标志位
			if (b) {
				addMessageAjax(m,"1","操作成功");
			}else{
				addMessageAjax(m,"0","操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessageAjax(m,"0","操作异常");
		}
		return m;
	}
	
	
}

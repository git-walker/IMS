package cn.rootyu.ims.basisData.web;

import cn.rootyu.ims.basisData.entity.Project;
import cn.rootyu.ims.basisData.service.InvclServicer;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.utils.jqgridSearch.JqGridHandler;
import cn.rootyu.rad.modules.sys.dao.UserDao;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.ims.common.dao.CodeValueDao;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
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


@Controller
@RequestMapping(value = "${adminPath}/qmis/project")
public class InvclController extends BaseController {
	@Autowired
	private CodeValueDao codeValueDao;
	@Autowired
	private InvclServicer invclServicer;
	@Autowired
	private UserDao userDao;
	@ModelAttribute
	public Project get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return invclServicer.get(id);
		}else{
			return new Project();
		}
	}
	
	//@RequiresPermissions("ims:project:view")
	@RequestMapping(value = {"list", ""})
	public String list(Project Project, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = invclServicer.findTypeList();
		model.addAttribute("typeList", typeList);
		model.addAttribute("systemList",codeValueDao.findSystemList(null));
		return "qmis/basisData/invclList";
	}

	//@RequiresPermissions("ims:project:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Project Project,HttpServletRequest request, HttpServletResponse response) {
		String where = new JqGridHandler(request).getWheres(null, true);
		System.out.println("sql:　　　　　　"+where);
        Page<Project> page = invclServicer.findPage(new Page<Project>(request, response),Project);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}

	
	//@RequiresPermissions("ims:project:view")
	@RequestMapping(value = "form")
	public String form(Project project,HttpServletRequest request, Model model) {
			model.addAttribute("project", project);
			model.addAttribute("systemList",codeValueDao.findSystemList(null));

			// 项目经理
			User user = new User();
			/*Office office = new Office();
			office.setId(ConstantUtils.Office.MARKET);
			user.setOffice(office);*/
			List<User> chargeList = userDao.findList(user);
			model.addAttribute("chargeList",chargeList);
		return "qmis/basisData/invclForm";
	}

	//@RequiresPermissions("ims:project:edit")
	@RequestMapping(value = "save") 
	@ResponseBody
	public  Map<String,Object> save(Project project, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		invclServicer.save(project);			
		addMessageAjax(returnMap,"1","保存'" + project.getInvclassname() + "'成功");
		return returnMap;
	}
	
	//@RequiresPermissions("ims:project:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(Project project, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
			invclServicer.delete(project);
			addMessageAjax(returnMap,"1", "删除成功");
		return returnMap;
	}


	//@RequiresPermissions("ims:project:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public  Map<String,Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();

			invclServicer.batchDelete(ids);
			addMessageAjax(returnMap,"1", "删除成功");
		return returnMap;
	}
	
}



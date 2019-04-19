package cn.rootyu.rad.modules.sys.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.rad.common.beanvalidator.BeanValidators;
import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.utils.DateUtils;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.utils.excel.ExportExcel;
import cn.rootyu.rad.common.utils.excel.ImportExcel;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.common.web.Servlets;
import cn.rootyu.rad.modules.sys.dao.UserDao;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.Role;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.rad.modules.sys.service.SystemService;
import cn.rootyu.rad.modules.sys.utils.PhotoUtils;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleController
 * @Description 角色Controller
 * @Authour yuhui
 * @Date 2019/3/11 9:56
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request,Model model) {
		CommonUtil.startPage(request);
		LayuiPageInfo<User> page = systemService.findUser(user);
		model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public LayuiPageInfo searchPage(User user, @RequestBody Map<String,Object> params) {
		CommonUtil.startPage(params);
		LayuiPageInfo<User> pageInfo = null;
		Office office=new Office();
		office.setId((String)params.get("office.id"));
		user.setOffice(office);
		user.setName((String)params.get("name"));
		user.setLoginName((String)params.get("loginName"));
		try{
			pageInfo=systemService.findUser(user);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
		return pageInfo;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user,HttpServletRequest request, Model model) {
		//页面请求先执行上面的get方法，返回user对象，此时user内的部门从数据库中查询
		//是正确的，放入缓存中，随后页面传来的office.id和office.name覆盖user对象中的相应属性，缓存中的user也被改变，导致此bug。
		//这里有安全隐患，页面传入的如果和对象匹配，可随意修改缓存中的值。
		//解决方法，页面值改变名称
		String officeId= request.getParameter("pageOfficeId");
		String officeName= request.getParameter("pageOfficeName");
		Office office = new Office(officeId);
		office.setName(officeName);
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(new Office());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(office);
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user,request, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user,request, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	/**
	 * 上传用户头像
	 */
	//@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "saveAvatar")
	@ResponseBody
	public Map<String,Object> saveAvatar( @RequestParam MultipartFile avatar,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		User user= UserUtils.getUser();
		if(avatar.isEmpty()){
			returnMap.put("status", "0");	
			returnMap.put("message", "请选择头像上传");	
			return returnMap;
		}
		String imgName = avatar.getOriginalFilename();
        String suffix = imgName.substring(imgName.lastIndexOf(".")+1,imgName.length());
        //todo 可以验证图片格式
        if(!"jpg".equals(suffix)&&!"png".equals(suffix)&&!"gif".equals(suffix)&&!"jpeg".equals(suffix)){
        	returnMap.put("status", "0");	
			returnMap.put("message", "图片格式错误，请重试");	
			return returnMap;
        }
		String name =user.getId()+"_large";
//        String urlString="/uploads/userAvatar/";
        String urlString= Global.getConfig("user.avatar.path");

        //web端访问路径
        String filePath = Servlets.getRequest().getContextPath() +
                urlString + name +"."+suffix;
        //本地保存路径
        String fileLarge = Global.getUserfilesBaseDir() + urlString + user.getId()+"_large" +"."+suffix;
        String fileMiddle = Global.getUserfilesBaseDir() + urlString + user.getId()+"_middle" +"."+suffix;
        String fileSmall = Global.getUserfilesBaseDir() + urlString + user.getId()+"_small" +"."+suffix;
        String fileOr = Global.getUserfilesBaseDir() + urlString + user.getId() +"."+suffix;
        //创建目录
        File oldFile = new File(fileOr);
        if (!oldFile.exists()) {
        	oldFile.mkdirs();
        }
        //保存原图片
        try {
            avatar.transferTo(oldFile);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("status", "0");	
			returnMap.put("message", "存储用户'" + user.getLoginName() + "'头像失败,请重试");	
        	return returnMap;
        }
        try {
        	PhotoUtils.zoomImage(fileOr, fileLarge, 200, 200);
        	PhotoUtils.zoomImage(fileOr, fileMiddle, 100, 100);
        	PhotoUtils.zoomImage(fileOr, fileSmall, 50, 50);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnMap.put("status", "0");	
			returnMap.put("message", "存储用户'" + user.getLoginName() + "'头像失败,请重试");	
        	return returnMap;
		}
		//此处上传只存储图片的格式，具体的图片由     uploads/userAvatar/user.id_large+user.photo
		user.setPhoto("."+suffix);
		try {
			systemService.updateUserInfo(user);
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.put("status", "0");	
			returnMap.put("message", "保存用户'" + user.getLoginName() + "'头像失败");	
		}
		returnMap.put("status", "1");	
		returnMap.put("url",filePath );
		return returnMap;
	}
	
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "doSave")
	@ResponseBody
	public Map<String,Object> doSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Map<String,Object> returnMap = new HashMap<String,Object>();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap, "0",  "演示模式，不允许操作！");
			return returnMap;
		}

		if(user.getLoginFlag()!=null && user.getLoginFlag().equals("on")){
			user.setLoginFlag("1");
		}else{
			user.setLoginFlag("0");
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		String officeId = request.getParameter("office.id");
		user.setOffice(new Office(officeId));
		Office company = systemService.getCompany(officeId);
		user.setCompany(company);
		// 如果新密码为空，则不更换密码
		if (!StringUtils.isNotBlank(user.getNewPassword())&&(user.getId()==null||user.getId().equals(""))) {
			user.setNewPassword("123456");
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidatorAjax(returnMap, user)){
			return returnMap;
		}
		Map<String,Object> checkResult =checkLoginNameAjax(user.getLoginName(),user.getId());
		if(checkResult.get("valid").equals("false")){
			addMessageAjax(returnMap, "0",  "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return returnMap;
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		if(roleList.size()==0 && user.getLoginFlag().equals("1")){
			addMessageAjax(returnMap, "0",  "保存用户'" + user.getLoginName() + "'失败，允许登录用户没有分配角色");		
			return returnMap;	
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
		}
		addMessageAjax(returnMap, "1",  "保存用户'" + user.getLoginName() + "'成功");
		return returnMap;
	}
	
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(User user, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessageAjax(returnMap,"0", "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessageAjax(returnMap,"0", "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessageAjax(returnMap,"1", "删除用户成功");
		}
		return returnMap;
	}
	
	/**
	 * 函数功能说明 :批量删除用户
	 * @参数： @param ids
	 * @参数： @param redirectAttributes
	 * @参数： @return
	 * @throws
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public  Map<String,Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		List<String> list = Arrays.asList(ids);
		for(int i = 0 ; i<list.size();i++){
			String id = list.get(i);
			if (UserUtils.getUser().getId().equals(id)){
				addMessageAjax(returnMap,"0", "删除用户失败, 不允许删除当前用户");
				return returnMap;
			}else if (User.isAdmin(id)){
				addMessageAjax(returnMap,"0", "删除用户失败, 不允许删除超级管理员用户");
				return returnMap;
			}
		}
		systemService.batchDeleteUser(list);
		addMessageAjax(returnMap,"1", "删除用户成功");
		return returnMap;
	}
	
	/**
	 * 函数功能说明 :重置用户密码
	 * @参数： @param id
	 * @参数： @param redirectAttributes
	 * @参数： @return
	 * @throws
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "resetPwd")
	@ResponseBody
	public  Map<String,Object> resetPwd(String id,String loginName, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		if(!UserUtils.getUser().isAdmin()){
			addMessageAjax(returnMap,"0","非超级管理员用户不可以重置用户密码！");
			return returnMap;
		}
		systemService.updatePasswordById(id,loginName,"123456");
		addMessageAjax(returnMap,"1", "重置密码成功");
		return returnMap;
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
	//TODO
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<User> list = systemService.findAllUser(user);
    		new ExportExcel("用户数据", User.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 验证登录名是否被占用
	 * @param loginName
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginNameAjax")
	public Map<String,Object> checkLoginNameAjax(String loginName, String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		User user = systemService.getUserByLoginName(loginName);
		if(user == null){
			map.put("valid",true);
		}else if(id != null && !id.equals("")){
			if(id.equals(user.getId())){
				map.put("valid",true);
			}else{
				map.put("valid",false);				
			}
		}else{
			map.put("valid",false);
		}
		return map;
	}
	

	/**
	 * 用户信息显示
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}
	
	/**
	 * 用户信息保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveInfo")
	@ResponseBody
	public void saveInfo(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
//		model.addAttribute("Global", new Global());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("user", currentUser);
		returnMap.put("message", "保存用户信息成功");
		ResMessage(response, "1", returnMap);
//		return returnMap;
	}
	
	public  void ResMessage(HttpServletResponse response,String idStr,Map<String,Object> valueStr)
	{
		//{"result_code":"?","result":{"?"}}
		StringBuffer resStrBuff = new StringBuffer();
	
			resStrBuff.append(valueStr);
	

		PrintWriter out = null;
		  
 
		try
		{ 
			out = response.getWriter();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(out != null)
		{
			out.println(resStrBuff.toString());
			out.flush();
			out.close();
		}
	}
	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "doModifyPwd")
	@ResponseBody
	public Map<String,Object> doModifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		out:
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			
			if(Global.isDemoMode()){
				//model.addAttribute("message", "演示模式，不允许操作！");
				returnMap.put("message",  "演示模式，不允许操作！");
				break out;
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				returnMap.put("message",  "修改密码成功");
				returnMap.put("status", "success");
				break out;
//				model.addAttribute("message", "修改密码成功");
			}else{
				returnMap.put("message",  "修改密码失败，旧密码错误");
				returnMap.put("status", "failure");
				break out;
//				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}

		return returnMap;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
	/**
	 *初始化用户密码
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "initPassword")
	public String initPassword(Model model) {
		
		systemService.initUserPassword();	
		return "";
	}

	/**
	 * 禁止用户登录
	 * @param id
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "resetUserLogin")
	@ResponseBody
	public ResultBean resetUserLogin(String id) {
		ResultBean resultBean=new ResultBean();
		try{
			systemService.resetUserLogin(new User(id));
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			resultBean.setSuccess(false);
			resultBean.setMsg(e.getMessage());
		}
		return resultBean;
	}

	//下载
	@ResponseBody
	@RequestMapping(value = "downloadChrome")
	public void downloadChrome(String version,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String basePath="";
		// 32位
//		if("1".equals(version)) {
//			basePath="/resource/52.0.2743.116_chrome_installer(32).exe";
//		} else {
//			basePath="/resource/52.0.2743.116_chrome_installer(64).exe";
//		}
		if("1".equals(version)) {
			basePath="Chrome_x86.exe";
		} else {
			basePath="Chrome_x64.exe";
		}
		// 获取webroot下资源路径
//		String path = request.getSession().getServletContext().getRealPath(basePath);
		String path = "D:\\resource\\browser\\" + basePath;
		// 获取当前目录的图片路径
        // 获取文件名
		File file = new File(path);  
        //制定浏览器头
        //在下载的时候这里是英文是没有问题的 
        //如果图片名称是中文需要设置转码
		String liulanqi = request.getHeader("User-Agent").toLowerCase();
		if(liulanqi.indexOf("firefox")>0){
		    response.setHeader("content-disposition", "attachment;fileName=\""+new String(file.getName().getBytes("UTF-8"), "ISO-8859-1")+"\"");
		}else{
		    response.setHeader("content-disposition", "attachment;fileName="+file.getName());
		}
        
        InputStream reader = null;
        OutputStream out = null;
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            // 读取文件
            reader = new FileInputStream(path);
            // 写入浏览器的输出流
            out = response.getOutputStream();

            while ((len = reader.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (out != null)
                out.close();
        }
	}
}

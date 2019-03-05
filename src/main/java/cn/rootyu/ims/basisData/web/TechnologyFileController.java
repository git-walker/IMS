/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rootyu.ims.basisData.entity.TechnologyFile;
import cn.rootyu.ims.basisData.service.TechnologyFileService;
import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.modules.act.service.ActProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.rootyu.rad.common.utils.DateUtils;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;

import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;


/**
 * 文件管理 Controller
 * 
 * @author DHC
 * @version 2014-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/qmis/technologyFile")
public class TechnologyFileController extends BaseController {

	@Autowired
	private TechnologyFileService tfService;
	
	@Autowired
	private ActProcessService actProcessService;


	@ModelAttribute
	public TechnologyFile get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return tfService.get(id);
		} else {
			return new TechnologyFile();
		}
	}

	@RequestMapping(value = { "list", "" })
	public String list(TechnologyFile technologyFile,HttpServletRequest request, HttpServletResponse response,Model model) {
		List<TechnologyFile> fileTypeList = tfService.findFileTypeList();
		Date beginDate = DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", DateUtils.addMonths(beginDate, 1));
		model.addAttribute("fileTypeList", fileTypeList);
		return "qmis/basisData/TechnologyFileList";
	}

	@RequestMapping(value = { "searchPage" })
	@ResponseBody
	public Map<String, Object> searchPage(TechnologyFile technologyFile,HttpServletRequest request, HttpServletResponse response) {
		Page<TechnologyFile> page = tfService.findPage(new Page<TechnologyFile>(request, response), technologyFile);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
		return returnMap;
	}

	@RequestMapping(value = "form")
	public String form(TechnologyFile technologyFile, Model model) {
		List<TechnologyFile> fileTypeList = tfService.findFileTypeList();
		if(technologyFile.getFileDate()==null){
			technologyFile.setFileDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		model.addAttribute("fileTypeList", fileTypeList);
		model.addAttribute("technologyFile", technologyFile);
		return "qmis/basisData/TechnologyFileForm";
	}

	@RequestMapping(value = "upload")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam("files") MultipartFile[] files,@RequestParam("id") String id,HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		TechnologyFile technologyFile = tfService.get(id);
		MultipartFile file = files[0];
		String fileName = file.getOriginalFilename();
		// 验证格式
		String suffix = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
		// 验证图片大小
		if (file.getSize() > 5 * 1024 * 1024) {
			addMessageAjax(returnMap, "0", "文件过大");
			return returnMap;
		}
		String urlString = Global.getConfig("files.uploadFile");
		// 本地保存路径
		String fileLocalPath = urlString + id + "/" + technologyFile.getFileName()+"."+suffix;
		// 创建目录
		File saveDirFile = new File(fileLocalPath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 保存图片
		try {
			file.transferTo(saveDirFile);
		} catch (Exception e) {
			e.printStackTrace();
			addMessageAjax(returnMap, "0", "文件上传失败");
			return returnMap;
		}
		technologyFile.setFileStatus("1");
		tfService.saveStatus(technologyFile,request.getContextPath());
		returnMap.put("messageStatus", "1");
		return returnMap;
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(TechnologyFile technologyFile, Model model,RedirectAttributes redirectAttributes) {
		Map<String, Object> returnMap = Maps.newHashMap();
		String file = technologyFile.getId();
		//id不为空并且 文件版本被修改 并且状态不为0
		if(file != null && !"".equals(file) && !technologyFile.getFileVersion().equals(tfService.get(file).getFileVersion()) && !"0".equals(technologyFile.getFileStatus())){
			TechnologyFile tf = tfService.get(file);
			tf.setFileStatus("H");
			//tf.setDelFlag("1")
			tfService.save(tf);
			technologyFile.setId("");
			technologyFile.setFileStatus("0");
			tfService.save(technologyFile);
		}else{
			if("".equals(file) && tfService.countFileCode(technologyFile)!=0 ){
				addMessageAjax(returnMap, "0", "文件编码重复，保存失败");
				return returnMap;
			}else{
				if(file == null || "".equals(file)){
					technologyFile.setFileStatus("0");
				}
				tfService.save(technologyFile);
			}
		}
		addMessageAjax(returnMap, "1", "保存成功");
		return returnMap;
	}

	@RequestMapping(value = "saveId")
	public String saveId(TechnologyFile technologyFile, Model model) {
		model.addAttribute("fileId", technologyFile.getId());
		return "qmis/basisData/TechnologyFileUpload";
	}
	
	@RequestMapping(value = "judgement")
	@ResponseBody
	public ModelMap judgement(@RequestParam("ids") String ids ,  TechnologyFile technologyFile, Model model, RedirectAttributes redirectAttributes) {
		String[] id =  ids.split(",");
		int result = 0;
		StringBuilder str = new StringBuilder();
		for(int i = 0;i < id.length ; i++){
			if(tfService.getQuantity(id[i])>0){
				result++;
				str.append(tfService.get(id[i]).getFileName()+"、");
			}
		}
		ModelMap map=new ModelMap();
		if(result>0){
			str.deleteCharAt(str.length()-1);
			map.put("result", "0");
			map.put("str", "以下文件 "+str+" 已被引用，是否删除？一旦删除不可恢复！");
		}else{
			map.put("result", "1");
			map.put("str", "文件一旦删除不可恢复！是否删除？");
		}
		return map;
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, Object> delete(TechnologyFile technologyFile,RedirectAttributes redirectAttributes) {
		Map<String, Object> returnMap = Maps.newHashMap();
		if (Global.isDemoMode()) {
			addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
		} else {
			File file = new File(Global.getConfig("files.uploadFile")+technologyFile.getId()+"/");  
			tfService.deleteFile(technologyFile,file);
			addMessageAjax(returnMap, "1", "删除文件成功");
		}
		return returnMap;
	}

	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Map<String, Object> batchDelete(String[] ids,RedirectAttributes redirectAttributes) {
		Map<String, Object> returnMap = Maps.newHashMap();
		if (Global.isDemoMode()) {
			addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
		} else {
			tfService.batchDelete(ids);
			addMessageAjax(returnMap, "1", "删除文件成功");
		}
		return returnMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "download")
	public void download(@RequestParam(value = "id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String  path = Global.getConfig("files.uploadFile")+id+"/";
        tfService.downloadFile(new File(path),request,response);
      
	}
	
	@RequestMapping(value = "findFile")
	@ResponseBody
	public Map<String,Object> findFile(@RequestParam("planId") String planId,HttpServletRequest request, HttpServletResponse response, Model model) {
	        Map<String,Object> Map = new HashMap<String,Object>();
	        List<TechnologyFile> fileList = tfService.findFileList(planId);
	        Map.put("row",fileList);
	        return Map;
	} 
	
	@RequestMapping(value = "fileHistory")
	public String fileHistory(TechnologyFile technologyFile, Model model) {
		model.addAttribute("technologyFile", technologyFile);
		return "qmis/basisData/fileHistory";
	} 
	
	@RequestMapping(value = {"fileHistoryList"})
	@ResponseBody
	public Map<String,Object> searchPage(@RequestParam("id") String id,TechnologyFile technologyFile,HttpServletRequest request, HttpServletResponse response) {
		technologyFile = tfService.get(id);
		List<TechnologyFile> fileHistoryList = tfService.fileHistoryList(technologyFile);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("row", fileHistoryList);
        return returnMap;
	}

}

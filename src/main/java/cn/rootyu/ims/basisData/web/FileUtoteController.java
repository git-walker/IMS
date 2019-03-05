/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.web;

import java.util.List;
import java.util.Map;

import cn.rootyu.ims.basisData.entity.FileQuote;
import cn.rootyu.ims.basisData.entity.TechnologyFile;
import cn.rootyu.ims.basisData.service.FileQuoteService;
import cn.rootyu.ims.basisData.service.TechnologyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import cn.rootyu.rad.common.utils.StringUtils;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;

/**
 * 文件关联 Controller
 * 
 * @author DHC
 * @version 2014-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/qmis/fileutote")
public class FileUtoteController extends BaseController {

	@Autowired
	private FileQuoteService fqService;
	
	@Autowired
	private TechnologyFileService tfService;

	@ModelAttribute
	public FileQuote get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return fqService.get(id);
		} else {
			return new FileQuote();
		}
	}
	
	@RequestMapping(value = "fileList")
	public String fileList(@RequestParam("planId") String planId ,@RequestParam("requestTag") String requestTag , Model model) {
		//文件关联
		List<FileQuote> fileList = fqService.findFileList(planId);
		//文件信息
		List<TechnologyFile> allFileList = tfService.findAllList();
		model.addAttribute("fileList",fileList);
		model.addAttribute("allFileList",allFileList);
		model.addAttribute("planId",planId);
		model.addAttribute("requestTag",requestTag);
		return "qmis/basisData/selectFileList";
	}
	
	@RequestMapping(value = "correlation")
	@ResponseBody
	public Map<String,Object> correlation(@RequestParam("requestTag") String requestTag ,  FileQuote fileQuote, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		fqService.correlation(fileQuote.getFileId(),fileQuote.getPlanId(),requestTag);
		addMessageAjax(returnMap, "1", "文件关联成功");
		return returnMap;
	}
	


}

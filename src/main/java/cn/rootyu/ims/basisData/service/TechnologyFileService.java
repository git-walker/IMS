/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rootyu.ims.basisData.entity.FileQuote;
import cn.rootyu.rad.common.config.Global;
import cn.rootyu.rad.common.service.CrudService;
import cn.rootyu.rad.modules.sys.dao.NotifyDao;
import cn.rootyu.rad.modules.sys.entity.Notify;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.rad.modules.sys.service.NotifyService;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rootyu.ims.basisData.dao.FileQuoteDao;
import cn.rootyu.ims.basisData.dao.TechnologyFileDao;
import cn.rootyu.ims.basisData.entity.TechnologyFile;

/**
 * 文件管理Service
 * 
 * @author DHC
 * @version 2014-11-02
 */
@Service
@Transactional(readOnly = true)
public class TechnologyFileService extends CrudService<TechnologyFileDao, TechnologyFile> {
	
	@Autowired
	private FileQuoteDao fqDao;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private NotifyDao notifyDao;

	@Transactional(readOnly = false)
	public List<TechnologyFile> findFileTypeList() {
		return dao.findFileTypeList();
	}

	@Transactional(readOnly = false)
	public int countFileCode(TechnologyFile technologyFile) {
		return dao.countFileCode(technologyFile);
	}
	
	@Transactional(readOnly = false)
	public List<TechnologyFile> findAllList(){
		return dao.findAllList();
	}

	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			File file = new File(Global.getConfig("files.uploadFile") + ids[i] + "/");
			deleteFile(file);
			TechnologyFile technologyFile = new TechnologyFile(ids[i]);
			technologyFile.preUpdate();
			super.delete(technologyFile);//文件信息表删除
			FileQuote fileQuote = fqDao.get(ids[i]);
			fileQuote.preUpdate();
			fqDao.fileIdDelete(fileQuote);//文件关联表删除
		}
	}

	@Transactional(readOnly = false)
	public int getQuantity(String id) {
		return dao.getQuantity(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteFile(TechnologyFile technologyFile, File file) {
		//technologyFile.preUpdate()
		technologyFile.preUpdate();
		delete(technologyFile);//文件信息表删除
		fqDao.fileIdDelete(fqDao.get(technologyFile.getId()));//文件关联表删除
		deleteFile(file);
	}

	@Transactional(readOnly = false)
	public void downloadFile(File dar, HttpServletRequest request, HttpServletResponse response)throws IOException {
		if (dar.exists()) {// 判断文件是否存在
			File files = dar.listFiles()[0];// 声明目录下所有的文件 files[]
			String liulanqi = request.getHeader("User-Agent").toLowerCase();
            if(liulanqi.indexOf("firefox")>-1){
                response.setHeader("content-disposition", "attachment;fileName=\""+new String(files.getName().getBytes("UTF-8"), "ISO-8859-1")+"\"");
            }else{
                response.setHeader("content-disposition", "attachment;fileName="+URLEncoder.encode(files.getName(), "UTF-8"));
            }
			InputStream reader = null;
			OutputStream out = null;
			byte[] bytes = new byte[1024];
			int len = 0;
			try {
				// 读取文件
				reader = new FileInputStream(files);
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
	
	public void deleteFile(File file){
		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
			}else if (file.isDirectory()) {// 否则如果它是一个目录
				File[] files = file.listFiles();// 声明目录下所有的文件 files[]
				for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
					this.deleteFile(files[i]);// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
			}
		}
	}
	
	@Transactional(readOnly = false)
	public List<TechnologyFile> findFileList(String planId){
		return dao.findFileList(planId);
	}
	
	@Transactional(readOnly = false)
	public List<TechnologyFile> fileHistoryList(TechnologyFile technologyFile){
		return dao.fileHistoryList(technologyFile);
	}
	
	@Transactional(readOnly = false)
	public void saveStatus(TechnologyFile technologyFile,String url){
		//更改文件状态
		save(technologyFile);
		if(countFileCode(technologyFile)>1){
			//发送文件变更通知
			Notify notify = new Notify();
			notify.setType("1");
			notify.setTitle("文件版本变更通知");
			notify.setUrgentFlag("0");
			notify.setReadFlag("0");;
			User user = UserUtils.getUser();
			notify.setSender(user);
			notify.setDelFlag("0");
			
			//检验计划
			List<TechnologyFile> planUser =  planUserList(technologyFile);
			for (TechnologyFile tf : planUser) {
				notify.setContent("您创建的检验计划：<a class='btn btn-info btn-sm' onClick=\"javascript:$('#viewDivId').dialog('close');location.href='"+url+"/#page/inspection/inspectionPlan'\">"+tf.getName()+"</a>关联的名字为："+technologyFile.getFileName()+",编码为："+technologyFile.getFileCode()+"的文件版本已更新！");
				notify.setReceiver(tf.getCreateBy());
				notify.setLoginName(tf.getLoginName());
				notify.preInsert();
				notifyDao.insert(notify);
			}
			//质量计划
			List<TechnologyFile> qualityUser =  qualityUserList(technologyFile);
			for (TechnologyFile tf : qualityUser) {
				notify.setContent("您创建的质量计划：<a class='btn btn-info btn-sm' onClick=\"javascript:$('#viewDivId').dialog('close');location.href='"+url+"/#page/inspection/inspectionQuality'\">"+tf.getName()+"</a>关联的名字为："+technologyFile.getFileName()+",编码为："+technologyFile.getFileCode()+"的文件版本已更新！");
				notify.setReceiver(tf.getCreateBy());
				notify.setLoginName(tf.getLoginName());
				notify.preInsert();
				notifyDao.insert(notify);
			}
			//控制计划
			List<TechnologyFile> controlUser =  controlUserList(technologyFile);
			for (TechnologyFile tf : controlUser) {
				notify.setContent("您创建的质量计划：<a class='btn btn-info btn-sm' onClick=\"javascript:$('#viewDivId').dialog('close');location.href='"+url+"/#page/inspection/inspectionControl'\">"+tf.getName()+"</a>关联的名字为："+technologyFile.getFileName()+",编码为："+technologyFile.getFileCode()+"的文件版本已更新！");
				notify.setReceiver(tf.getCreateBy());
				notify.setLoginName(tf.getLoginName());
				notify.preInsert();
				notifyDao.insert(notify);
			}

		}
	}
	
	@Transactional(readOnly = false)
	public List<TechnologyFile> planUserList(TechnologyFile technologyFile){
		return dao.planUserList(technologyFile);
	}
	
	@Transactional(readOnly = false)
	public List<TechnologyFile> qualityUserList(TechnologyFile technologyFile){
		return dao.qualityUserList(technologyFile);
	}
	
	@Transactional(readOnly = false)
	public List<TechnologyFile> controlUserList(TechnologyFile technologyFile){
		return dao.controlUserList(technologyFile);
	}

	@Transactional(readOnly = false)
	public List<TechnologyFile> fileQuoteHistory(String id) {
		return dao.fileQuoteHistory(id);
	}

}

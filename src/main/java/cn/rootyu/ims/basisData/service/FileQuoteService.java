/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.service;

import java.util.List;

import cn.rootyu.rad.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.rootyu.ims.basisData.dao.FileQuoteDao;
import cn.rootyu.ims.basisData.entity.FileQuote;

/**
 * 文件关联 Service
 * 
 * @author DHC
 * @version 2014-11-14
 */
@Service
@Transactional(readOnly = true)
public class FileQuoteService extends CrudService<FileQuoteDao, FileQuote> {

	@Transactional(readOnly = false)
	public List<FileQuote> findFileList(String planId){
		return dao.findFileList(planId);
	}
	
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			FileQuote fileQuote = new FileQuote(ids[i]);
			super.delete(fileQuote);
		}
	}
	
	@Transactional(readOnly = false)
	public void correlation(String fileId,String planId,String requestTag) {
		if(fileId==null){
			fileId="";
		}
		String[] fileIds = fileId.split(",");
		List<FileQuote> fileList = findFileList(planId);//关联
		FileQuote file = new FileQuote();
		if(fileList.size()>0){
			int[] listi = new int[fileList.size()];
			int[] listj = new int[fileIds.length];
			for (int i = 0 ; i < fileList.size() ; i++) {
				for(int j = 0 ; j < fileIds.length; j++) {
					file = fileList.get(i);
					if(fileIds[j].equals(file.getFileId())){
						listi[i] = 1;
						listj[j] = 1;
					}
				}
			}
			for(int i = 0 ; i < fileList.size() ; i++){
				if(listi[i]==0){
					file = fileList.get(i);
					file.preUpdate();
					dao.delete(file);
				}
			}
			for(int j = 0 ; j < fileIds.length ; j++){
				if(listj[j]==0 && !"".equals(fileIds[j])){
					FileQuote filee = new FileQuote();
					filee.setFileId(fileIds[j]);
					filee.setPlanId(planId);
					filee.setStatus("L");
					filee.setPlanType(requestTag);
					filee.preInsert();
					dao.insert(filee);
				}
			}
		}else{
			for(int i = 0 ; i < fileIds.length ; i++){
				file.setFileId(fileIds[i]);
				file.setPlanId(planId);
				file.setStatus("L");
				file.setPlanType(requestTag);
				file.preInsert();
				dao.insert(file);
			}
		}
	}
	
}

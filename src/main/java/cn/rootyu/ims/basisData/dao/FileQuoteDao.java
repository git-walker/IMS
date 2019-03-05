/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.dao;

import cn.rootyu.ims.basisData.entity.FileQuote;
import cn.rootyu.ims.basisData.entity.TechnologyFile;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * 文件管理 DAO接口
 * @author DHC
 * @version 2016-11-02
 */
@MyBatisDao
public interface FileQuoteDao extends CrudDao<FileQuote> {

	public List<FileQuote> findFileList(String planId);
	
	public List<TechnologyFile> findAllFileList();
	
	public void fileIdDelete(FileQuote fileQuote);
}

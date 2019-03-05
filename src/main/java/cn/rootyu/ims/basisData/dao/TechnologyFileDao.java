/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.dao;

import cn.rootyu.ims.basisData.entity.TechnologyFile;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * 文件关联 DAO接口
 * @author DHC
 * @version 2016-11-14
 */
@MyBatisDao
public interface TechnologyFileDao extends CrudDao<TechnologyFile> {

	public List<TechnologyFile> findFileTypeList();
	
	public List<TechnologyFile> findAllList();
	
	public List<TechnologyFile> findFileList(String planId);
	
	public int countFileCode(TechnologyFile technologyFile);
	
	public int getQuantity(String id);

	public List<TechnologyFile> fileHistoryList(TechnologyFile technologyFile);

	public List<TechnologyFile> planUserList(TechnologyFile technologyFile);

	public List<TechnologyFile> qualityUserList(TechnologyFile technologyFile);

	public List<TechnologyFile> controlUserList(TechnologyFile technologyFile);

	public List<TechnologyFile> fileQuoteHistory(String id);
	
}

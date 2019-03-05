package cn.rootyu.rad.modules.stafrock.dao;

import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.CargDoc;
import cn.rootyu.rad.modules.stafrock.entity.JobStorMng;
import cn.rootyu.rad.modules.stafrock.entity.StorCarg;
import cn.rootyu.rad.modules.sys.entity.Office;

import java.util.List;
import java.util.Map;

/**
 * 项目仓库管理DAO接口
 * @author jinkf
 * @version 2015-10-23
 */
@MyBatisDao
public interface JobStorMngDao extends CrudDao<JobStorMng> {

	/**
	 * 匹配项目仓库信息
	 * @author jinkf
	 * @version 2015-10-30
	 */
	JobStorMng jobsMtorMatch(String storid);
	
	List<Office> getStoreRoomList(Office office);

	int updateStoreroom(JobStorMng jobStorMng);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-7
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param reservoirId
	 * @参数： @return
	 * @throws     
	 */
	List<CargDoc> getAllStorDocTree(Map<String, Object> params);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-7
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param reservoirId
	 * @参数： @param storDocId
	 * @参数： @return
	 * @throws     
	 */
	List<String> getStorDocIdList(StorCarg storCarg);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param storCarg
	 * @throws     
	 */
	void saveStorDocMenu(StorCarg storCarg);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param storCarg
	 * @throws     
	 */
	void deleteStorDocMenu(StorCarg storCarg);

	List<JobStorMng> getVehiclemodelidList(JobStorMng JobStorMng);

	int updateVehiclemodelid(JobStorMng jobStorMng);

}

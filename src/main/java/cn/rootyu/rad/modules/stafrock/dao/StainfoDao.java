/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.dao;


import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.stafrock.entity.*;
import cn.rootyu.rad.modules.stafrocktype.entity.StaFrock;
import cn.rootyu.rad.modules.sys.entity.Office;
import cn.rootyu.rad.modules.sys.entity.User;

import java.util.List;
import java.util.Map;


/**
 * 工位档案管理DAO接口
 * @author chunze.cao
 * @version 2015-10-14
 */
@MyBatisDao
public interface StainfoDao  extends CrudDao<Stainfo> {

	/**
	 * 按ID查询工位信息
	 */	
	Stainfo checkValues(String id);
	
	/**
	 * 查询项目仓库列表
	 */
	List<Stordoc> findStordocList(Stordoc stordoc);
//	public List<Office> findOfficeList();
	
	/**
	 * 查询仓库人员列表
	 */
//	public List<User> findUserList();

	/**
	 * 查询工作中心（工位）列表
	 */
	List<Wk> findWkList(Wk wk);
	/**
	 * 查询库管员列表（局部刷新）
	 */
	List<User> findStorList(String value);

	int updateStorekeeper(Stainfo stainfo);

	/**  
	 * 函数功能说明 :根据工位管理ID查找匹配规则及未选择对应工装车
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param id
	 * @参数： @return
	 * @throws     
	 */
	List<StaFrock> getNoChooseFrock(String id);
	/**  
	 * 函数功能说明 :根据工位管理ID查找匹配规则及选择对应工装车
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param id
	 * @参数： @return
	 * @throws     
	 */
	List<String> getChooseFrock(String id);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @return
	 * @throws     
	 */
	List<StaFrock> getAllFrock();

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param id
	 * @throws     
	 */
	int deleteStaFrock(Stainfo stainfo);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-8
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param staFrock
	 * @throws     
	 */
	int insertStaFrock(StaFrock staFrock);
	/**
	 * 函数功能说明 :获取仓库管理员集合
	 * 作者: fangzr  
	 * 创建时间：2015-12-9
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @return
	 * @throws
	 */
	List<User> getStorUserList();
	/**
	 * 
	 * 函数功能说明 :获取仓库管理员所在库区集合
	 * 作者: fangzr  
	 * 创建时间：2015-12-9
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param office
	 * @参数： @return
	 * @throws
	 */
	List<Office> getReservoirList(Office office);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param storDocId
	 * @参数： @return
	 * @throws     
	 */
	List<CargDoc> getAllCargDocTree(Map<String, Object> params);

	/**  
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param staCarg
	 * @参数： @return
	 * @throws     
	 */
	List<String> getCargDocIdList(StaCarg staCarg);

	/**  
	 * 函数功能说明 :保存工位管理档案ID和货位的关系
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param staCarg
	 * @throws     
	 */
	void saveStaCargMenu(StaCarg staCarg);

	/**  
	 * 函数功能说明 :删除工位管理档案ID和货位的关系
	 * 作者: fangzr  
	 * 创建时间：2015-12-11
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param staCarg
	 * @throws     
	 */
	void deleteStaCargMenu(StaCarg staCarg);
	
}

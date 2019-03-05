/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Notify;

import java.util.List;
import java.util.Map;


/**
 * 通知DAO接口
 * @author lijunjie
 * @version 2015-11-21
 */


@MyBatisDao
public interface NotifyDao extends CrudDao<Notify> {

	List<Map<String,Object>> getNotifyListByUserId(String userId);

	int readNotify(Notify notify);

	int insertAll(List<Notify> notifyList);

	Long findCount(Notify notify);
	List<Notify> showFindList(Notify notify);
	
//	public Map<String, Object> getCurrentamount(Map<String, String> mapWhere);
//
//	public void save(Map<String, Object> map);
//
//	public void update(Map<String, Object> map1);
//
//	public List<Map<String, Object>> getNinnum(Map<String, Object> map);



}

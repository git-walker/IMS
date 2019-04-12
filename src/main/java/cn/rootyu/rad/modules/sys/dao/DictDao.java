package cn.rootyu.rad.modules.sys.dao;

import cn.rootyu.rad.common.dao.CrudDao;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;
import cn.rootyu.rad.modules.sys.entity.Dict;

import java.util.List;

/**
 * 字典DAO接口
 * @author yuhui
 * @version 1.0
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	List<String> findTypeList(Dict dict);
	
	int batchDelete(List<String> list);
	
}

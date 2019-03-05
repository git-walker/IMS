package cn.rootyu.ims.basisData.dao;

import java.util.List;

import cn.rootyu.ims.basisData.entity.ToolsBean;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

/**
 * 工具信息DAO接口
 * @author 李闯
 *
 */
@MyBatisDao
public interface ToolsDao extends CrudDao<ToolsBean> {
	
	List<String> findTypeList(ToolsBean tools);
	
	int batchDelete(List<String> list);
}

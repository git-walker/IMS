package cn.rootyu.ims.basisData.service;

import java.util.List;

import cn.rootyu.rad.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rootyu.ims.basisData.dao.ToolsDao;
import cn.rootyu.ims.basisData.entity.ToolsBean;

/**
 * 工具service
 * @author 
 *
 */
@Service
@Transactional(readOnly = true)
public class ToolsService extends CrudService<ToolsDao, ToolsBean> {
	/**
	 * 查询工具类型列表
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new ToolsBean());
	}
	/**
	 * 保存修改数据
	 */
	public void save(ToolsBean tools){
		super.save(tools);
	}
	/**
	 * 删除数据
	 */
	@Transactional(readOnly = false)
	public void delete(ToolsBean tools){
		super.delete(tools);
	}
	/**
	 * 删除多个数据
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids){
		for(int i = 0;i < ids.length; i++){
			ToolsBean tools =new ToolsBean(ids[i]);
			super.delete(tools);
		}
	}
}

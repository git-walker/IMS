package cn.rootyu.ims.basisData.dao;

import cn.rootyu.ims.basisData.entity.Project;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;


@MyBatisDao
public interface InvcllDao extends CrudDao<Project> {

		List<String> findTypeList(Project project);
		
		int batchDelete(List<String> list);

}




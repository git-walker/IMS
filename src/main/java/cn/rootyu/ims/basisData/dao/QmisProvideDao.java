package cn.rootyu.ims.basisData.dao;

import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;
import cn.rootyu.ims.basisData.entity.QmisProvide;

import java.util.List;

@MyBatisDao
public interface QmisProvideDao extends CrudDao<QmisProvide> {
    //查询类型列表
    List<String> findTypeList();
    //查询车型列表
    List<String> findLabelList();
}

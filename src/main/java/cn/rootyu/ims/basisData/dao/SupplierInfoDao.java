package cn.rootyu.ims.basisData.dao;

import cn.rootyu.ims.basisData.entity.SupplierInfo;
import cn.rootyu.rad.common.persistence.CrudDao;
import cn.rootyu.rad.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * 供应商信息DAO
 */
@MyBatisDao
public interface SupplierInfoDao extends CrudDao<SupplierInfoDao> {


    //获取供应商下拉列表
    List<SupplierInfo> findDropDownList(SupplierInfo param);

}

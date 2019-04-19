package cn.rootyu.ims.purchase.dao;

import cn.rootyu.ims.purchase.entity.SupplierDir;
import cn.rootyu.ims.purchase.entity.SupplierDirDetail;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface SupplierDirDao {

    List<SupplierDir> selectSupplierDirList(Map<String,Object> params);

    void supplierDirDel(String[] idArray);

    void updateSupplierDir(SupplierDir supplier);

    List<SupplierDirDetail> selectSupplierDirDetailList(Map<String,Object> params);

    void addSupplierDir(SupplierDir dir);

    List<SupplierDirDetail> unCheckedSupplierPageList(Map<String, Object> params);

    void batchInsertSupplierDirDetail(List<SupplierDirDetail> list);


    void delSupplierDirDetail(String[] idArray);

    List<SupplierDirDetail> selectSupplierHistoryDirList(Map<String, Object> params);
}

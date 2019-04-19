package cn.rootyu.ims.purchase.dao;

import cn.rootyu.ims.purchase.entity.Supplier;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface SupplierDao {

    List<Supplier> selectSupplierList(Map<String,Object> params);

    void removeSuppliers(String[] idArray);

    void updateSupplier(Supplier supplier);

    void addSupplier(Supplier supplier);

    List<Supplier> getAllSupplier();
}

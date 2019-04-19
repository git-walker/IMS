package cn.rootyu.ims.purchase.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.purchase.dao.SupplierDao;
import cn.rootyu.ims.purchase.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SupplierManageService
 * @Description 供应商Service
 * @Author yuhui
 * @Date 2019/4/14 15:36
 * @Version 1.0
 */
@Service
public class SupplierManageService {

    @Autowired
    private SupplierDao supplierDao;

    public LayuiPageInfo<Supplier> getSupplierPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<Supplier> list = supplierDao.selectSupplierList(params);
        for(Supplier it:list){
            it.setQualifiedSituation("");//TODO
        }
        return new LayuiPageInfo<>(list);
    }
    public Supplier getSupplier(String id) {
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<Supplier> list = supplierDao.selectSupplierList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new Supplier();
    }

    public void delSuppliers(String[] idArray) {
        supplierDao.removeSuppliers(idArray);
    }

    public void updateSupplier(Supplier supplier) {
        supplier.preUpdate();
        supplierDao.updateSupplier(supplier);
    }

    public void addSupplier(Supplier supplier) {
        supplier.preInsert();
        supplier.setUnuseFlag("0");
        supplierDao.addSupplier(supplier);
    }

    /**
     * 查询所有供应商
     * @return
     */
    public List<Supplier> getAllSupplier(){
        return supplierDao.getAllSupplier();
    }

}

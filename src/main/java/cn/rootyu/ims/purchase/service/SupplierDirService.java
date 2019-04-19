package cn.rootyu.ims.purchase.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.purchase.dao.SupplierDirDao;
import cn.rootyu.ims.purchase.entity.SupplierDir;
import cn.rootyu.ims.purchase.entity.SupplierDirDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SupplierDirService
 * @Description 合格供方名录维护
 * @Author yuhui
 * @Date 2019/4/14 16:24
 * @Version 1.0
 */
@Service
public class SupplierDirService {
    @Autowired
    private SupplierDirDao supplierDirDao;

    public LayuiPageInfo<SupplierDir> getSupplierDirPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<SupplierDir> list = supplierDirDao.selectSupplierDirList(params);
        return new LayuiPageInfo<>(list);
    }

    public void supplierDirDel(String[] idArray) {
        supplierDirDao.supplierDirDel(idArray);
    }

    public void updateSupplierDir(SupplierDir supplierDir) {
        supplierDir.preUpdate();
        supplierDirDao.updateSupplierDir(supplierDir);
    }

    public SupplierDir getSupplierDir(String id) {
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<SupplierDir> list = supplierDirDao.selectSupplierDirList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new SupplierDir();
    }

    public LayuiPageInfo<SupplierDirDetail> getSupplierDirDetailPageList(Map<String, Object> params) {
        CommonUtil.startPage(params);
        List<SupplierDirDetail> list= supplierDirDao.selectSupplierDirDetailList(params);
        return new LayuiPageInfo<>(list);
    }


    public void addSupplierDir(SupplierDir dir) {
        dir.preInsert();
        supplierDirDao.addSupplierDir(dir);
    }

    public LayuiPageInfo<SupplierDirDetail> unCheckedSupplierPageList(Map<String, Object> params) {
        CommonUtil.startPage(params);
        List<SupplierDirDetail> list=supplierDirDao.unCheckedSupplierPageList(params);
        return new LayuiPageInfo<>(list);
    }

    @Transactional
    public void checkQualifiedSupplier(String[] idArray,String supplierDirId, boolean isCheck) {
        if (isCheck){//勾选
            List<SupplierDirDetail> list=new ArrayList<>();
            for (int i=0;i<idArray.length;i++){
                SupplierDirDetail detail=new SupplierDirDetail();
                detail.preInsert();
                detail.setSupplierId(idArray[i]);
                detail.setSupplierDirId(supplierDirId);
                list.add(detail);
            }
            supplierDirDao.batchInsertSupplierDirDetail(list);
        }else{
            //取消勾选
            supplierDirDao.delSupplierDirDetail(idArray);
        }
    }

    public LayuiPageInfo<SupplierDirDetail> supplierHistoryDirPageList(Map<String, Object> params) {
        CommonUtil.startPage(params);
        List<SupplierDirDetail> list = supplierDirDao.selectSupplierHistoryDirList(params);
        return new LayuiPageInfo<>(list);
    }
}

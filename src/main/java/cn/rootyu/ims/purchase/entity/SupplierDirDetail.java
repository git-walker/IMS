package cn.rootyu.ims.purchase.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName SupplierDirDetail
 * @Description 供方名录明细
 * @Author yuhui
 * @Date 2019/4/14 16:28
 * @Version 1.0
 */
public class SupplierDirDetail extends DataEntity<SupplierDirDetail> {

    private String supplierId;
    private String supplierDirId;
    private String supplierName;
    private String theme;
    private String createByName;//创建人

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierDirId() {
        return supplierDirId;
    }

    public void setSupplierDirId(String supplierDirId) {
        this.supplierDirId = supplierDirId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }
}

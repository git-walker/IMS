package cn.rootyu.ims.purchase.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName Supplier
 * @Description 供应商
 * @Author yuhui
 * @Date 2019/4/14 15:20
 * @Version 1.0
 */
public class Supplier extends DataEntity<Supplier> {

    private String dutyMan;//联系人
    private String phone;//联系方式
    private String address;//联系地址
    private String supplierDesc;//供方描述
    private String fullName;//供应商全称
    private String briefName;//简称
    private String effectiveDate;//生效日期
    private String expireDate;//过期日期
    private String unuseFlag;//是否有效
    private String qualifiedSituation;//合格供方情况

    public String getDutyMan() {
        return dutyMan;
    }

    public void setDutyMan(String dutyMan) {
        this.dutyMan = dutyMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupplierDesc() {
        return supplierDesc;
    }

    public void setSupplierDesc(String supplierDesc) {
        this.supplierDesc = supplierDesc;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getUnuseFlag() {
        return unuseFlag;
    }

    public void setUnuseFlag(String unuseFlag) {
        this.unuseFlag = unuseFlag;
    }

    public String getQualifiedSituation() {
        return qualifiedSituation;
    }

    public void setQualifiedSituation(String qualifiedSituation) {
        this.qualifiedSituation = qualifiedSituation;
    }
}

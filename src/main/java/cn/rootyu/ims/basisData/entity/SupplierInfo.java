package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/**
 * 供应商信息实体
 */
public class SupplierInfo extends DataEntity<SupplierInfo> {

    private static final long serialVersionUID = 1L;

    private String splName; //供应商名称

    private String shortName;  //供应商简称

    public String getSplName() {
        return splName;
    }

    public void setSplName(String splName) {
        this.splName = splName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}

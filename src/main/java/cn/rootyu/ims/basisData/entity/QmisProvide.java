package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/*
 * 售后供应商维护
 */
public class QmisProvide extends DataEntity<QmisProvide> {

    private String splName;	//供应商名称
    private String delFlag;	// 删除标志
    private String belFlag;	// 归属大铁路（1）还是城轨（2）

    public String getSplName() {
        return splName;
    }

    public void setSplName(String splName) {
        this.splName = splName;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getBelFlag() {
        return belFlag;
    }

    public void setBelFlag(String belFlag) {
        this.belFlag = belFlag;
    }
}

package cn.rootyu.ims.resourceManage.entity;

import cn.rootyu.rad.common.entity.DataEntity;


/**
 * @ClassName Repo
 * @Description 仓库实体类
 * @Author yuhui
 * @Date 2019/4/13 14:14
 * @Version 1.0
 */
public class Repo extends DataEntity<Repo>{

    private String repoName;//仓库名称
    private String repoCode;//仓库编号
    private Integer status;//是否可用
    private String repoAddress;//仓库地址
    private String keeperId;//仓库管理员Id
    private String storekeeper;//仓库管理员

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoCode() {
        return repoCode;
    }

    public void setRepoCode(String repoCode) {
        this.repoCode = repoCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRepoAddress() {
        return repoAddress;
    }

    public void setRepoAddress(String repoAddress) {
        this.repoAddress = repoAddress;
    }

    public String getKeeperId() {
        return keeperId;
    }

    public void setKeeperId(String keeperId) {
        this.keeperId = keeperId;
    }

    public String getStorekeeper() {
        return storekeeper;
    }

    public void setStorekeeper(String storekeeper) {
        this.storekeeper = storekeeper;
    }
}

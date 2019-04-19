package cn.rootyu.ims.stock.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName Stock
 * @Description 库存实体类
 * @Author yuhui
 * @Date 2019/4/18 11:45
 * @Version 1.0
 */
public class Stock extends DataEntity<Stock> {

    private String goodsId;//商品
    private String goodsName;//商品名称
    private String repoId;//仓库id
    private String repoName;//仓库名称
    private Integer count;//库存数量

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

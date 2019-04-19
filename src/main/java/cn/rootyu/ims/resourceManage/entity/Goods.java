package cn.rootyu.ims.resourceManage.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName Goods
 * @Description 商品类
 * @Author yuhui
 * @Date 2019/4/13 14:11
 * @Version 1.0
 */
public class Goods extends DataEntity<Goods> {

    private String goodsName;//商品名称
    private String goodsCode;//商品编码
    private String goodsType;//商品类型
    private String goodsBrand;//商品品牌(供应商id)
    private String brandName;//品牌
    private String specification; //规格型号
    private String buyPrice;//预设进价
    private String salePrice;//预设售价
    private Integer status;//商品状态
    private String picture;//商品图片
    private Integer cnt;//数量

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}

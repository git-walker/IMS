package cn.rootyu.ims.order.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName OrderItem
 * @Description 订单明细表
 * @Author yuhui
 * @Date 2019/4/14 20:57
 * @Version 1.0
 */
public class OrderItem extends DataEntity<OrderItem> {

    private String orderId;//订单id
    private String goodsId;//商品id
    private String goodsNm;//商品名称
    private String supplierId;//供应商id
    private String supplierName;//供应商名称
    private Double goodsPrice;//商品价格
    private Integer count;//商品数量
    private String creater;//创建人

    public OrderItem() {
    }

    public OrderItem(String orderId, String goodsId, String supplierId, Integer count) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.supplierId = supplierId;
        this.count = count;
    }

    public String getGoodsNm() {
        return goodsNm;
    }

    public void setGoodsNm(String goodsNm) {
        this.goodsNm = goodsNm;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

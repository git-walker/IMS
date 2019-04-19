package cn.rootyu.ims.dataManage.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName StockInfo
 * @Description 库存详情
 * @Author yuhui
 * @Date 2019/4/19 15:32
 * @Version 1.0
 */
public class StockInfo extends DataEntity<StockInfo> {

    private String goodsName;//商品
    private Integer count;//数量

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

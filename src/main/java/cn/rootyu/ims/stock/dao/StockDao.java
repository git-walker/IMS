package cn.rootyu.ims.stock.dao;

import cn.rootyu.ims.stock.entity.Stock;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface StockDao {

    /**
     * 库存列表
     * @param params
     * @return
     */
    List<Stock> selectStockList(Map<String,Object> params);

    /**
     * 根据仓库查库存
     * @param repoId
     * @return
     */
    List<Stock> selectStockListByRepoId(String repoId);

    /**
     * 根据仓库和商品查商品库存数量
     * @param params
     * @return
     */
    Stock getGoodsStock(Map<String,Object> params);

    void addStock(Stock stock);

    void updateStock(Stock stock);
}

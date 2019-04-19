package cn.rootyu.ims.resourceManage.dao;

import cn.rootyu.ims.resourceManage.entity.Goods;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface GoodsDao {

    List<Goods> selectGoodsList(Map<String,Object> params);

    void addGoods(Goods goods);

    void updateGoods(Goods goods);

    void removeGoods(String[] idArray);

    /**
     * 查询供应商提供的所有在售商品
     */
    List<Goods> selectGoodsBySupplierId(String supplierId);

    List<Map<String,Object>> getDefaultGoods(Map<String, Object> params);

    /**
     * 查询当前仓库所有在售商品
     */
    List<Goods> getAllGoodsByRepoId(String repoId);
}

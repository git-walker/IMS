package cn.rootyu.ims.resourceManage.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.resourceManage.dao.GoodsDao;
import cn.rootyu.ims.resourceManage.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GoodsService
 * @Description 商品Service
 * @Author yuhui
 * @Date 2019/4/13 14:12
 * @Version 1.0
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public LayuiPageInfo<Goods> getGoodsPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<Goods> list = goodsDao.selectGoodsList(params);
        return new LayuiPageInfo<>(list);
    }

    public Goods getGoods(String id){
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<Goods> list = goodsDao.selectGoodsList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new Goods();
    }


    public void addGoods(Goods goods, List<MultipartFile> imgAttach, String imgAttachDesc) {
        goods.preInsert();
        goodsDao.addGoods(goods);
    }

    public void updateGoods(Goods goods) {
        goods.preUpdate();
        goodsDao.updateGoods(goods);
    }

    public void removeGoods(String[] idArray) {
        goodsDao.removeGoods(idArray);
    }

    /**
     * 当前供应商下提供的所有在售产品
     * @param supplierId
     * @return
     */
    public List<Goods> selectGoodsBySupplierId(String supplierId){
        return goodsDao.selectGoodsBySupplierId(supplierId);
    }

    public List<Map<String,Object>> getDefaultGoods(Map<String, Object> params){
        return goodsDao.getDefaultGoods(params);
    }
}

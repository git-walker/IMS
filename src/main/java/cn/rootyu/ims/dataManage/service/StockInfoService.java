package cn.rootyu.ims.dataManage.service;

import cn.rootyu.ims.dataManage.dao.StockInfoDao;
import cn.rootyu.ims.dataManage.entity.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName StockInfoService
 * @Description 库存Service
 * @Author yuhui
 * @Date 2019/4/19 15:21
 * @Version 1.0
 */
@Service
public class StockInfoService {

    @Autowired
    private StockInfoDao stockInfoDao;

    public List<StockInfo> queryStockInfo(String repoId){
        return stockInfoDao.queryStockInfo(repoId);
    }
}

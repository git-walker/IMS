package cn.rootyu.ims.dataManage.dao;

import cn.rootyu.ims.dataManage.entity.StockInfo;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;

@MyBatisDao
public interface StockInfoDao {

    List<StockInfo> queryStockInfo(String repoId);
}

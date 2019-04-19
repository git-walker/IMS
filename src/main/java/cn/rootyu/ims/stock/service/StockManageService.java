package cn.rootyu.ims.stock.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.order.dao.OrderDao;
import cn.rootyu.ims.order.entity.Order;
import cn.rootyu.ims.order.entity.OrderItem;
import cn.rootyu.ims.resourceManage.dao.GoodsDao;
import cn.rootyu.ims.resourceManage.entity.Goods;
import cn.rootyu.ims.stock.dao.StockDao;
import cn.rootyu.ims.stock.entity.Stock;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName StockManageService
 * @Description 库存管理Service
 * @Author yuhui
 * @Date 2019/4/18 11:49
 * @Version 1.0
 */
@Service
public class StockManageService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 库存列表
     * @param params
     * @return
     */
    public LayuiPageInfo<Stock> getStockPageList(Map<String, Object> params){
        CommonUtil.startPage(params);
        List<Stock> list = stockDao.selectStockList(params);
        return new LayuiPageInfo<>(list);
    }

    /**
     * 订单明细
     * @param params
     * @return
     */
    public LayuiPageInfo<OrderItem> getGoodsListPageList(Map<String, Object> params) {
        CommonUtil.startPage(params);
        String orderId = (String) params.get("order");
        Order order = orderDao.getOrderByOrderId(orderId);
        List<OrderItem> list = Lists.newArrayList();
        if (order.getOrderType()==0){//采购订单
            list = orderDao.selectPurchaseOrderGoodsList(orderId);
        }else if (order.getOrderType()==1){//销售订单
            list = orderDao.selectSaleOrderGoodsList(orderId);
        }
        return new LayuiPageInfo<>(list);
    }

    /**
     * 审核订单
     * @param params
     */
    public void  checkOrder(Map<String,Object> params){
        String id = (String)params.get("orderId");
        String checkResult = (String)params.get("checkResult");
        String checkNote = (String)params.get("checkNote");
        Order order = new Order();
        order.setId(id);
        order.setStatus(3);//审核完成(订单状态:审核中——>审核完成)
        order.setCheckResult(checkResult);//审核结果(Y--通过,N--未通过)
        order.setCheckNote(checkNote);//审核意见
        order.setCheckManId(UserUtils.getUser().getId());//审核人:当前用户id
        order.setCheckDate(new Date());//审核时间:当前审核时间
        order.preUpdate();
        orderDao.checkOrder(order);
    }

    public void changeStock(String orderId){
        Order order = orderDao.getOrderByOrderId(orderId);//获取订单
        String repoId = order.getOrderRepoId();//仓库id
        List<Stock> stockList = stockDao.selectStockListByRepoId(repoId);//根据仓库查库存记录
        List<OrderItem> orderItemList;
        if (order.getOrderType()==0){//订单采购订单
            orderItemList = orderDao.selectPurchaseOrderGoodsList(orderId);
            List<OrderItem> orderItemList1 = Lists.newArrayList();
            List<OrderItem> orderItemList2 = Lists.newArrayList();//新增库存
            List<Stock> stockList1 = Lists.newArrayList();
            List<Stock> stockList2 = Lists.newArrayList();//新增库存记录
            List<Stock> stockList3 = Lists.newArrayList();//修改现有库存记录
            List<String> goodsIdList1 = Lists.newArrayList();
            List<String> goodsIdList2 = Lists.newArrayList();
            List<String> goodsIdList3 = Lists.newArrayList();
            for (int i=0;i<orderItemList.size();i++){
                goodsIdList1.add(orderItemList.get(i).getGoodsId());//订单明细中所有goodsId
            }
            for(int j=0;j<stockList.size();j++){
                goodsIdList2.add(stockList.get(j).getGoodsId());//当前仓库中所有goodsId
            }
            for(int i=0;i<goodsIdList2.size();i++){
                for (int j=0;j<goodsIdList1.size();j++){
                    if (goodsIdList2.get(i).equals(goodsIdList1.get(j))){
                        goodsIdList3.add(goodsIdList1.get(j));//库存中存在采购商品记录
                    }
                }
            }
            goodsIdList1.removeAll(goodsIdList3);//新增库存记录
            for (int i=0;i<goodsIdList1.size();i++){
                for (int j=0;j<orderItemList.size();j++){
                    if (Objects.equals(goodsIdList1.get(i),orderItemList.get(j).getGoodsId())){
                        orderItemList2.add(orderItemList.get(j));
                    }
                }
            }
            for (int i=0;i<goodsIdList3.size();i++){
                for (int k=0;k<stockList.size();k++){
                    if (Objects.equals(goodsIdList3.get(i),stockList.get(k).getGoodsId())){
                        stockList1.add(stockList.get(k));
                    }
                }
            }
            for (int i=0;i<goodsIdList3.size();i++){
                for (int j=0;j<orderItemList.size();j++){
                    if (Objects.equals(goodsIdList3.get(i),orderItemList.get(j).getGoodsId())){
                        orderItemList1.add(orderItemList.get(j));
                    }
                }
            }
            for (int i=0;i<orderItemList1.size();i++){
                Stock stock1 = new Stock();
                stock1.setRepoId(repoId);
                for (int k=0;k<stockList1.size();k++){
                    if (Objects.equals(orderItemList1.get(i).getGoodsId(),stockList1.get(k).getGoodsId())){
                        stock1.setGoodsId(orderItemList1.get(i).getGoodsId());
                        stock1.setCount(stockList.get(k).getCount()+orderItemList.get(i).getCount());
                        stock1.setId(stockList1.get(k).getId());
                        stockList3.add(stock1);
                    }
                }
            }
            //新增库存
            for (int i=0;i<orderItemList2.size();i++){
                Stock stock1 = new Stock();
                stock1.setRepoId(repoId);
                stock1.setGoodsId(orderItemList2.get(i).getGoodsId());
                stock1.setCount(orderItemList2.get(i).getCount());
                stockList2.add(stock1);
            }
            if (stockList3.size()>0){
                for (Stock stock:stockList3){
                    updateStock(stock);
                }
            }
            if (stockList2.size()>0){
                for (Stock stock:stockList2){
                    addStock(stock);
                }
            }
        } else if (order.getOrderType()==1){//销售订单
            orderItemList = orderDao.selectSaleOrderGoodsList(orderId);
            for (Stock stock:stockList){
                Stock stock1 = new Stock();
                stock1.setRepoId(repoId);
                for (int j=0;j<orderItemList.size();j++){
                    if (Objects.equals(stock.getGoodsId(),orderItemList.get(j).getGoodsId())){//该仓库存在该商品库存记录，修改库存记录
                        stock1.setGoodsId(orderItemList.get(j).getGoodsId());
                        stock1.setCount(stock.getCount()-orderItemList.get(j).getCount());//更新库存:库存-销售
                        stock1.setId(stock.getId());
                        updateStock(stock1);
                    }else{//不存在该商品记录,那肯定是销售订单出错

                    }
                }
            }
        }
    }

    /**
     * 新增库存记录
     * @param stock
     */
    public void addStock(Stock stock){
        stock.preInsert();
        stockDao.addStock(stock);
    }

    /**
     * 更新库存记录
     * @param stock
     */
    public void updateStock(Stock stock){
        stock.preUpdate();
        stockDao.updateStock(stock);
    }

    /**
     * 当前仓库所有在售产品
     * @return
     */
    public List<Goods> getAllGoodsByRepoId(String repoId){
        return goodsDao.getAllGoodsByRepoId(repoId);
    }

    /**
     * 根据仓库和商品查商品库存数量
     * @param params
     * @return
     */
    public Stock getGoodsStock(Map<String,Object> params){
        return stockDao.getGoodsStock(params);
    }

    public void rejectOrder(String orderId){
        Order order = new Order();
        order.setStatus(1);//审核中——>待审核
        order.setId(orderId);
        order.setCheckResult("N");//审核结果(Y--通过,N--未通过)
        order.setCheckNote("订单驳回");//审核意见
        order.setCheckManId(UserUtils.getUser().getId());//审核人:当前用户id
        order.setCheckDate(new Date());//审核时间:当前审核时间
        order.preUpdate();
        orderDao.rejectOrder(order);
    }
}

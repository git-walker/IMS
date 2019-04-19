package cn.rootyu.ims.order.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.order.dao.OrderDao;
import cn.rootyu.ims.order.entity.Order;
import cn.rootyu.rad.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderService
 * @Description 订单Service
 * @Author yuhui
 * @Date 2019/4/14 20:57
 * @Version 1.0
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    /**
     * 订单查询
     * @param params
     * @return
     */
    public LayuiPageInfo<Order> getOrderPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<Order> list = orderDao.selectOrderList(params);
        return new LayuiPageInfo<>(list);
    }

    /**
     * 批量查询订单
     * @param idArray
     * @return
     */
    public List<Order> getOrderList(String[] idArray){
        return orderDao.getOrderList(idArray);
    }

    /**
     * 采购订单查询
     * @param params
     * @return
     */
    public LayuiPageInfo<Order> getPurchaseOrderPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<Order> list = orderDao.selectPurchaseOrderList(params);
        return new LayuiPageInfo<>(list);
    }

    /**
     * 销售订单查询
     * @param params
     * @return
     */
    public LayuiPageInfo<Order> getSaleOrderPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<Order> list = orderDao.selectSaleOrderList(params);
        return new LayuiPageInfo<>(list);
    }

    /**
     * 采购订单
     * @param id
     * @return
     */
    public Order getPurchaseOrder(String id){
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<Order> list = orderDao.selectPurchaseOrderList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new Order();
    }

    /**
     * 销售订单
     * @param id
     * @return
     */
    public Order getSaleOrder(String id){
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<Order> list = orderDao.selectSaleOrderList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new Order();
    }

    /**
     * 订单
     * @param id
     * @return
     */
    public Order getOrder(String id){
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<Order> list = orderDao.selectOrderList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new Order();
    }

    /**
     * 查订单
     * @param orderId
     * @return
     */
    public Order getOrderByOrderId(String orderId){
        return orderDao.getOrderByOrderId(orderId);
    }

    public void addOrder(Order order) {
        order.preInsert();
        if (order.getOrderType() == 0) {//采购订单,客户id存本公司1006
            order.setCustomerId(BaseEntity.ERP_CORP_ID);
        }
        order.setStatus(0);//录入中
        orderDao.addOrder(order);
    }

    public void updateOrder(Order order) {
        order.preUpdate();
        orderDao.updateOrder(order);
    }

    public void removeOrder(String[] idArray) {
        orderDao.removeOrder(idArray);
        //同时删除订单明细表相关记录
        orderDao.removeRelativeOrderItem(idArray);
    }

    //提交订单审核
    public void commitOrder(String[] idArray){
        orderDao.commitOrder(idArray);
    }
}

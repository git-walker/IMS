package cn.rootyu.ims.order.dao;

import cn.rootyu.ims.order.entity.Order;
import cn.rootyu.ims.order.entity.OrderItem;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface OrderDao {

    /**
     * 审核中和审核完成订单查询
     * @param params
     * @return
     */
    List<Order> selectOrderList(Map<String,Object> params);

    /**
     * 采购订单
     * @param params
     * @return
     */
    List<Order> selectPurchaseOrderList(Map<String,Object> params);

    /**
     * 批量查询采购订单
     * @param idArray
     * @return
     */
    List<Order> getOrderList(String[] idArray);

    /**
     * 销售订单查询
     * @param params
     * @return
     */
    List<Order> selectSaleOrderList(Map<String,Object> params);

    /**
     * 查订单
     * @param orderId
     * @return
     */
    Order getOrderByOrderId(String orderId);

    void addOrder(Order order);

    void updateOrder(Order order);

    void completeOrder(Order order);

    void removeOrder(String[] idArray);

    void commitOrder(String[] idArray);

    void checkOrder(Order order);

    void rejectOrder(Order order);

    /*********************以下订单明细表相关*************************/

    /**
     * 删除订单同时删除订单明细表相关记录
     * @param idArray
     */
    void removeRelativeOrderItem(String[] idArray);

    /**
     * 采购订单明细
     * @param orderId
     * @return
     */
    List<OrderItem> selectPurchaseOrderGoodsList(String orderId);

    /**
     * 销售订单明细
     * @param orderId
     * @return
     */
    List<OrderItem> selectSaleOrderGoodsList(String orderId);

    /**
     * 采购订单明细(根据主键)
     * @param id
     * @return
     */
    OrderItem selectPurchaseOrderItem(String id);

    /**
     * 销售订单明细(根据主键查)
     * @param id
     * @return
     */
    OrderItem selectSaleOrderItem(String id);

    void addOrderItem(OrderItem orderItem);

    void updateOrderItem(OrderItem orderItem);

    void removeOrderItem(String[] idArray);
}

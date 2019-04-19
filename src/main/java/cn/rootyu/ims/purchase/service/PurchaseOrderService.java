package cn.rootyu.ims.purchase.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.order.dao.OrderDao;
import cn.rootyu.ims.order.entity.Order;
import cn.rootyu.ims.order.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PurchaseOrderService
 * @Description 采购订单Service
 * @Author yuhui
 * @Date 2019/4/15 15:15
 * @Version 1.0
 */
@Service
public class PurchaseOrderService {
    @Autowired
    private OrderDao orderDao;

    public LayuiPageInfo<OrderItem> getGoodsListPageList(Map<String, Object> params) {
        CommonUtil.startPage(params);
        String orderId = (String) params.get("order");
        List<OrderItem> list = orderDao.selectPurchaseOrderGoodsList(orderId);
        return new LayuiPageInfo<>(list);
    }

    public List<OrderItem> haveAnyGoods(String orderId) {
        return orderDao.selectPurchaseOrderGoodsList(orderId);
    }

    public void completeOrder(String orderId){
        Order order = new Order();
        List<OrderItem> itemList = orderDao.selectPurchaseOrderGoodsList(orderId);
        double totalPrice = 0;
        for (OrderItem orderItem:itemList){
            totalPrice+=orderItem.getGoodsPrice()*orderItem.getCount();
        }
        order.setId(orderId);
        order.setTotalPrice(totalPrice);
        order.setStatus(1);//录入-->待审核
        order.preUpdate();
        orderDao.completeOrder(order);
    }

    public OrderItem getOrderItem(String id) {
        return orderDao.selectPurchaseOrderItem(id);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.preInsert();
        orderDao.addOrderItem(orderItem);
    }

    public void updateOrderItem(OrderItem orderItem){
        orderItem.preUpdate();
        orderDao.updateOrderItem(orderItem);
    }

    public void delOrderItem(String[] idArray) {
        orderDao.removeOrderItem(idArray);
    }
}

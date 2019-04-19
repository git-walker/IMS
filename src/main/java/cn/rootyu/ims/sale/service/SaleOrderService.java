package cn.rootyu.ims.sale.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.order.dao.OrderDao;
import cn.rootyu.ims.order.entity.Order;
import cn.rootyu.ims.order.entity.OrderItem;
import cn.rootyu.rad.common.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SaleOrderService
 * @Description 销售订单Service
 * @Author yuhui
 * @Date 2019/4/16 18:08
 * @Version 1.0
 */
@Service
public class SaleOrderService {
    @Autowired
    private OrderDao orderDao;

    public LayuiPageInfo<OrderItem> getGoodsListPageList(Map<String, Object> params) {
        CommonUtil.startPage(params);
        String orderId = (String) params.get("order");
        List<OrderItem> list = orderDao.selectSaleOrderGoodsList(orderId);
        return new LayuiPageInfo<>(list);
    }

    public List<OrderItem> haveAnyGoods(String orderId) {
        return orderDao.selectSaleOrderGoodsList(orderId);
    }

    public void completeOrder(String orderId){
        Order order = new Order();
        List<OrderItem> itemList = orderDao.selectSaleOrderGoodsList(orderId);
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
        return orderDao.selectSaleOrderItem(id);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.preInsert();
        String supplierId = ConstantUtils.ERP_IMS_ID;
        orderItem.setSupplierId(supplierId);//本公司作为供应商
        orderDao.addOrderItem(orderItem);
    }

    public void updateOrderItem(OrderItem orderItem){
        orderItem.preUpdate();
        String supplierId = ConstantUtils.ERP_IMS_ID;
        orderItem.setSupplierId(supplierId);//本公司作为供应商
        orderDao.updateOrderItem(orderItem);
    }

    public void delOrderItem(String[] idArray) {
        orderDao.removeOrderItem(idArray);
    }
}

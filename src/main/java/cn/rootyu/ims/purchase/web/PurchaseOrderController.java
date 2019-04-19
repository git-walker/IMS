package cn.rootyu.ims.purchase.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.order.entity.Order;
import cn.rootyu.ims.order.entity.OrderItem;
import cn.rootyu.ims.order.service.OrderService;
import cn.rootyu.ims.purchase.service.PurchaseOrderService;
import cn.rootyu.ims.purchase.service.SupplierManageService;
import cn.rootyu.ims.resourceManage.entity.Goods;
import cn.rootyu.ims.resourceManage.entity.Repo;
import cn.rootyu.ims.resourceManage.service.GoodsService;
import cn.rootyu.ims.resourceManage.service.RepoService;
import cn.rootyu.rad.common.web.BaseController;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PurchaseOrderController
 * @Description 采购订单Controller
 * @Author yuhui
 * @Date 2019/4/14 22:16
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/purchase/order")
public class PurchaseOrderController extends BaseController {

    @Autowired
    private RepoService repoService;
    @Autowired
    private SupplierManageService supplierManageService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 采购订单列表
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Repo> repoList = repoService.getAllRepo();
        model.addAttribute("repoList",repoList);
        return "ims/purchase/order/purchaseOrderList";
    }

    /**
     * 采购订单列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "purchaseOrderList")
    @ResponseBody
    public LayuiPageInfo purchaseOrderList(@RequestBody Map<String,Object> params){
        LayuiPageInfo<Order> pageInfo=null;
        try{
            pageInfo=orderService.getPurchaseOrderPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    @RequestMapping(value = "initAddOrder")
    public String initAddOrder(Model model) {
        List<Repo> repoList = repoService.getAllRepo();
        model.addAttribute("repoList",repoList);
        return "ims/purchase/order/purchaseOrderAdd";
    }

    @RequestMapping(value = "initEditOrder")
    public String initEditOrder(String id, HttpServletRequest request, Model model) {
        Order order= orderService.getPurchaseOrder(id);
        request.setAttribute("o",order);
        List<Repo> repoList = repoService.getAllRepo();
        model.addAttribute("repoList",repoList);
        return "ims/purchase/order/purchaseOrderEdit";
    }

    /**
     * 新增采购订单
     * @param order
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addOrder")
    @ResponseBody
    public ResultBean addOrder(Order order) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            orderService.addOrder(order);
            resultBean.setData(order.getId());//回传id
        }catch (DuplicateKeyException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("记录已存在");
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }
    /**
     * 修改采购订单
     * @param order
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateOrder")
    @ResponseBody
    public ResultBean updateOrder(Order order) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(order.getId(),"更新主键不能为空");
            orderService.updateOrder(order);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }

    /**
     * 删除采购订单信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delOrder")
    @ResponseBody
    public ResultBean delOrder(String id) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            orderService.removeOrder(idArray);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }

    /**
     * 提交采购订单信息审核
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "commitOrder")
    @ResponseBody
    public ResultBean commitOrder(String id) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            boolean flag = true;
            List<Order> orderList = orderService.getOrderList(idArray);
            for (Order order:orderList){
                if (order.getStatus()==0||order.getStatus()==2||order.getStatus()==3){
                    flag = false;
                }
            }
            if (flag){//订单当前状态允许提交
                orderService.commitOrder(idArray);
            }else{
                resultBean.setSuccess(false);
                resultBean.setMsg("操作失败,无法提交！");
            }
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }

    /* ********************************************以下订单商品信息维护***********************************/
    /**
     * 采购订单的商品信息维护
     * @param orderId
     * @param request
     * @return
     */
    @RequestMapping(value = "orderGoodsList")
    public String list(String orderId, HttpServletRequest request) {
        request.setAttribute("orderId",orderId);
        return "ims/purchase/order/orderItem";
    }

    /**
     * 添加商品页面
     * @param model
     * @return
     */
    @RequestMapping("initGoodsAdd")
    public String initGoodsAdd(Model model){
        model.addAttribute("supplierList",supplierManageService.getAllSupplier());
        return "ims/purchase/order/orderGoodsEdit";
    }

    /**
     * 供应商-在售商品
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "getGoods")
    @ResponseBody
    public List<Goods> getGoods(String supplierId){
        List<Goods> goodsList = goodsService.selectGoodsBySupplierId(supplierId);
        return goodsList;
    }

    @RequestMapping(value = "getDefaultGoods")
    @ResponseBody
    public List<Map<String,Object>> getDefaultGoods(String id,String supplierId){
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        params.put("supplierId",supplierId);
        List<Map<String,Object>> goodsList = goodsService.getDefaultGoods(params);
        return goodsList;
    }

    /**
     * 修改商品页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("initGoodsEdit")
    public String initGoodsEdit(String id, Model model){
        model.addAttribute("supplierList",supplierManageService.getAllSupplier());
        model.addAttribute("o", purchaseOrderService.getOrderItem(id));
        return "ims/purchase/order/orderGoodsEdit";
    }

    @RequestMapping(value = "goodsPageList")
    @ResponseBody
    public LayuiPageInfo goodsList(@RequestBody Map<String,Object> params) throws Exception{
        LayuiPageInfo<OrderItem> pageInfo=null;
        try{
            pageInfo=purchaseOrderService.getGoodsListPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    @RequestMapping(value = "haveAnyGoods")
    @ResponseBody
    public Map<String,Object> haveAnyGoods(String orderId) throws Exception{
        Map<String,Object> mapList = Maps.newHashMap();
        List<OrderItem> orderItemList = purchaseOrderService.haveAnyGoods(orderId);
        mapList.put("count",orderItemList.size());
        return mapList;
    }

    @RequestMapping(value = "getOrderStatus")
    @ResponseBody
    public Map<String,Object> getOrderStatus(String orderId) throws Exception{
        Map<String,Object> mapList = Maps.newHashMap();
        Order order = orderService.getPurchaseOrder(orderId);
        mapList.put("status",order.getStatus());
        return mapList;
    }

    @RequestMapping(value = "completeOrder")
    @ResponseBody
    public ResultBean completeOrder(String orderId){
        ResultBean resultBean=new ResultBean();
        try {
            Assert.hasText(orderId,"更新主键不能为空");
            purchaseOrderService.completeOrder(orderId);
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }


    @RequestMapping(value = "addGoods")
    @ResponseBody
    public ResultBean addGoods(@RequestBody Map<String,Object> params) throws Exception{
        ResultBean resultBean=new ResultBean();
        OrderItem orderItem = new OrderItem();
        try{
            String orderId = (String)params.get("orderId");
            String supplierId = (String)params.get("supplierId");
            String goodsId = (String)params.get("goodsId");
            String num = (String)params.get("count");
            Integer count = Integer.valueOf(num).intValue();
            orderItem.setOrderId(orderId);
            orderItem.setSupplierId(supplierId);
            orderItem.setGoodsId(goodsId);
            orderItem.setCount(count);
            purchaseOrderService.addOrderItem(orderItem);
            resultBean.setData(orderItem.getId());
        }catch (DuplicateKeyException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("记录已存在");
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setMsg(e.getMessage());
            resultBean.setSuccess(false);
        }
        return resultBean;
    }

    @RequestMapping(value = "updateGoods")
    @ResponseBody
    public ResultBean updateGoods(@RequestBody Map<String,Object> params) throws Exception{
        ResultBean resultBean=new ResultBean();
        OrderItem orderItem = new OrderItem();
        try{
            String id = (String)params.get("id");
            Assert.hasText(id,"更新主键不能为空");
            String orderId = (String)params.get("orderId");
            String supplierId = (String)params.get("supplierId");
            String goodsId = (String)params.get("goodsId");
            String num = (String)params.get("count");
            Integer count = Integer.valueOf(num).intValue();
            orderItem.setOrderId(orderId);
            orderItem.setSupplierId(supplierId);
            orderItem.setGoodsId(goodsId);
            orderItem.setCount(count);
            orderItem.setId(id);
            purchaseOrderService.updateOrderItem(orderItem);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (IllegalStateException e){
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

    @RequestMapping(value = "delGoods")
    @ResponseBody
    public ResultBean delGoods(String id) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            purchaseOrderService.delOrderItem(idArray);
        }catch (IllegalArgumentException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("非法参数异常："+e.getMessage());
        }catch (NullPointerException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("NPE异常："+e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultBean.setSuccess(false);
        }
        return resultBean;
    }
}

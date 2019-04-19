package cn.rootyu.ims.stock.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.order.entity.Order;
import cn.rootyu.ims.order.entity.OrderItem;
import cn.rootyu.ims.order.service.OrderService;
import cn.rootyu.ims.resourceManage.entity.Repo;
import cn.rootyu.ims.resourceManage.service.RepoService;
import cn.rootyu.ims.sale.entity.Customer;
import cn.rootyu.ims.sale.service.CustomerService;
import cn.rootyu.ims.stock.service.StockManageService;
import cn.rootyu.rad.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderReviewController
 * @Description 订单审核Controller
 * @Author yuhui
 * @Date 2019/4/18 11:46
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/stockManage/orderReview")
public class OrderReviewController extends BaseController {

    @Autowired
    private RepoService repoService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StockManageService stockManageService;

    /**
     * 订单列表
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Repo> repoList = repoService.getAllRepo();//仓库
        List<Customer> customerList = customerService.getAllCustomer();//客户
        model.addAttribute("repoList",repoList);
        model.addAttribute("customerList",customerList);
        return "ims/stock/orderReview/orderReview";
    }

    /**
     * 订单列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "orderList")
    @ResponseBody
    public LayuiPageInfo orderList(@RequestBody Map<String,Object> params){
        LayuiPageInfo<Order> pageInfo=null;
        try{
            pageInfo=orderService.getOrderPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    /* ********************************************以下订单明细***********************************/
    /**
     * 订单的商品信息
     * @param orderId
     * @param request
     * @return
     */
    @RequestMapping(value = "orderGoodsList")
    public String list(String orderId, HttpServletRequest request) {
        request.setAttribute("orderId",orderId);
        return "ims/stock/orderReview/orderItemReview";
    }


    /**
     * 订单明细
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "goodsPageList")
    @ResponseBody
    public LayuiPageInfo goodsList(@RequestBody Map<String,Object> params) throws Exception{
        LayuiPageInfo<OrderItem> pageInfo=null;
        try{
            pageInfo=stockManageService.getGoodsListPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }


    /* ********************************************以下订单审核***********************************/

    @RequestMapping(value = "reviewForm")
    public String reviewForm(){
        return "ims/stock/orderReview/reviewForm";
    }

    @RequestMapping(value = "checkOrder")
    @ResponseBody
    public ResultBean checkOrder(@RequestBody Map<String,Object> params){
        ResultBean resultBean = new ResultBean();
        try {
            String orderId = (String)params.get("orderId");
            Assert.hasText(orderId,"主键不能为空");
            String checkResult = (String)params.get("checkResult");
            if ("Y".equals(checkResult)){//审核通过,库存变化
                stockManageService.changeStock(orderId);
            }
            stockManageService.checkOrder(params);
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

    /***************************订单驳回***************************/

    @RequestMapping(value = "rejectOrder")
    @ResponseBody
    public ResultBean checkOrder(String orderId) {
        ResultBean resultBean = new ResultBean();
        try {
            Assert.hasText(orderId,"主键不能为空");
            stockManageService.rejectOrder(orderId);
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
}

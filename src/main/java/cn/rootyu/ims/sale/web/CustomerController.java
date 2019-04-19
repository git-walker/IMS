package cn.rootyu.ims.sale.web;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.entity.ResultBean;
import cn.rootyu.ims.sale.entity.Customer;
import cn.rootyu.ims.sale.service.CustomerService;
import cn.rootyu.rad.common.web.BaseController;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName CustomerController
 * @Description 客户Controller
 * @Author yuhui
 * @Date 2019/4/16 16:03
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/sale/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    /**
     * 客户列表
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "ims/sale/customer/customerList";
    }

    /**
     * 导航到客户添加页面
     * @param request
     * @return
     */
    @RequestMapping(value = "initCustomerAdd")
    public String initCustomerAdd(HttpServletRequest request) {
        request.setAttribute("currentUserName", UserUtils.getUser().getName());
        return "ims/sale/customer/customerAdd";
    }

    /**
     * 导航到客户编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "initCustomerEdit")
    public String initCustomerEdit(String id,String readonly,HttpServletRequest request) {
        Customer customer= customerService.getCustomer(id);
        request.setAttribute("customer",customer);
        if ("true".equals(readonly)){
            request.setAttribute("readonly",true);
        }
        return "ims/sale/customer/customerEdit";
    }

    /**
     * 列表查询
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "customerList")
    @ResponseBody
    public LayuiPageInfo customerList(@RequestBody Map<String,Object> params) throws Exception{
        LayuiPageInfo<Customer> pageInfo=null;
        try{
            pageInfo=customerService.getCustomerPageList(params);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageInfo;
    }

    /**
     * 新增客户
     * @param customer
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "customerAdd")
    @ResponseBody
    public ResultBean addSupplier(Customer customer) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            customerService.addCustomer(customer);
        }catch (DuplicateKeyException e){
            resultBean.setSuccess(false);
            resultBean.setMsg("年度名录已存在");
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
     * 更新客户
     * @param customer
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "customerUpdate")
    @ResponseBody
    public ResultBean updateSupplier(Customer customer) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(customer.getId(),"更新主键不能为空");
            customerService.updateCustomer(customer);
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
     * 删除客户
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "customersDelete")
    @ResponseBody
    public ResultBean delCustomers(String id) throws Exception{
        ResultBean resultBean=new ResultBean();
        try{
            Assert.hasText(id,"id不能为空");
            String idArray[]=id.split(",");
            customerService.delCustomers(idArray);
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

package cn.rootyu.ims.sale.service;

import cn.rootyu.ims.common.entity.LayuiPageInfo;
import cn.rootyu.ims.common.utils.CommonUtil;
import cn.rootyu.ims.sale.dao.CustomerDao;
import cn.rootyu.ims.sale.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CustomerService
 * @Description 客户Service
 * @Author yuhui
 * @Date 2019/4/16 16:03
 * @Version 1.0
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public LayuiPageInfo<Customer> getCustomerPageList(Map<String,Object> params){
        CommonUtil.startPage(params);
        List<Customer> list = customerDao.selectCustomerList(params);
        return new LayuiPageInfo<>(list);
    }
    public Customer getCustomer(String id) {
        Map<String, Object> params=new HashMap<>();
        params.put("id", id);
        List<Customer> list = customerDao.selectCustomerList(params);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return new Customer();
    }

    public void delCustomers(String[] idArray) {
        customerDao.removeCustomers(idArray);
    }

    public void updateCustomer(Customer Customer) {
        Customer.preUpdate();
        customerDao.updateCustomer(Customer);
    }

    public void addCustomer(Customer Customer) {
        Customer.preInsert();
        customerDao.addCustomer(Customer);
    }

    /**
     * 查询所有客户
     * @return
     */
    public List<Customer> getAllCustomer(){
        return customerDao.getAllCustomer();
    }
}

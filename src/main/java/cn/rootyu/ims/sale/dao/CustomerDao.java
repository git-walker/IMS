package cn.rootyu.ims.sale.dao;

import cn.rootyu.ims.sale.entity.Customer;
import cn.rootyu.rad.common.dao.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface CustomerDao {
    List<Customer> selectCustomerList(Map<String,Object> params);

    void removeCustomers(String[] idArray);

    void updateCustomer(Customer customer);

    void addCustomer(Customer customer);

    List<Customer> getAllCustomer();
}

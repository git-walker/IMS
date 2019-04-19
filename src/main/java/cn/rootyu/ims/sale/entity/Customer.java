package cn.rootyu.ims.sale.entity;

import cn.rootyu.rad.common.entity.DataEntity;

/**
 * @ClassName Customer
 * @Description 客户实体类
 * @Author yuhui
 * @Date 2019/4/16 15:41
 * @Version 1.0
 */
public class Customer extends DataEntity<Customer> {

    private String customerName;//客户名称
    private String linkMan;//联系人
    private String phone;//联系方式
    private String address;//联系地址

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

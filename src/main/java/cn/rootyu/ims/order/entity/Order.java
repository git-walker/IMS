package cn.rootyu.ims.order.entity;

import cn.rootyu.rad.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @ClassName Order
 * @Description 订单实体类
 * @Author yuhui
 * @Date 2019/4/14 20:54
 * @Version 1.0
 */
public class Order extends DataEntity<Order> {

    private String orderCode;//订单编号
    private String customerId;//客户id
    private String customer;//客户
    private String orderRepoId;//出/入库ID
    private String orderRepo;//出/入库
    private Double totalPrice;//订单总价
    private String checkManId;//审核人ID
    private String checkManName;//审核人
    private Date checkDate;
    private String checkResult;//审核结果(Y--通过,N--未通过)
    private String checkNote;//审核意见
    private Integer status;//订单状态
    private Integer orderType;//订单类型(0--采购订单，1--销售订单)

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderRepoId() {
        return orderRepoId;
    }

    public void setOrderRepoId(String orderRepoId) {
        this.orderRepoId = orderRepoId;
    }

    public String getOrderRepo() {
        return orderRepo;
    }

    public void setOrderRepo(String orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCheckManId() {
        return checkManId;
    }

    public void setCheckManId(String checkManId) {
        this.checkManId = checkManId;
    }

    public String getCheckManName() {
        return checkManName;
    }

    public void setCheckManName(String checkManName) {
        this.checkManName = checkManName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckNote() {
        return checkNote;
    }

    public void setCheckNote(String checkNote) {
        this.checkNote = checkNote;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}

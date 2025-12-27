package com.p.bce.shopping.cart.rpc.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private String userId;
    private BigDecimal totalAmount;
    private Date orderDate;
    private String status; // PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO() {
        super();
    }

    public OrderDTO(int orderId, String userId, BigDecimal totalAmount, Date orderDate, String status) {
        super();
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDTO [orderId=" + orderId + ", userId=" + userId + ", totalAmount=" + totalAmount
                + ", orderDate=" + orderDate + ", status=" + status + "]";
    }
}


package com.github.renny.order.system.modules.order.dto.response;

import com.github.renny.order.system.modules.order.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UpdateOrderStatusResponse {
    private String orderName;
    private String orderNum;
    private BigDecimal orderAmount;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UpdateOrderStatusResponse(String orderName,String orderNum,BigDecimal orderAmount,OrderStatus orderStatus,LocalDateTime createdAt,LocalDateTime updatedAt){
        this.orderName = orderName;
        this.orderNum = orderNum;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getOrderName(){ return orderName; }
    public String getOrderNum(){ return orderNum; }
    public BigDecimal getOrderAmount(){ return orderAmount; }
    public OrderStatus getOrderStatus(){ return orderStatus; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public LocalDateTime getUpdatedAt(){ return updatedAt; }
}

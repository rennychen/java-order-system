package com.github.renny.order.system.modules.order.dto.response;

import com.github.renny.order.system.modules.order.enums.OrderStatus;

import java.time.LocalDateTime;

public class CreatedOrderResponse {
    private String orderName;
    private String orderNum;
    private int orderAmount;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    public CreatedOrderResponse(String orderName,String orderNum,int orderAmount,OrderStatus orderStatus,LocalDateTime createdAt){
        this.orderName = orderName;
        this.orderNum = orderNum;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public String getOrderName(){ return orderName; }
    public String getOrderNum(){ return orderNum; }
    public int getOrderAmount(){ return orderAmount; }
    public OrderStatus getOrderStatus(){ return orderStatus; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
}

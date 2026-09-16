package com.github.renny.order.system.modules.order.dto.response;


import com.github.renny.order.system.modules.order.enums.OrderStatus;

import java.time.LocalDateTime;

public class ReadOrderResponse {
    private String orderName;
    private String orderNum;
    private int orderAmount;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updetedAt;

    public ReadOrderResponse(String orderName,String orderNum,int orderAmount,OrderStatus orderStatus,LocalDateTime createdAt,LocalDateTime updetedAt){
        this.orderName = orderName;
        this.orderNum = orderNum;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updetedAt = updetedAt;
    }

    public String getOrderName(){ return orderName; }
    public String getOrderNum(){ return orderNum; }
    public int getOrderAmount(){ return orderAmount; }
    public OrderStatus getOrderStatus(){ return orderStatus; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public LocalDateTime getUpdetedAt(){ return updetedAt; }
}

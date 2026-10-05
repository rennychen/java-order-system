package com.github.renny.order.system.modules.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrderRequest {
    @NotBlank(message = "訂單名稱不得為空")
    private String orderName;
    @NotEmpty(message = "訂單清單不得為空")
    private List<OrderItemRequest> items;

    public String getOrderName(){ return orderName; }
    public List<OrderItemRequest> getItems() { return items; }

    public void setOrderName(String orderName){
        this.orderName = orderName;
    }
    public void setItems(List<OrderItemRequest> items){ this.items = items; }
}

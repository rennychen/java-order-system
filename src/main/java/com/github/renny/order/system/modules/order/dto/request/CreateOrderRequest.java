package com.github.renny.order.system.modules.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateOrderRequest {
    @NotBlank(message = "訂單名稱不得為空")
    private String orderName;
    @Schema(
            description = "訂單金額",
            example = "1500"
    )
    @NotNull( message = "價錢不得為空" )
    @Positive(message = "價錢必須大於0")
    private Integer orderAmount;

    public String getOrderName(){ return orderName; }
    public Integer getOrderAmount(){ return orderAmount; }

    public void setOrderName(String orderName){
        this.orderName = orderName;
    }
    public void setOrderAmount(Integer orderAmount){
        this.orderAmount = orderAmount;
    }
}

package com.github.renny.order.system.modules.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class CreateOrderRequest {
    @NotBlank(message = "訂單名稱不得為空")
    private String orderName;
    @Schema(
            description = "訂單金額",
            example = "1500"
    )
    @DecimalMin(value = "0",inclusive = false,message = "金額需大於0")
    private BigDecimal orderAmount;

    public String getOrderName(){ return orderName; }
    public BigDecimal getOrderAmount(){ return orderAmount; }

    public void setOrderName(String orderName){
        this.orderName = orderName;
    }
    public void setOrderAmount(BigDecimal orderAmount){
        this.orderAmount = orderAmount;
    }
}

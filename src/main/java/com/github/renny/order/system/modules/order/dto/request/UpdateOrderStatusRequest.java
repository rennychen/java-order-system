package com.github.renny.order.system.modules.order.dto.request;

import com.github.renny.order.system.common.exception.InvalidStatusTransitionException;
import com.github.renny.order.system.modules.order.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusRequest {
    @Schema(
            description = "訂單狀態(為 Enum OrderStatus )",
            example = "orderStatus.PAID"
    )
    @NotNull(message = "訂單狀態不得為空")
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus(){ return orderStatus; }

    public void setOrderStatus(OrderStatus orderStatus){ this.orderStatus = orderStatus; }

    public void validate(){
        if(this.orderStatus == OrderStatus.CANCELLED){
            throw new InvalidStatusTransitionException("請選擇刪除訂單選項");
        }
    }
}

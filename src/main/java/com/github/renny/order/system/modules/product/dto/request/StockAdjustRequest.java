package com.github.renny.order.system.modules.product.dto.request;

import com.github.renny.order.system.modules.product.enums.StockAdjustmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StockAdjustRequest {
    @NotNull(message = "欲更改數量不得為空")
    @Positive(message = "數量必須大於0")
    private Integer quantity;
    @NotNull(message = "更改項目不得為空")
    private StockAdjustmentType stockAdjustmentType;

    public Integer getQuantity(){ return quantity; }
    public StockAdjustmentType getStockAdjustmentType(){ return stockAdjustmentType; }

    public void setQuantity(Integer quantity){ this.quantity = quantity; }
    public void setStockAdjustmentType(StockAdjustmentType stockAdjustmentType){ this.stockAdjustmentType = stockAdjustmentType; }
}

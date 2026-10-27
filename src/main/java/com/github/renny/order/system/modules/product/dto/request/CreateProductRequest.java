package com.github.renny.order.system.modules.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateProductRequest {
    @NotBlank(message = "產品名稱不得為空")
    @Size(max = 20,message = "產品名稱不得大於20字")
    private String productName;
    @Size(max = 200,message = "產品描述不得大於200字")
    private String description;
    @NotNull(message = "產品數量不得為空")
    @PositiveOrZero(message = "產品數量不得為負數")
    private Integer quantity;
    @DecimalMin(value = "0",inclusive = false,message = "金額不得小於等於0")
    private BigDecimal price;

    public String getProductName(){ return productName; }
    public String getDescription(){ return description; }
    public Integer getQuantity(){ return quantity; }
    public BigDecimal getPrice(){ return price; }

    public void setProductName(String productName){ this.productName = productName; }
    public void setDescription(String description){ this.description = description; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }
    public void setPrice(BigDecimal price){ this.price = price; }
}

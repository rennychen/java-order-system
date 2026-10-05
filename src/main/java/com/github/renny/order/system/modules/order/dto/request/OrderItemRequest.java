package com.github.renny.order.system.modules.order.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class OrderItemRequest {
    /* TODO: Product 模組建立後版本
    @NotNull(message = "內容不得為空")
    @Schema( description = "產品Id" )
    private Long productId;
    @NotNull(message = "數量不得為空")
    @Positive(message = "價錢必須大於0")
    @Schema( description = "產品數量" )
    private Integer quantiuty;

    public Long getProductId() { return productId; }
    public Integer getQuantiuty() { return quantiuty; }

    public void setProductId(Long productId){ this.productId = productId; }
    public void setQuantiuty(Integer quantiuty){ this.quantiuty = quantiuty; }
    */
    @NotBlank(message = "產品名稱不得為空")
    private String productName;
    @NotBlank(message = "產品描述不得為空")
    @Size(max = 200,message = "產品描述不得超過200字")
    private String description;
    @NotNull(message = "產品數量不得為空")
    @Positive(message = "產品數量需大於0")
    private Integer quantity;
    @NotNull(message = "產品價格不得為空")
    @DecimalMin(value = "0",inclusive = false,message = "產品價錢需大於0")
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

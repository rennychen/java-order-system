package com.github.renny.order.system.modules.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UpdateProductRequest {
    @Size(max = 20,message = "產品名稱不得大於20字")
    @Pattern( regexp = ".*\\S.*", message = "產品名稱不得為空")
    private String productName;
    @Size(max = 200,message = "產品描述不得大於200字")
    private String description;
    @DecimalMin(value = "0",inclusive = false,message = "金額必須大於0元")
    private BigDecimal price;
    // 傳 null 代表不改

    public String getProductName(){ return productName; }
    public String getDescription(){ return description; }
    public BigDecimal getPrice(){ return price; }

    public void setProductName(String productName){ this.productName = productName; }
    public void setDescription(String description){ this.description = description; }
    public void setPrice(BigDecimal price){ this.price = price; }
}

package com.github.renny.order.system.modules.product.dto.response;

import com.github.renny.order.system.modules.product.enums.ProductStatus;

import java.math.BigDecimal;

public class CreateProductResponse {
    private Long id;
    private String productName;
    private String description;
    private int quantity;
    private BigDecimal price;
    private ProductStatus productStatus;

    public CreateProductResponse(Long id,String productName,String description,int quantity,BigDecimal price,ProductStatus productStatus){
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.productStatus = productStatus;
    }

    public Long getId(){ return id; }
    public String getProductName(){ return productName; }
    public String getDescription(){ return description; }
    public int getQuantity(){ return quantity; }
    public BigDecimal getPrice(){ return price; }
    private ProductStatus getProductStatus(){ return productStatus; }
}

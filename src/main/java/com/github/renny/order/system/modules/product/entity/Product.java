package com.github.renny.order.system.modules.product.entity;

import com.github.renny.order.system.common.entity.BaseEntity;
import com.github.renny.order.system.common.exception.BaseException;
import com.github.renny.order.system.common.exception.BusinessException;
import com.github.renny.order.system.modules.product.enums.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column(nullable = false,length = 20)
    private String productName;
    @Column( length = 200 )
    private String description;
    @Column( nullable = false )
    private int quantity;
    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal price;
    @Column( nullable = false )
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus = ProductStatus.PUBLISHED;

    protected Product(){}

    public Product(String productName,String description,int quantity,BigDecimal price){
        validateProductName(productName);
        validateDescription(description);
        validateQuantity(quantity);
        validatePrice(price);

        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName(){ return productName; }
    public String getDescription(){ return description; }
    public int getQuantity(){ return quantity; }
    public BigDecimal getPrice(){ return price; }
    public ProductStatus getProductStatus(){ return productStatus; }

    public void changeProductName(String productName){
        validateProductName(productName);
        this.productName = productName;
    }
    public void updateDescription(String description){
        validateDescription(description);
        this.description =description;
    }
    public void addStock(int quantity){
        validateQuantity(quantity);
        this.quantity += quantity;
    }
    public void deductStock(int quantity){
        validateQuantity(quantity);
        if(this.quantity<quantity){
            throw new IllegalArgumentException("庫存數量不足");
        }
        this.quantity -= quantity;
    }
    public void changePrice(BigDecimal price){
        validatePrice(price);
        this.price = price;
    }

    private void validateProductName(String productName){
        if(productName == null || productName.isBlank()){
            throw new IllegalArgumentException("商品名稱不能為空");
        }
        if(productName.length()>20){
            throw new IllegalArgumentException("商品名稱字數不得大於20字");
        }
    }
    private void validateDescription(String description){
        if(description != null && description.length() > 200){
            throw new IllegalArgumentException("商品描述字數不得大於200字");
        }
    }
    private void validateQuantity(int quantity){
        if(quantity<0){
            throw new BusinessException("商品數量/商品調整數量不得小於0");
        }
    }
    private void validatePrice(BigDecimal price){
        if(price.compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("價格必須大於0元");
        }
    }
}

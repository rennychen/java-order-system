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
    private ProductStatus productStatus = ProductStatus.DRAFT;

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

    public Long getId(){ return id; }
    public String getProductName(){ return productName; }
    public String getDescription(){ return description; }
    public int getQuantity(){ return quantity; }
    public BigDecimal getPrice(){ return price; }
    public ProductStatus getProductStatus(){ return productStatus; }

    public void addStock(int quantity){
        validateChangeQuantity(quantity);
        if (this.quantity > Integer.MAX_VALUE - quantity) {
            throw new BusinessException("商品庫存數量超過最大限制");
        }
        this.quantity += quantity;
    }
    public void deductStock(int quantity){
        validateChangeQuantity(quantity);
        if(this.quantity<quantity){
            throw new BusinessException("庫存數量不足");
        }
        this.quantity -= quantity;
    }
    public void changeProductStatus(ProductStatus productStatus){
        if(productStatus == null){
            throw new BusinessException("更新狀態不得為空");
        }
        if (this.productStatus == productStatus){
            return;
        }
        if (this.productStatus == ProductStatus.DRAFT
                && productStatus == ProductStatus.UNPUBLISHED){
            throw new BusinessException("草稿商品不能更改為下架狀態");
        }
        if (this.productStatus == ProductStatus.ARCHIVED
                && (productStatus == ProductStatus.PUBLISHED
                || productStatus == ProductStatus.UNPUBLISHED
                || productStatus == ProductStatus.DRAFT)){
            throw new BusinessException("已封存商品不能再次變更狀態");
        }
        this.productStatus = productStatus;
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
            throw new BusinessException("商品數量不得小於0");
        }
    }
    private void validateChangeQuantity(int quantity){
        if(quantity<=0){
            throw new BusinessException("商品調整數量不得小於0");
        }
    }
    private void validatePrice(BigDecimal price){
        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("價格必須大於0元,且不得為空值");
        }
    }

    public void updateDetails(String productName,String description,BigDecimal price){
        if(productName != null){
            if(productName.isBlank() || productName.length()>20){
                throw new BusinessException("產品名稱不得為空或是自數>20字");
            }
            validateProductName(productName);
            this.productName = productName;
        }

        if(description != null){
            if(description.length()>200){
                throw new BusinessException("產品描述不得大於200字");
            }
            validateDescription(description);
            this.description = description;
        }  // TODO: 待測試 -> 傳入 "" 是否能正確修正

        if(price != null){
            if(price.compareTo(BigDecimal.ZERO) <= 0){
                throw new BusinessException("價格必須大於0元");
            }
            validatePrice(price);
            this.price = price;
        }
    }
}

package com.github.renny.order.system.modules.orderitem.entity;

import com.github.renny.order.system.common.entity.BaseEntity;
import com.github.renny.order.system.modules.order.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column( nullable = false,length = 20)
    private String productName;
    @Column( length = 200)
    private String description;
    @Column( nullable = false )
    private int quantity;
    @Column( nullable = false,precision = 10,scale = 2)
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    protected OrderItem(){}

    public OrderItem(String productName,String description,int quantity,BigDecimal price,Order order){
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public Long getId(){ return id; }
    public String getProductName(){ return productName; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public Order getOrder() { return order; }

    public void changeProductName(String productName){
        this.productName = productName;
    }
    public void updateDescription(String description){
        this.description = description;
    }
    public void updateQuantity(int quantity){
        this.quantity = quantity;
    }
    public void changePrice(BigDecimal price){
        this.price = price;
    }
}

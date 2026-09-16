package com.github.renny.order.system.modules.order.entity;

import com.github.renny.order.system.common.entity.BaseEntity;
import com.github.renny.order.system.modules.order.enums.OrderStatus;
import com.github.renny.order.system.modules.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "orders" )
public class Order extends BaseEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false )
    private String orderName;
    @Column( nullable = false, unique = true )
    private String orderNum;
    @Column( nullable = false )
    private int totalAmount;
    @Version
    private Long version;
    @Column( nullable = false )
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "user_id", nullable = false )
    private User user;

    protected Order(){};

    public Order(String orderName,int totalAmount,User user){
        this.orderName = orderName;
        this.totalAmount = totalAmount;
        this.user = user;
    }

    @PrePersist
    private void onCreated(){
        if(this.orderNum == null){
            this.orderNum = "ORD" + System.currentTimeMillis() + (int)(Math.random() * 1000);
        }
    }

    public Long getId(){ return id; }
    public String getOrderName(){ return orderName; }
    public String getOrderNum(){ return orderNum; }
    public int getTotalAmount(){ return totalAmount; }
    public OrderStatus getOrderStatus(){ return orderStatus; }
    public User getUser(){ return user; }

    public void updateOrderName(String orderName){
        this.orderName = orderName;
    }
    public void modifyAmount(int totalAmount){
        this.totalAmount = totalAmount;
    }
    public void changeOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
}

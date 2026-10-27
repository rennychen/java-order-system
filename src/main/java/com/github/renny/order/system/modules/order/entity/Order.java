package com.github.renny.order.system.modules.order.entity;

import com.github.renny.order.system.common.entity.BaseEntity;
import com.github.renny.order.system.common.exception.BusinessException;
import com.github.renny.order.system.modules.order.enums.OrderStatus;
import com.github.renny.order.system.modules.user.entity.User;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Column( nullable = false,precision = 12,scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    @Version
    private Long version;
    @Column( nullable = false )
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "user_id", nullable = false )
    private User user;
    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order(){};

    public Order(String orderName,User user){
        this.orderName = orderName;
        this.user = user;
    }

    @PrePersist
    private void onCreated(){
        if(this.orderNum == null){
            this.orderNum = "ORD" + System.currentTimeMillis() + (int)(Math.random() * 1000);
        }
    }

    public void addOrderItem(OrderItem item){
        orderItems.add(item);
        item.setOrder(this);
    }

    public void calculateTotalAmount(){
        this.totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public Long getId(){ return id; }
    public String getOrderName(){ return orderName; }
    public String getOrderNum(){ return orderNum; }
    public BigDecimal getTotalAmount(){ return totalAmount; }
    public OrderStatus getOrderStatus(){ return orderStatus; }
    public User getUser(){ return user; }

    public void changeOrderName(String orderName){
        if(orderName == null || orderName.isEmpty()){
            throw new IllegalArgumentException("訂單名稱不能為空");
        }
        this.orderName = orderName;
    }
    public void changeOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
    // TODO: refactor status--判斷式移到這
}

package com.github.renny.order.system.modules.order.service;

import com.github.renny.order.system.common.exception.InvalidStatusTransitionException;
import com.github.renny.order.system.common.exception.ResourceNotFoundException;
import com.github.renny.order.system.modules.order.dto.request.CreateOrderRequest;
import com.github.renny.order.system.modules.order.dto.request.UpdateOrderStatusRequest;
import com.github.renny.order.system.modules.order.dto.response.CreatedOrderResponse;
import com.github.renny.order.system.modules.order.dto.response.ReadOrderResponse;
import com.github.renny.order.system.modules.order.dto.response.UpdateOrderStatusResponse;
import com.github.renny.order.system.modules.order.entity.Order;
import com.github.renny.order.system.modules.order.enums.OrderStatus;
import com.github.renny.order.system.modules.order.repository.OrderRepository;
import com.github.renny.order.system.modules.user.entity.User;
import com.github.renny.order.system.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreatedOrderResponse createdOrder(Long userId, CreateOrderRequest request){
        log.info("準備建立新訂單,userId: {}",userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶"));

        Order order = new Order(request.getOrderName(),request.getOrderAmount(),user);
        Order saveOrder = orderRepository.save(order);
        log.info("訂單建立成功,orderNum: {}",saveOrder.getOrderNum());
        return new CreatedOrderResponse(
                saveOrder.getOrderName(),
                saveOrder.getOrderNum(),
                saveOrder.getTotalAmount(),
                saveOrder.getOrderStatus(),
                saveOrder.getCreatedAt()
                );
    }

    public Page<ReadOrderResponse> readOrder(Long userId, Pageable pageable){
        log.info("準備查詢訂單,userId: {}",userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶"));
        Page<Order> pageOrder = orderRepository.findByUserId(userId,pageable);
        log.info("查詢訂單成功");
        return pageOrder.map(order -> new ReadOrderResponse(
                order.getOrderName(),
                order.getOrderNum(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        ));
    }

    /*
        TODO: status狀態是否拆成 pay(),ship(),cancel()
         User 有權限修改所有 Order Status?
         user -> User 有權限修改所有 Order Status
         Admin / Staff -> 可以更新訂單狀態
         目前updateAt取得的是舊資料,問題要再修正
         ---
         取消訂單不走 updateOrderStatus,直接進deleteOrder方法
     */
    @Transactional
    public UpdateOrderStatusResponse updateOrderStatus(Long userId,String orderNum, UpdateOrderStatusRequest request){
        log.info("準備更新訂單狀態,userId: {},orderNum: {}",userId,orderNum);
        Order order = orderRepository.findByOrderNumAndUserId(orderNum,userId)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶或找不到該訂單號碼"));
        OrderStatus currentStatus = order.getOrderStatus();
        OrderStatus targetStatus = request.getOrderStatus();
        if(currentStatus == OrderStatus.CANCELLED || currentStatus == OrderStatus.COMPLETED){
            throw new InvalidStatusTransitionException("已取消/已完成的訂單無法變更狀態");
        }
        if (targetStatus == OrderStatus.COMPLETED && currentStatus != OrderStatus.SHIPPED){
            throw new InvalidStatusTransitionException("訂單狀態更新錯誤,完成訂單前請確認當前狀態是否為'已出貨'");
        }
        if( currentStatus == targetStatus){
            throw new InvalidStatusTransitionException("訂單狀但已是當前狀態");
        }
        if(currentStatus == OrderStatus.SHIPPED && targetStatus != OrderStatus.COMPLETED){
            throw new InvalidStatusTransitionException("無法更改已出貨訂單狀態");
        }
        if(currentStatus == OrderStatus.PAID && targetStatus == OrderStatus.PENDING){
            throw new InvalidStatusTransitionException("已付款的訂單無法退回待處理");
        }
        if(currentStatus == OrderStatus.PENDING && targetStatus == OrderStatus.SHIPPED){
            throw new InvalidStatusTransitionException("更改訂單狀態失敗,請確認該訂單是否完成付款");
        }
        order.changeOrderStatus(request.getOrderStatus());
        log.info("成功更改訂單狀態");
        return new UpdateOrderStatusResponse(
                order.getOrderName(),
                order.getOrderNum(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
                );
    }

    @Transactional
    public void deleteOrder(Long userId,String orderNum){
        log.info("準備取消訂單,userId: {},orderNum: {}",userId,orderNum);
        Order order = orderRepository.findByOrderNumAndUserId(orderNum,userId)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶或找不到該訂單號碼"));
        if(order.getOrderStatus() == OrderStatus.SHIPPED){
            throw new InvalidStatusTransitionException("已出貨的訂單無法取消");
        }
        order.changeOrderStatus(OrderStatus.CANCELLED);
        order.markAsDelete();
        log.info("該訂單已執行軟刪除");
    }
}

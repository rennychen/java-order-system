package com.github.renny.order.system.modules.order.controller;

import com.github.renny.order.system.common.response.ApiResponse;
import com.github.renny.order.system.modules.order.dto.request.CreateOrderRequest;
import com.github.renny.order.system.modules.order.dto.request.UpdateOrderStatusRequest;
import com.github.renny.order.system.modules.order.dto.response.CreatedOrderResponse;
import com.github.renny.order.system.modules.order.dto.response.ReadOrderResponse;
import com.github.renny.order.system.modules.order.dto.response.UpdateOrderStatusResponse;
import com.github.renny.order.system.modules.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(
        name = "Order",
        description = "訂單系統相關 API"
)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    /*  TODO: token解析id未上
     */
    @Operation(
            summary = "建立訂單",
            description = "此 API 用於建立已登入之使用者訂單",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<CreatedOrderResponse>> createdOrder(@PathVariable Long userId,
                                                          @Valid @RequestBody CreateOrderRequest request){
        CreatedOrderResponse response = orderService.createdOrder(userId,request);
        return ResponseEntity.ok(ApiResponse.success("訂單建立成功",response));
    }

    @Operation(
            summary = "查詢使用者訂單",
            description = "此 API 用於查詢已經入之使用者的所有訂單",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Page<ReadOrderResponse>>> readOrder(@PathVariable Long userId,
                                                                          @PageableDefault(
                                                                                  page = 0,
                                                                                  size = 10,
                                                                                  sort = "createdAt",
                                                                                  direction = Sort.Direction.DESC
                                                                          )Pageable pageable){
        Page<ReadOrderResponse> response = orderService.readOrder(userId,pageable);
        return ResponseEntity.ok(ApiResponse.success("查詢訂單成功",response));
    }

    @Operation(
            summary = "更改訂單狀態",
            description = "此 API 用於更改已成立訂單之訂單狀態",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/{userId}/{orderNum}/status")
    public ResponseEntity<ApiResponse<UpdateOrderStatusResponse>> updateOrderStatus(@PathVariable Long userId,
                                                                                    @PathVariable String orderNum,
                                                                                    @Valid @RequestBody UpdateOrderStatusRequest request){
        request.validate();
        UpdateOrderStatusResponse response = orderService.updateOrderStatus(userId,orderNum,request);
        return ResponseEntity.ok(ApiResponse.success("訂單狀態更新成功",response));
    }

    @Operation(
            summary = "刪除訂單",
            description = "此 API 用於刪除已成立之訂單",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/{userId}/{orderNum}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long userId,
                                                         @PathVariable String orderNum){
        orderService.deleteOrder(userId,orderNum);
        return ResponseEntity.ok(ApiResponse.success("成功取消訂單",null));
    }
}

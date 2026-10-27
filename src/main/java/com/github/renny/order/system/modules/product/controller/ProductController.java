package com.github.renny.order.system.modules.product.controller;

import com.github.renny.order.system.common.response.ApiResponse;
import com.github.renny.order.system.modules.product.dto.request.ChangeProductStatusRequest;
import com.github.renny.order.system.modules.product.dto.request.StockAdjustRequest;
import com.github.renny.order.system.modules.product.dto.request.CreateProductRequest;
import com.github.renny.order.system.modules.product.dto.request.UpdateProductRequest;
import com.github.renny.order.system.modules.product.dto.response.ChangeProductStatusResponse;
import com.github.renny.order.system.modules.product.dto.response.StockAdjustResponse;
import com.github.renny.order.system.modules.product.dto.response.CreateProductResponse;
import com.github.renny.order.system.modules.product.dto.response.ReadProductResponse;
import com.github.renny.order.system.modules.product.dto.response.UpdateProductResponse;
import com.github.renny.order.system.modules.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@Tag(
        name = "Product",
        description = "產品相關 API"
)
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @Operation(
            summary = "建立產品",
            description = "此 API 用於建立產品"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResponse>> createdProduct(@RequestBody @Valid CreateProductRequest request){
        CreateProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("建立產品成功",response));
    }

    @Operation(
            summary = "查詢產品",
            description = "此 API 用於查詢已建立完成之產品"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadProductResponse>> readProduct(@PathVariable Long id){
        ReadProductResponse response = productService.readProduct(id);
        return ResponseEntity.ok(ApiResponse.success("成功查詢產品資訊",response));
    }

    @Operation(
            summary = "更改產品資訊",
            description = "此 API 用於更改產品資訊(產品名稱,產品描述,產品價格)"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateProductResponse>> updateProduct(@PathVariable Long id,
                                                                            @RequestBody @Valid UpdateProductRequest request){
        UpdateProductResponse response = productService.updateProduct(id,request);
        return ResponseEntity.ok(ApiResponse.success("成功更新產品資料",response ));
    }

    @Operation(
            summary = "更改產品庫存",
            description = "此 API 用於增加或減少產品庫存"
    )
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<StockAdjustResponse>> stockAdjust(@PathVariable Long id,
                                                                        @RequestBody @Valid StockAdjustRequest request){
        StockAdjustResponse response = productService.stockAdjust(id,request);
        return ResponseEntity.ok(ApiResponse.success("庫存調整成功",response));
    }

    @Operation(
            summary = "更改產品狀態",
            description = "此 API 用於變更產品狀態(上架/下架/草稿/封存)"
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ChangeProductStatusResponse>> changeProductStatus(@PathVariable Long id,
                                                                                          @RequestBody @Valid ChangeProductStatusRequest request){
        ChangeProductStatusResponse response = productService.changeProductStatus(id,request);
        return ResponseEntity.ok(ApiResponse.success("變更產品狀態成功",response));
    }

    @Operation(
            summary = "刪除產品",
            description = "此 API 用於變更產品狀態(封存)"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

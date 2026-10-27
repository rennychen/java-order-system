package com.github.renny.order.system.modules.product.dto.request;

import com.github.renny.order.system.modules.product.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;

public class ChangeProductStatusRequest {
    @NotNull(message = "產品狀態不得為空")
    private ProductStatus productStatus;

    public ProductStatus getProductStatus(){ return productStatus; }

    public void setProductStatus(ProductStatus productStatus){ this.productStatus = productStatus; }
}

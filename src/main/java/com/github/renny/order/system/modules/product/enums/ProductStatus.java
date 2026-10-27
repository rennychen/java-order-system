package com.github.renny.order.system.modules.product.enums;

public enum ProductStatus {
    PUBLISHED, // 上架
    UNPUBLISHED, // 下架, 可重新上架
    DRAFT, // 草稿
    ARCHIVED // 封存(停產,不再上架)
}

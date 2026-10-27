package com.github.renny.order.system.modules.product.service;

import com.github.renny.order.system.common.exception.BusinessException;
import com.github.renny.order.system.common.exception.ResourceNotFoundException;
import com.github.renny.order.system.modules.product.dto.request.ChangeProductStatusRequest;
import com.github.renny.order.system.modules.product.dto.request.StockAdjustRequest;
import com.github.renny.order.system.modules.product.dto.request.CreateProductRequest;
import com.github.renny.order.system.modules.product.dto.request.UpdateProductRequest;
import com.github.renny.order.system.modules.product.dto.response.ChangeProductStatusResponse;
import com.github.renny.order.system.modules.product.dto.response.StockAdjustResponse;
import com.github.renny.order.system.modules.product.dto.response.CreateProductResponse;
import com.github.renny.order.system.modules.product.dto.response.ReadProductResponse;
import com.github.renny.order.system.modules.product.dto.response.UpdateProductResponse;
import com.github.renny.order.system.modules.product.entity.Product;
import com.github.renny.order.system.modules.product.enums.ProductStatus;
import com.github.renny.order.system.modules.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request){
        log.info("準備建立產品");
        Product product = new Product(
                request.getProductName(),
                request.getDescription(),
                request.getQuantity(),
                request.getPrice());
        Product saveProduct = productRepository.save(product);
        log.info("建立產品成功,productId: {}",saveProduct.getId());
        return new CreateProductResponse(
                saveProduct.getId(),
                saveProduct.getProductName(),
                saveProduct.getDescription(),
                saveProduct.getQuantity(),
                saveProduct.getPrice(),
                saveProduct.getProductStatus());
    }

    public ReadProductResponse readProduct(Long id){
        log.info("查詢產品資訊,productId: {}",id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該產品"));
        log.info("成功查詢產品資訊");
        return new ReadProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getProductStatus()
        );
    }

    @Transactional
    public UpdateProductResponse updateProduct(Long id, UpdateProductRequest request){
        log.info("準備更新產品資訊, productId: {}",id);
        if(request.getProductName() == null && request.getDescription() == null && request.getPrice() == null){
            throw new BusinessException("尚未填入更新資訊,請重新嘗試");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該產品"));

        product.updateDetails(request.getProductName(),request.getDescription(),request.getPrice());

        log.info("更新產品資訊成功, productId: {}, productName: {}, productDescription: {}, productPrice: {}",product.getId(),product.getProductName(),product.getDescription(),product.getPrice());
        return new UpdateProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getProductStatus()
        );
    }

    @Transactional
    public StockAdjustResponse stockAdjust(Long id, StockAdjustRequest request){
        log.info("準備調整, productId: {}, stockAdjustType: {}",id,request.getStockAdjustmentType());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該產品,請重新嘗試"));

        switch (request.getStockAdjustmentType()){
            case ADD -> product.addStock(request.getQuantity());
            case DEDUCT -> product.deductStock(request.getQuantity());
            default -> throw new BusinessException("不支援的庫存調整型態");
        }

        log.info("產品數量新增完畢, productQuantity: {}",product.getQuantity());
        return new StockAdjustResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getProductStatus()
        );
    }

    @Transactional
    public ChangeProductStatusResponse changeProductStatus(Long id, ChangeProductStatusRequest request){
        log.info("準備變更產品狀態, productId: {}",id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該產品,請重新嘗試"));
        product.changeProductStatus(request.getProductStatus());
        log.info("產品狀態更新完成");
        return new ChangeProductStatusResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getProductStatus()
        );
    }

    @Transactional
    public void deleteProduct(Long id){
        log.info("準備進行商品軟刪除(封存), productId: {}",id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該產品,請重新嘗試"));
        product.markAsDelete();
        product.changeProductStatus(ProductStatus.DRAFT);
        log.info("產品封存成功");
    }
}

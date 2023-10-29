package com.enima.tokonyadia.service;

import com.enima.tokonyadia.entity.ProductPrice;

public interface ProductPriceService {
    ProductPrice create(ProductPrice productPrice);

    ProductPrice getById(String id);

    ProductPrice findProductPriceActive(String productId , Boolean active);
}

package com.enima.tokonyadia.service;

import com.enima.tokonyadia.entity.Product;
import com.enima.tokonyadia.model.request.ProductRequest;
import com.enima.tokonyadia.model.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product getById(String id);
    List<Product> getAll();
    Product update(Product product);
    void deleteById(String id);
    ProductResponse createProduct(ProductRequest request);

    Page<ProductResponse> getAllByNameOrPrice(String name , Long maxPrice , Integer page , Integer size);
}

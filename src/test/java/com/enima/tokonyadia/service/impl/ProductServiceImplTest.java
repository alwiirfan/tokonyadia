package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Product;
import com.enima.tokonyadia.entity.ProductPrice;
import com.enima.tokonyadia.entity.Store;
import com.enima.tokonyadia.model.request.ProductRequest;
import com.enima.tokonyadia.model.response.ProductResponse;
import com.enima.tokonyadia.repository.ProductRepository;
import com.enima.tokonyadia.service.ProductPriceService;
import com.enima.tokonyadia.service.ProductService;
import com.enima.tokonyadia.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);

    private final ProductPriceService productPriceService = mock(ProductPriceService.class);

    private final StoreService storeService = mock(StoreService.class);

    private final ProductService productService = new ProductServiceImpl(productRepository, storeService, productPriceService);

    @Test
    void create() {
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void createProduct() {
        // data dummy
        // dummy request
        ProductRequest dummyRequest = new ProductRequest();
        dummyRequest.setProductId("product1");
        dummyRequest.setProductName("oreo");
        dummyRequest.setDescription("rasa coklat");
        dummyRequest.setPrice(1000L);
        dummyRequest.setStock(1000);
        dummyRequest.setStoreId("store1");

        // dummy store
        Store dummyStore = new Store();
        dummyStore.setId(dummyRequest.getStoreId());
        dummyStore.setName("testing");
        dummyStore.setAddress("jalan di tutup");

        // dummy product
        Product dummyProduct = new Product();
        dummyProduct.setId(dummyRequest.getProductId());
        dummyProduct.setName(dummyRequest.getProductName());
        dummyProduct.setDescription(dummyRequest.getDescription());

        // dummy product price
        ProductPrice dummyProductPrice = new ProductPrice();
        dummyProductPrice.setStock(dummyRequest.getStock());
        dummyProductPrice.setPrice(dummyRequest.getPrice());
        dummyProductPrice.setProduct(dummyProduct);
        dummyProductPrice.setStore(dummyStore);
        dummyProductPrice.setIsActive(true);

        //mock
        when(storeService.getById(dummyRequest.getStoreId())).thenReturn(dummyStore);
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(dummyProduct);
        when(productPriceService.create(any(ProductPrice.class))).thenReturn(dummyProductPrice);

        // perform
        ProductResponse actualProduct = productService.createProduct(dummyRequest);

        // verify
        verify(storeService, times(1)).getById(dummyRequest.getStoreId());
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
        verify(productPriceService, times(1)).create(any(ProductPrice.class));

        assertEquals(dummyProduct.getName(), actualProduct.getProductName());
        assertEquals(dummyStore.getName(), actualProduct.getStore().getName());
        assertEquals(dummyProductPrice.getPrice(), actualProduct.getPrice());

        // dummy
    }

    @Test
    void itShouldReturnProductResponsePageWhenGetAllByName() {
        // dummy

    }
}
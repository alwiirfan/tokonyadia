package com.enima.tokonyadia.controller;

import com.enima.tokonyadia.entity.Product;
import com.enima.tokonyadia.model.request.ProductRequest;
import com.enima.tokonyadia.model.response.CommonResponse;
import com.enima.tokonyadia.model.response.PagingResponse;
import com.enima.tokonyadia.model.response.ProductResponse;
import com.enima.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public Product createNewProduct(@RequestBody Product request) {
        return productService.create(request);
    }

    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getById(id);
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct(
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(name = "maxPrice",required = false) Long maxPrice,
            @RequestParam(name = "page",required = false , defaultValue = "0") Integer page,
            @RequestParam(name = "size",required = false, defaultValue = "5") Integer size
    ) {
        Page<ProductResponse> productResponses = productService.getAllByNameOrPrice(name,maxPrice,page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(productResponses.getTotalPages())
                .size(size)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all customer")
                        .data(productResponses.getContent())
                        .paging(pagingResponse)
                        .build());
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable String id){
        productService.deleteById(id);
    }


    @PostMapping(value = "/all")
    public ResponseEntity<?> createProductAll(@RequestBody ProductRequest request){
        ProductResponse productResponse = productService.createProduct(request);
     return ResponseEntity.status(HttpStatus.CREATED)
             .body(CommonResponse.<ProductResponse>builder()
                     .statusCode(HttpStatus.CREATED.value())
                     .message("Successfully Create New Product")
                     .data(productResponse)
                     .build());
    }
}

package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Product;
import com.enima.tokonyadia.entity.ProductPrice;
import com.enima.tokonyadia.entity.Store;
import com.enima.tokonyadia.model.request.ProductRequest;
import com.enima.tokonyadia.model.response.ProductResponse;
import com.enima.tokonyadia.model.response.StoreResponse;
import com.enima.tokonyadia.repository.ProductRepository;
import com.enima.tokonyadia.service.ProductPriceService;
import com.enima.tokonyadia.service.ProductService;
import com.enima.tokonyadia.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private StoreService storeService;
    private ProductPriceService productPriceService;


    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(String id)    {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product product) {
        Product currentProduct = getById(product.getId());
        if (currentProduct != null) {
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Store store = storeService.getById(request.getStoreId());

        Product product = Product.builder()
                .name(request.getProductName())
                .description(request.getDescription())
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(request.getPrice())
                .stock(request.getStock())
                .store(store)
                .product(product)
                .isActive(true)
                .build();
        productPriceService.create(productPrice);
        return toProductResponse(store, product, productPrice);
    }

    private static ProductResponse toProductResponse(Store store, Product product, ProductPrice productPrice) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .name(store.getName())
                        .address(store.getAddress())
                        .build())
                .build();
    }

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        Specification<Product> specification = (root, query , criteriaBuilder ) ->{
            Join<Product , ProductPrice> productPrices = root.join("productPrices");
            List<Predicate> predicates = new ArrayList<>();
            if (name != null){
                 predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + name.toLowerCase() + "%"));
            }
            if (maxPrice != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPrices.get("price"), maxPrice));
            }
            return  query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productRepository.findAll(specification,pageable);
        List<ProductResponse> productResponses = new ArrayList<>(); // ini digunakan untuk menyimpan response prodcut
        for (Product product : products.getContent()){
            //for disini digunakan untuk mengiterasi daftar product yang disimpan dalam object
            Optional<ProductPrice> productPrice = product.getProductPrices()//Optonial ini mencoba untuk mencari harga yang aktif
                    .stream()
                    .filter(ProductPrice::getIsActive).findFirst();
            if (productPrice.isEmpty()) continue;//kondisi ini untuk memeriksa apakah productPrice kosong atau tidak ,kalau tidak aktif maka akan di skip
            Store store = productPrice.get().getStore(); // ini digunakan untuk jika harga product yang aktif ditemukan di store ,maka harga akan di masukan kedam store

            productResponses.add(toProductResponse(store,product,productPrice.get()));
        }
        return new PageImpl<>(productResponses,pageable,products.getTotalElements());
    }
}

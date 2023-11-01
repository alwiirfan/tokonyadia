package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Seller;
import com.enima.tokonyadia.repository.SellerRepository;
import com.enima.tokonyadia.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public Seller create(Seller seller) {
        return sellerRepository.save(seller);
    }

}

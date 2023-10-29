package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Store;
import com.enima.tokonyadia.repository.StoreRepository;
import com.enima.tokonyadia.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store getById(String id) {
        return storeRepository.findById(id).get();
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store update(Store store) {
        Store currentStore = getById(store.getId());
        if (currentStore != null){
            return storeRepository.save(store);
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        storeRepository.deleteById(id);
    }
}

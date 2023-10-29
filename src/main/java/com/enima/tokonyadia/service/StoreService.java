package com.enima.tokonyadia.service;

import com.enima.tokonyadia.entity.Store;

import java.util.List;

public interface StoreService {
    Store create(Store store);
    Store getById(String id);
    List<Store> getAll();
    Store update(Store store);
    void deleteById(String id);
}

//crete sama update sama , bedanya itu di requestnya
//kalau create requestnya dia tanpa ID
//kalau update pakai ID.

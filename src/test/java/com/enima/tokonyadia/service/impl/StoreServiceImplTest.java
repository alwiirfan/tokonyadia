package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Store;
import com.enima.tokonyadia.repository.StoreRepository;
import com.enima.tokonyadia.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StoreServiceImplTest {

    // mock repository
    private final StoreRepository storeRepository = mock(StoreRepository.class);

    // create service instance
    private final StoreService storeService = new StoreServiceImpl(storeRepository);

    @Test
    void itShouldReturnStoreWhenCreateNewStore() {
        Store dummyStore = new Store();
        dummyStore.setId("123");
        dummyStore.setName("testing");

        //mock behavior repository
        when(storeRepository.save(any(Store.class))).thenReturn(dummyStore);

        //perform
        Store createStore = storeService.create(dummyStore);

        verify(storeRepository, times(1)).save(dummyStore);

        assertEquals(dummyStore.getId(), createStore.getId());
        assertEquals(dummyStore.getName(), createStore.getName());
    }

    @Test
    void itShouldGetDataStoreOneWhenGetByIdStore() {
        Store dummyStore = new Store();
        dummyStore.setId("123");
        dummyStore.setName("testing");

        // mock behavior storeRepo findById
        when(storeRepository.findById(dummyStore.getId())).thenReturn(Optional.of(dummyStore));

        //perform
        Store actualStore = storeService.getById(dummyStore.getId());

        //verify
        verify(storeRepository, times(1)).findById(dummyStore.getId());

        assertEquals(dummyStore.getId(), actualStore.getId());
        assertEquals(dummyStore.getName(), actualStore.getName());
    }

    @Test
    void itShouldGetAllDataStoreWhenCallGetAll() {
        List<Store> dummyStores = new ArrayList<>();
        dummyStores.add(new Store("1", "123", "Berkah Selalu", "Jalan Buntu", "0934239423"));
        dummyStores.add(new Store("2", "124", "Berkah oke", "Jalan berongga", "080979087890"));
        dummyStores.add(new Store("3", "125", "Berkah jaya", "Jalan rusak", "7562582132"));

        when(storeRepository.findAll()).thenReturn(dummyStores);

        List<Store> actualStores = storeService.getAll();

        verify(storeRepository, times(1)).findAll();

        for (int i = 0; i < dummyStores.size(); i++) {
            assertEquals(dummyStores.get(i).getId(), actualStores.get(i).getId());
            assertEquals(dummyStores.get(i).getName(), actualStores.get(i).getName());
            assertEquals(dummyStores.get(i).getNoSiup(), actualStores.get(i).getNoSiup());
            assertEquals(dummyStores.get(i).getAddress(), actualStores.get(i).getAddress());
            assertEquals(dummyStores.get(i).getMobilePhone(), actualStores.get(i).getMobilePhone());
        }

        assertEquals(dummyStores, actualStores);
    }

    @Test
    void itShouldReturnStoreWhenUpdateStore() {
        Store dummyStore = new Store("1", "123", "Berkah Selalu", "Jalan Buntu", "0934239423");

        when(storeRepository.findById(dummyStore.getId())).thenReturn(Optional.of(new Store("2", "124", "Berkah oke", "Jalan berongga", "080979087890")));
        when(storeRepository.save(dummyStore)).thenReturn(dummyStore);

        Store updateStore = storeService.update(dummyStore);

        verify(storeRepository,times(1)).findById(updateStore.getId());
        verify(storeRepository, times(1)).save(dummyStore);

        assertEquals(dummyStore.getNoSiup(), updateStore.getNoSiup());
        assertEquals(dummyStore.getName(), updateStore.getName());

        System.out.println("Data " + dummyStore.getNoSiup() + " " + updateStore.getNoSiup());
    }

    @Test
    void itShouldDeleteStoreWhenCallDeleteById() {
        String id = "1";
        storeService.deleteById(id);
        verify(storeRepository, times(1)).deleteById(id);
    }
}
package com.enima.tokonyadia.service;

import com.enima.tokonyadia.model.request.OrderRequest;
import com.enima.tokonyadia.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createNewTransaction(OrderRequest orderRequest);
    OrderResponse getOrderById(String id);
    List<OrderResponse> getAllTransaction();
    //update
    //delete
}

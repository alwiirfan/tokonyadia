package com.enima.tokonyadia.controller;

import com.enima.tokonyadia.model.request.OrderRequest;
import com.enima.tokonyadia.model.response.CommonResponse;
import com.enima.tokonyadia.model.response.OrderResponse;
import com.enima.tokonyadia.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/transactions")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createNewTransaction(@RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.createNewTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new transaction")
                        .data(orderResponse)
                        .build());
    }
}

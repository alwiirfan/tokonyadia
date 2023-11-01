package com.enima.tokonyadia.controller;

import com.enima.tokonyadia.model.request.AuthRequest;
import com.enima.tokonyadia.model.request.RegisterSellerRequest;
import com.enima.tokonyadia.model.response.CommonResponse;
import com.enima.tokonyadia.model.response.LoginResponse;
import com.enima.tokonyadia.model.response.RegisterResponse;
import com.enima.tokonyadia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request){
        RegisterResponse register = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<RegisterResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully registered")
                        .data(register)
                        .build());
    }

    @PostMapping(path = "/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request){
        RegisterResponse register = authService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<RegisterResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully registered admin")
                        .data(register)
                        .build());
    }

    @PostMapping(path = "/register/seller")
    public ResponseEntity<?> registerSeller(@RequestBody RegisterSellerRequest request){
        RegisterResponse register = authService.registerSeller(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<RegisterResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully registered seller")
                        .data(register)
                        .build());
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        LoginResponse login = authService.login(request);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success login")
                .data(login)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

}

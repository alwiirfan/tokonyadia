package com.enima.tokonyadia.service;

import com.enima.tokonyadia.model.request.AuthRequest;
import com.enima.tokonyadia.model.request.RegisterSellerRequest;
import com.enima.tokonyadia.model.response.LoginResponse;
import com.enima.tokonyadia.model.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    RegisterResponse registerSeller(RegisterSellerRequest request);
    LoginResponse login(AuthRequest request);

}

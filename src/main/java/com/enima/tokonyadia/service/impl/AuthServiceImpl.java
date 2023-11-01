package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.*;
import com.enima.tokonyadia.entity.contact.ERole;
import com.enima.tokonyadia.model.request.AuthRequest;
import com.enima.tokonyadia.model.request.RegisterSellerRequest;
import com.enima.tokonyadia.model.response.LoginResponse;
import com.enima.tokonyadia.model.response.RegisterResponse;
import com.enima.tokonyadia.repository.UserCredentialRepository;
import com.enima.tokonyadia.security.BCryptUtils;
import com.enima.tokonyadia.security.JwtUtils;
import com.enima.tokonyadia.service.*;
import com.enima.tokonyadia.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptUtils bCryptUtils;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final SellerService sellerService;
    private final StoreService storeService;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final ValidationUtil validationUtil;


    @Override
    public RegisterResponse register(AuthRequest request) {
        try {
            // role
            Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);
            UserCredential userCredential = UserCredential.builder() // membuat user credential
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential); // menyimpan user credential

            Customer customer = Customer.builder() // membuat customer
                    .name(request.getName())
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .mobilePhone(request.getMobilePhone())
                    .userCredential(userCredential)
                    .build();
            customerService.create(customer); // menyimpan customer

            return RegisterResponse.builder() // return register response
                    .email(userCredential.getEmail())
                    .build();

        }catch (DataIntegrityViolationException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            // role
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = Admin.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .userCredential(userCredential)
                    .build();
            adminService.create(admin);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .build();

        }catch (DataIntegrityViolationException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exist");
        }

    }

    @Override
    public RegisterResponse registerSeller(RegisterSellerRequest request) {
        try {
            //role
            Role role = roleService.getOrSave(ERole.ROLE_SELLER);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Store store = Store.builder()
                    .name(request.getStoreName())
                    .mobilePhone(request.getMobilePhone())
                    .address(request.getAddress())
                    .noSiup(request.getNoSiup())
                    .build();
            storeService.create(store);

            Seller seller = Seller.builder()
                    .userName(request.getUsername())
                    .store(store)
                    .userCredential(userCredential)
                    .build();
            sellerService.create(seller);

            return RegisterResponse.builder()
                    .email(request.getEmail())
                    .build();

        }catch (DataIntegrityViolationException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seller already exist");
        }

    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtUtils.generateToken(userDetails.getEmail());
        return LoginResponse.builder()
                .email(userDetails.getEmail())
                .roles(roles)
                .token(token)
                .build();
    }
}

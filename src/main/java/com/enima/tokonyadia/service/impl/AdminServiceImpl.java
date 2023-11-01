package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Admin;
import com.enima.tokonyadia.repository.AdminRepository;
import com.enima.tokonyadia.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }
}

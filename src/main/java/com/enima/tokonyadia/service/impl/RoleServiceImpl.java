package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Role;
import com.enima.tokonyadia.entity.contract.ERole;
import com.enima.tokonyadia.repository.RoleRepository;
import com.enima.tokonyadia.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        return roleRepository.findByRole(role).orElseGet(() -> roleRepository.save(Role.builder()
                .role(role)
                .build()));
    }
}

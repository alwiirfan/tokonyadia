package com.enima.tokonyadia.service;

import com.enima.tokonyadia.entity.Role;
import com.enima.tokonyadia.entity.contract.ERole;

public interface RoleService {
    Role getOrSave(ERole role);
}

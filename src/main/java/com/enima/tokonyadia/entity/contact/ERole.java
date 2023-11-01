package com.enima.tokonyadia.entity.contact;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum ERole {
    ROLE_CUSTOMER,
    ROLE_ADMIN,
    ROLE_SELLER;

    public static ERole get(String value){
        for (ERole eRole : ERole.values()){
            if (eRole.name().equalsIgnoreCase(value)) return eRole;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found");
    }
}

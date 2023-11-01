package com.enima.tokonyadia.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterSellerRequest {

    private String email;
    private String password;
    private String username;
    private String storeName;
    private String mobilePhone;
    private String address;
    private String noSiup;

}

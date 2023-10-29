package com.enima.tokonyadia.model.request;


import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {

    private String productId;
    @NotBlank(message = "product name is required")
    private String productName;
    @NotBlank(message = "description is required")
    private String description;
    @NotNull(message = "price is required")
    @Min(value = 0 ,message = "price must be greater than equal 0")
    private Long price;
    @NotNull(message = "stock is required")
    @Min(value = 0 ,message = "stock must be greater than equal 0")
    private Integer stock;
    @NotBlank(message = "storeId is required")
    private String storeId;
}

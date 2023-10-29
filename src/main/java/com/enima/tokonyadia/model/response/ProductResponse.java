package com.enima.tokonyadia.model.response;

import com.enima.tokonyadia.entity.Store;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String id;
    private String productName;
    private String description;
    private Long price;
    private Integer stock;
    private StoreResponse store;
}

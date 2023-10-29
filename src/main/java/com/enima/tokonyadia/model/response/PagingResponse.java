package com.enima.tokonyadia.model.response;

import lombok.*;

@Getter
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse {
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}

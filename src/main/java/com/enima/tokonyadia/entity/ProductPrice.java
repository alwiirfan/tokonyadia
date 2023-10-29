package com.enima.tokonyadia.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_product_price")
@Builder(toBuilder = true)
public class ProductPrice {

    @Id
    @GenericGenerator(strategy = "uuid2",name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    @Column(columnDefinition = "int check (stock > 0)")
    private Integer stock;
    @Column
    private Boolean isActive;
    @Column(columnDefinition = "bigint check (price > 0)")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}

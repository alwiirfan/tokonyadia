package com.enima.tokonyadia.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "m_store")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class Store {
    @Id
    @GenericGenerator(strategy = "uuid2",name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    @Column(name = "no_siup" , nullable = false , unique = true)
    private String noSiup;
    @Column(name = "name" , nullable = false)
    private String name;
    @Column(name = "address" , nullable = false)
    private String address;
    @Column(name = "mobile_phone" , nullable = false , unique = true)
    private String mobilePhone;
}

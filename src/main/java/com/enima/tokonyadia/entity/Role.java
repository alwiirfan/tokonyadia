package com.enima.tokonyadia.entity;

import com.enima.tokonyadia.entity.contract.ERole;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "m_role")
public class Role {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "role_id")
    private String id;

    @Enumerated(EnumType.STRING)
    private ERole role;

}

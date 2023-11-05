package com.enima.tokonyadia.repository;

import com.enima.tokonyadia.entity.Role;
import com.enima.tokonyadia.entity.contract.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByRole(ERole role);

}

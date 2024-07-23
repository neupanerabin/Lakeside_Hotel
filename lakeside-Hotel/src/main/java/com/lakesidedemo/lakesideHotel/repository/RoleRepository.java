package com.lakesidedemo.lakesideHotel.repository;

import com.lakesidedemo.lakesideHotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * @author : rabin
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleUser);
}

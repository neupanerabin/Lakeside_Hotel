package com.lakesidedemo.lakesideHotel.repository;

import com.lakesidedemo.lakesideHotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * @author : rabin
 */

/**
 * Repository interface for Role entity.
 *
 * This interface extends JpaRepository to provide CRUD operations for the Role entity.
 * It also defines a custom query method to find a Role by its name.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param roleUser The name of the role to find.
     * @return An Optional containing the found role, or empty if no role is found.
     */
    Optional<Role> findByName(String roleUser);

    boolean existsByName(Role role);
}

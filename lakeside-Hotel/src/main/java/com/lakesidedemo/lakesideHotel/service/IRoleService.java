package com.lakesidedemo.lakesideHotel.service;

import com.lakesidedemo.lakesideHotel.model.Role;
import com.lakesidedemo.lakesideHotel.model.User;

import java.util.List;

/*
 * @author : rabin
 */

/**
 * Service interface for Role-related operations.
 * This interface defines the contract for services that handle operations
 * related to roles and their assignments to users.
 */
public interface IRoleService {

    /**
     * Retrieves all roles.
     *
     * @return A list of all roles.
     */
    List<Role> getRoles();

    /**
     * Creates a new role.
     *
     * @param theRole The role to be created.
     * @return The created Role object.
     */
    Role createRole(Role theRole);

    /**
     * Deletes a role by its ID.
     *
     * @param id The ID of the role to be deleted.
     */
    void deleteRow(Long id);

    /**
     * Finds a role by its name.
     *
     * @param name The name of the role to find.
     * @return The Role object if found, otherwise null.
     */
    Role findByName(String name);

    /**
     * Removes a user from a role.
     *
     * @param userId The ID of the user to be removed from the role.
     * @param roleId The ID of the role from which the user is to be removed.
     * @return The updated User object after removal.
     */
    User removeUserFromRole(Long userId, Long roleId);

    /**
     * Assigns a role to a user.
     *
     * @param userId The ID of the user to whom the role is to be assigned.
     * @param roleId The ID of the role to be assigned.
     * @return The updated User object after assignment.
     */
    User assignRoleToUser(Long userId, Long roleId);

    /**
     * Removes all users from a role.
     *
     * @param roleId The ID of the role from which all users are to be removed.
     * @return The updated Role object after all users are removed.
     */
    Role removeAllUsersFromRole(Long roleId);
}

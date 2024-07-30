package com.lakesidedemo.lakesideHotel.service;

import com.lakesidedemo.lakesideHotel.exception.RoleAlreadyExistException;
import com.lakesidedemo.lakesideHotel.exception.UserAlreadyExistsException;
import com.lakesidedemo.lakesideHotel.model.Role;
import com.lakesidedemo.lakesideHotel.model.User;
import com.lakesidedemo.lakesideHotel.repository.RoleRepository;
import com.lakesidedemo.lakesideHotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * @author : rabin
 */

/**
 * Service implementation for role-related operations.
 *
 * This class implements the IRoleService interface and provides the actual
 * logic for managing roles and their associations with users.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final RoleRepository roleRepository;
    private final IUserService userService;
    private final UserRepository userRepository;

    /**
     * Retrieves all roles.
     *
     * @return A list of all roles.
     */
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    /**
     * Creates a new role.
     *
     * @param theRole The role to be created.
     * @return The created Role object.
     * @throws RoleAlreadyExistException if the role already exists.
     */
    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);

        if(roleRepository.existsByName(role)){
                throw new RoleAlreadyExistException(theRole.getName() + " role already exists");
        }
        return roleRepository.save(role);
    }

    /**
     * Deletes a role by its ID.
     *
     * @param roleId The ID of the role to be deleted.
     */
    @Override
    public void deleteRow(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    /**
     * Finds a role by its name.
     *
     * @param name The name of the role to find.
     * @return The Role object if found.
     */
//    @Override
//    public Role findByName(String name) {
//        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
//    }
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    /**
     * Removes a user from a role.
     *
     * @param userId The ID of the user to be removed from the role.
     * @param roleId The ID of the role from which the user is to be removed.
     * @return The updated User object after removal.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getUsers().contains(user.orElseThrow(() -> new UsernameNotFoundException("User not found")))) {
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    /**
     * Assigns a role to a user.
     *
     * @param userId The ID of the user to whom the role is to be assigned.
     * @param roleId The ID of the role to be assigned.
     * @return The updated User object after assignment.
     * @throws UserAlreadyExistsException if the user already has the role.
     */
    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.orElseThrow(() -> new RuntimeException("Role not found")))) {
            throw new UserAlreadyExistsException(user.get().getFirstName() + " is already assigned to the " + role.get().getName() + " role");
        }
        role.get().assignRoleToUser(user.get());
        roleRepository.save(role.get());
        return user.get();
    }

    /**
     * Removes all users from a role.
     *
     * @param roleId The ID of the role from which all users are to be removed.
     * @return The updated Role object after all users are removed.
     */
    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.orElseThrow(() -> new RuntimeException("Role not found")).removeAllUsersFromRole();
        return roleRepository.save(role.get());
    }
}

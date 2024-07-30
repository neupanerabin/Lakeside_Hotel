package com.lakesidedemo.lakesideHotel.controller;

import com.lakesidedemo.lakesideHotel.exception.RoleAlreadyExistException;
import com.lakesidedemo.lakesideHotel.model.Role;
import com.lakesidedemo.lakesideHotel.model.User;
import com.lakesidedemo.lakesideHotel.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;

/*
 * @author : rabin
 */

@RestController  // Indicates that this class is a REST controller.
@RequiredArgsConstructor  // Lombok annotation to generate a constructor for all final fields.
@RequestMapping("/roles")  // Base URL for all endpoints in this controller.
public class RoleController {
    private final IRoleService roleService;  // Service for role-related operations.

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getRoles(), FOUND);  // Returns all roles with FOUND status.
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole) {
        try {
            roleService.createRole(theRole);  // Creates a new role.
            return ResponseEntity.ok("New role created successfully!");
        } catch (RoleAlreadyExistException re) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());  // Handles role already exists exception.
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRow(roleId);  // Deletes a role by ID.
    }

    @PostMapping("/remove-all-users")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
        return roleService.removeAllUsersFromRole(roleId);  // Removes all users from a role.
    }

    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        return roleService.removeUserFromRole(userId, roleId);  // Removes a specific user from a role.
    }

    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        return roleService.assignRoleToUser(userId, roleId);  // Assigns a user to a role.
    }
}

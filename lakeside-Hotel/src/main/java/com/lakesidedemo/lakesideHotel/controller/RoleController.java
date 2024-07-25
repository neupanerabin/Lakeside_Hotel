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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    private  final IRoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getRoles(), FOUND);
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole){
        try{
            roleService.createRole(theRole);
            return ResponseEntity.ok("New role created successfully! ");

        }catch(RoleAlreadyExistException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());

        }
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRow(roleId);
    }

    @PostMapping("/remove-all-users")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId){
        return roleService.removeAllUsersFromRole(roleId);

    }

    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        return  roleService.removeUserFromRole(userId, roleId);

    }

    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        return  roleService.assignRoleToUser(userId, roleId);

    }


}

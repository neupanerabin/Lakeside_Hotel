package com.lakesidedemo.lakesideHotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/*
 * @author : rabin
 */

/**
 * Entity class representing a Role.
 * This class is annotated with JPA annotations to map it to a database table.
 * It also uses Lombok annotations to generate getters, setters, and a no-argument constructor.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    // Primary key for the Role entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the role
    private String name;

    public Role(String name) {
        this.name = name;
    }

    // Many-to-many relationship with User
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    /**
     * Assigns this role to a user.
     *
     * @param user The user to assign the role to.
     */
    public void assignRoleToUser(User user) {
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    /**
     * Removes this role from a user.
     *
     * @param user The user to remove the role from.
     */
    public void removeUserFromRole(User user) {
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    /**
     * Removes this role from all users.
     */
    public void removeAllUsersFromRole() {
        if (this.getUsers() != null) {
            List<User> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this::removeUserFromRole);
        }
    }

    /**
     * Gets the name of the role.
     *
     * @return The name of the role, or an empty string if the name is null.
     */
    public String getName() {
        return name != null ? name : "";
    }


}

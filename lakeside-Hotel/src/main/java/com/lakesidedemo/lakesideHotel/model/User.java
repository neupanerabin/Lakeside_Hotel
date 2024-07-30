package com.lakesidedemo.lakesideHotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;
import java.util.HashSet;

/**
 * Entity class representing a User.
 *
 * This class is annotated with JPA annotations to map it to a database table.
 * It also uses Lombok annotations to generate getters, setters, and a no-argument constructor.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    // Primary key for the User entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User's first name
    private String firstName;

    // User's last name
    private String lastName;

    // User's email
    private String email;

    // User's password
    private String password;

    // Many-to-many relationship between User and Role
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,
                    CascadeType.DETACH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();

}

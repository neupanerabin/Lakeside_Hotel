package com.lakesidedemo.lakesideHotel.response;


/*
 * @author : rabin
 */

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtResponse {
    private Long id;
    private String email;
    private String jwt;
    private String type="Bearer";
    private List<String> roles;

    public JwtResponse(Long id, String email, String jwt, List<String> roles) {
        this.id = id;
        this.email = email;
        this.jwt = jwt;
        this.roles = roles;
    }
}

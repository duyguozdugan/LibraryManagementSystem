package com.duyguozdugan.library_management_system.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse{

    private String username;
    private String email;
    private String Role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}

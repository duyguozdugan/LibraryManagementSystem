package com.duyguozdugan.library_management_system.dto.response;


import com.duyguozdugan.library_management_system.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse{

    private Long id;
    private String username;
    private String email;
    private Role role;

    public UserResponse(Long id,String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        role = role;
    }
}

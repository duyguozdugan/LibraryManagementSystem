package com.duyguozdugan.library_management_system.controller;
import com.duyguozdugan.library_management_system.dto.request.UserRequest;
import com.duyguozdugan.library_management_system.dto.response.UserResponse;
import com.duyguozdugan.library_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.ok("User created successfully.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.getId()")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateUser(id, userRequest, loggedInEmail);
        return ResponseEntity.ok("User updated successfully.");
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.getId() or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUser(id, loggedInEmail);
        return ResponseEntity.ok("User deleted successfully.");
    }
}



package com.duyguozdugan.library_management_system.controller;

import com.duyguozdugan.library_management_system.dto.request.UserRequest;
import com.duyguozdugan.library_management_system.dto.response.BookResponse;
import com.duyguozdugan.library_management_system.dto.response.UserResponse;
import com.duyguozdugan.library_management_system.model.User;
import com.duyguozdugan.library_management_system.service.BookService;
import com.duyguozdugan.library_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

   private final BookService bookService;
   private final UserService userService;



    public UserController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public String createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return "User created successfully.";
    }

    @GetMapping()
    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully.";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateUser(id, userRequest);
        return "User updated successfully.";
    }


}

package com.duyguozdugan.library_management_system.service;

import com.duyguozdugan.library_management_system.dto.request.UserRequest;
import com.duyguozdugan.library_management_system.dto.response.UserResponse;
import com.duyguozdugan.library_management_system.model.User;
import com.duyguozdugan.library_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(@RequestBody UserRequest userRequest) {
        User user = new User();
         user.updateFrom(userRequest);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, @RequestBody UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow();
      user.updateFrom(userRequest);
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(String.valueOf(user.getRole()));
            return userResponse;
        }).toList();
    }


}

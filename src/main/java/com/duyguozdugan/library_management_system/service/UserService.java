package com.duyguozdugan.library_management_system.service;

import com.duyguozdugan.library_management_system.dto.request.UserRequest;
import com.duyguozdugan.library_management_system.dto.response.UserResponse;
import com.duyguozdugan.library_management_system.model.Role;
import com.duyguozdugan.library_management_system.model.User;
import com.duyguozdugan.library_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole()))
                .toList();
    }

    public void updateUser(Long id, UserRequest userRequest, String loggedInEmail) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getEmail().equals(loggedInEmail)) {
            throw new IllegalStateException("You can only update your own account.");
        }

        user.updateFrom(userRequest);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id, String loggedInEmail) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getEmail().equals(loggedInEmail) && !isAdmin(loggedInEmail)) {
            throw new IllegalStateException("You can only delete your own account.");
        }

        userRepository.delete(user);
    }

    public boolean isAdmin(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getRole() == Role.ADMIN;
    }
}

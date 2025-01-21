package com.duyguozdugan.library_management_system.init;
import com.duyguozdugan.library_management_system.model.Role;
import com.duyguozdugan.library_management_system.model.User;
import com.duyguozdugan.library_management_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("StrongAdminPass123")); // Güçlü şifre belirle!
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user created successfully.");
        }
    }
}

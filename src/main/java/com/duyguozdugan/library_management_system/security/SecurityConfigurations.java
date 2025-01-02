package com.duyguozdugan.library_management_system.security;

import com.duyguozdugan.library_management_system.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.duyguozdugan.library_management_system.model.Permission.*;
import static com.duyguozdugan.library_management_system.model.Role.ADMIN;
import static com.duyguozdugan.library_management_system.model.Role.USER;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations{


    @Bean
    @Primary
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities(Role.ADMIN.getAuthorities()) // ADMIN yetkileri atanır
                .build();

        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder.encode("user1"))
                .authorities(Role.USER.getAuthorities()) // USER yetkileri atanır
                .build();

        return new InMemoryUserDetailsManager(user1, admin);
    }





   

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/books/**").hasAnyRole(ADMIN.name(),USER.name()) // Kitap API'sine genel erişim için ADMIN rolü gerekiyor
                                .requestMatchers(HttpMethod.GET, "/api/v1/books/**").hasAnyAuthority("admin:read","user:read") // Sadece GET için USER_READ ve ADMIN_READ yetkileri
                                .requestMatchers(HttpMethod.POST, "/api/v1/books/**").hasAnyAuthority(ADMIN_CREATE.name()) // Diğer işlemler sadece ADMIN'e açık
                                .requestMatchers(HttpMethod.PUT, "/api/v1/books/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasAnyAuthority(ADMIN_DELETE.name())
                                .anyRequest().authenticated() // Diğer tüm istekler için kimlik doğrulama gerekli
                )
                .httpBasic(withDefaults());

        return http.build();
    }





    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
}

}

package com.example.movietickets.config;

import com.example.movietickets.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableMethodSecurity
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean UserDetailsService userDetailsService(UserRepository users){return email -> users.findByEmailIgnoreCase(email).map(u -> User.withUsername(u.getEmail()).password(u.getPassword()).roles(u.getRole().name()).build()).orElseThrow(() -> new UsernameNotFoundException("User not found"));}
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{return configuration.getAuthenticationManager();}
    @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable()).headers(headers->headers.frameOptions(frame->frame.sameOrigin())).authorizeHttpRequests(auth->auth
            .requestMatchers("/","/index.html","/css/**","/js/**","/api/auth/**","/api/movies/**","/api/showtimes/**","/h2-console/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .requestMatchers("/api/bookings/**").authenticated()
            .anyRequest().permitAll())
            .formLogin(form->form.disable()).httpBasic(basic->basic.disable()).logout(logout->logout.disable());
        return http.build();
    }
}

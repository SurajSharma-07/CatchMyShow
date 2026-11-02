package com.example.movietickets.controller;

import com.example.movietickets.dto.AuthRequest; import com.example.movietickets.model.User; import com.example.movietickets.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.authentication.*; import org.springframework.security.core.*; import org.springframework.security.core.context.*; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.context.HttpSessionSecurityContextRepository; import org.springframework.web.bind.annotation.*; import java.util.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final AuthenticationManager authenticationManager;
 public AuthController(UserRepository users,PasswordEncoder encoder,AuthenticationManager authenticationManager){this.users=users;this.encoder=encoder;this.authenticationManager=authenticationManager;}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request){if(users.existsByEmailIgnoreCase(request.email()))return ResponseEntity.badRequest().body(Map.of("message","An account with this email already exists.")); String name=(request.fullName()==null||request.fullName().isBlank())?request.email().split("@")[0]:request.fullName().trim(); User user=users.save(new User(request.email().trim().toLowerCase(),encoder.encode(request.password()),name,User.Role.CUSTOMER)); return ResponseEntity.status(HttpStatus.CREATED).body(profile(user));}
 @PostMapping("/login") public Map<String,Object> login(@Valid @RequestBody AuthRequest request,HttpServletRequest httpRequest){Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email().trim().toLowerCase(),request.password())); SecurityContext context=SecurityContextHolder.createEmptyContext(); context.setAuthentication(authentication); SecurityContextHolder.setContext(context); httpRequest.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,context); return current(authentication.getName());}
 @GetMapping("/me") public ResponseEntity<?> me(Authentication authentication){return authentication==null?ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Not authenticated")):ResponseEntity.ok(current(authentication.getName()));}
 @PostMapping("/logout") public Map<String,String> logout(HttpServletRequest request){SecurityContextHolder.clearContext(); if(request.getSession(false)!=null)request.getSession(false).invalidate(); return Map.of("message","Logged out");}
 private Map<String,Object> current(String email){return users.findByEmailIgnoreCase(email).map(this::profile).orElseThrow();}
 private Map<String,Object> profile(User user){Map<String,Object> p=new LinkedHashMap<>();p.put("email",user.getEmail());p.put("fullName",user.getFullName());p.put("role",user.getRole().name());return p;}
}

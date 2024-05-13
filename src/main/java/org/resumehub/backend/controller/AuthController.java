package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.LoginDTO;
import org.resumehub.backend.dto.UserDTO;
import org.resumehub.backend.response.AuthResponse;
import org.resumehub.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginDTO loginDTO) {
        AuthResponse response = userService.loginUser(loginDTO);
        return ResponseEntity.ok(response);
    }
}

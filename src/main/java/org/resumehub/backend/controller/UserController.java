package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.dto.UserDTO;
import org.resumehub.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("authorization") String jwt) {

        UserDTO userProfile = userService.findUserProfileByJwt(jwt);
        userProfile.setPassword("*".repeat(userProfile.getPassword().length()));
        logger.info("User profile details: {}", userProfile);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("/email")
    ResponseEntity<UserDTO> getUserByEmail(@RequestHeader("authorization") String jwt, @RequestParam String email) {
        var user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(@RequestHeader("authorization") String jwt) {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> findById(@Validated @RequestHeader("authorization") String jwt, @PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    public void create(@Validated @RequestHeader("authorization") String jwt, @RequestBody UserDTO newUser) {
        ResponseEntity.ok(userService.create(newUser));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> update(@Validated @RequestHeader("authorization") String jwt, @PathVariable("id") String userId, @RequestBody UserDTO updatedUser) {
        return ResponseEntity.ok(userService.update(userId, updatedUser));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@Validated @RequestHeader("authorization") String jwt, @PathVariable("id") String userId) {
        userService.delete(userId);
        return ResponseEntity.ok("User deleted successful");
    }
}

package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.UserDto;
import org.resumehub.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@Validated @PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    public void create(@Validated @RequestBody UserDto newUser) {
        ResponseEntity.ok(userService.create(newUser));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@Validated @PathVariable("id") String userId, @RequestBody UserDto updatedUser) {
        return ResponseEntity.ok(userService.update(userId, updatedUser));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@Validated @PathVariable("id") String userId) {
        userService.delete(userId);
        return ResponseEntity.ok("User deleted successful");
    }
}

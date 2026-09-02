package com.smartcitizen.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcitizen.portal.dto.LoginDto;
import com.smartcitizen.portal.dto.LoginResponseDto;
import com.smartcitizen.portal.dto.UserDto;
import com.smartcitizen.portal.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody UserDto userDto) {

        UserDto savedUser = userService.registerUser(userDto);

        return new ResponseEntity<>(
                savedUser,
                HttpStatus.CREATED
        );
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(
            @Valid @RequestBody LoginDto loginDto) {

        LoginResponseDto response =
                userService.loginUser(loginDto);

        return ResponseEntity.ok(response);
    }

   @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {

        return ResponseEntity.ok(
                userService.updateUser(
                        id,
                        userDto
                )
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }
}
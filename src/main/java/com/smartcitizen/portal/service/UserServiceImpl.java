package com.smartcitizen.portal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcitizen.portal.dto.LoginDto;
import com.smartcitizen.portal.dto.LoginResponseDto;
import com.smartcitizen.portal.dto.UserDto;
import com.smartcitizen.portal.model.User;
import com.smartcitizen.portal.repository.ComplaintRepository;
import com.smartcitizen.portal.repository.NotificationRepository;
import com.smartcitizen.portal.repository.UserRepository;
import com.smartcitizen.portal.security.JwtService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private NotificationRepository notificationRepository;


    // =========================================================
    // REGISTER USER
    // =========================================================

    @Override
    public UserDto registerUser(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {

            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setFullname(
                userDto.getFullname()
        );

        user.setEmail(
                userDto.getEmail()
        );

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        userDto.getPassword()
                )
        );

        user.setPhone(
                userDto.getPhone()
        );

        // Normal registration creates CITIZEN
        user.setRole("CITIZEN");

        User savedUser =
                userRepository.save(user);

        return mapToDto(savedUser);
    }


    // =========================================================
    // GET ALL USERS
    // =========================================================

    @Override
    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    // =========================================================
    // GET USER BY ID
    // =========================================================

    @Override
    public UserDto getUserById(Long id) {

        User user =
                userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User Not Found"
                        )
                );

        return mapToDto(user);
    }


    // =========================================================
    // UPDATE USER
    // =========================================================

    @Override
    public UserDto updateUser(
            Long id,
            UserDto userDto) {

        User user =
                userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User Not Found"
                        )
                );

        user.setFullname(
                userDto.getFullname()
        );

        user.setPhone(
                userDto.getPhone()
        );

        User updatedUser =
                userRepository.save(user);

        return mapToDto(updatedUser);
    }


    // =========================================================
    // DELETE USER
    // =========================================================

    @Override
    @Transactional
    public void deleteUser(Long id) {

        // Find user first
        User user =
                userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User Not Found"
                        )
                );


        // -----------------------------------------------------
        // 1. DELETE ALL NOTIFICATIONS OF THIS USER
        // -----------------------------------------------------

        notificationRepository.deleteByUserUserid(id);


        // -----------------------------------------------------
        // 2. DELETE ALL COMPLAINTS OF THIS USER
        // -----------------------------------------------------

        complaintRepository.deleteByUserUserid(id);


        // -----------------------------------------------------
        // 3. DELETE THE USER
        // -----------------------------------------------------

        userRepository.delete(user);
    }


    // =========================================================
    // LOGIN USER
    // =========================================================

    @Override
    public LoginResponseDto loginUser(
            LoginDto loginDto) {

        User user =
                userRepository.findByEmail(
                        loginDto.getEmail()
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Invalid Email"
                        )
                );


        // Check password
        if (!passwordEncoder.matches(
                loginDto.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }


        // Generate JWT
        String token =
                jwtService.generateToken(user);


        // Create response
        LoginResponseDto response =
                new LoginResponseDto();


        response.setUserId(
                user.getUserid()
        );

        response.setFullname(
                user.getFullname()
        );

        response.setEmail(
                user.getEmail()
        );

        response.setRole(
                user.getRole()
        );

        response.setToken(
                token
        );


        return response;
    }


    // =========================================================
    // ENTITY → DTO
    // =========================================================

    private UserDto mapToDto(User user) {

        UserDto dto =
                new UserDto();

        dto.setId(
                user.getUserid()
        );

        dto.setFullname(
                user.getFullname()
        );

        dto.setEmail(
                user.getEmail()
        );

        dto.setPhone(
                user.getPhone()
        );

        dto.setRole(
                user.getRole()
        );

        return dto;
    }
}
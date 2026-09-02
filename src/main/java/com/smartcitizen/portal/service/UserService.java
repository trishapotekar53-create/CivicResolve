package com.smartcitizen.portal.service;

import java.util.List;

import com.smartcitizen.portal.dto.LoginDto;
import com.smartcitizen.portal.dto.LoginResponseDto;
import com.smartcitizen.portal.dto.UserDto;

public interface UserService {

    UserDto registerUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);

    LoginResponseDto loginUser(LoginDto loginDto);
}
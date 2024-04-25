package com.personal.gadgetstore.service;

import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.dto.UserDto;

import java.util.List;

public interface UserService {
    // Create user
    UserDto createUser(UserDto userDto);

    //Create batch user
    List<UserDto> createUsers(List<UserDto> userDtos);

    // Update user
    UserDto updateUser(Integer userId, UserDto userDto);

    // Delete user by ID
    void deleteUserById(Integer userId);

    // Get all users
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    // Get user by ID
    UserDto getUserById(Integer userId);

    // Get user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    void updateImageForUser(int userId, String imageName);
}

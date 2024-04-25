package com.personal.gadgetstore.service.impl;

import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.dto.UserDto;
import com.personal.gadgetstore.entity.User;
import com.personal.gadgetstore.exception.ResourceNotFoundException;
import com.personal.gadgetstore.helper.PageableHelper;
import com.personal.gadgetstore.repository.UserRepository;
import com.personal.gadgetstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imageFilePath;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> createUsers(List<UserDto> userDtos) {
        List<User> users = userDtos.stream()
                .map(userDto -> modelMapper.map(userDto, User.class))
                .collect(Collectors.toList());
        List<User> createdUsers = userRepository.saveAll(users);
        return createdUsers.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found userId:" + userId));
        modelMapper.map(userDto, user);
        user.setUserId(userId);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found userId:" + userId));
        //deleting user's image
        String imagePath = imageFilePath + File.separator + user.getImageName();
        try {
            Files.delete(Paths.get(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Could not delete user's image");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDir.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, direction, sortBy);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> pageableResponse = PageableHelper.createPageableResponse(page, UserDto.class);
        return pageableResponse;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found userId:" + userId));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found email:" + email));
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        // Perform the search using the UserRepository
        List<User> users = userRepository.findByNameContaining(keyword);

        // Map the User entities to UserDto objects
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateImageForUser(int userId, String imageName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found userId:" + userId));
        user.setImageName(imageName);
        userRepository.save(user);
    }
}

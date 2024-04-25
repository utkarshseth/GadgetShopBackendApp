package com.personal.gadgetstore.controller;

import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.dto.UserDto;
import com.personal.gadgetstore.service.FileService;
import com.personal.gadgetstore.service.UserService;
import com.personal.gadgetstore.validate.Admin;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Api
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageFilePath;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        HttpStatus status = HttpStatus.OK;
        if (createdUser == null) status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Admin
    @PostMapping("/createBatch")
    public ResponseEntity<List<UserDto>> createUsers(@Valid @RequestBody List<UserDto> userDtos,
                                                     @RequestParam(defaultValue = "", required = false) String admin) {
        List<UserDto> createdUsers = userService.createUsers(userDtos);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUsers);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Admin
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId,
                                           @RequestParam(defaultValue = "", required = false) String admin) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                                 @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                                 @RequestParam(defaultValue = "name", required = false) String sortBy,
                                                                 @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<UserDto> users = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        UserDto user = userService.getUserById(userId);
        HttpStatus status = HttpStatus.OK;
        if (user == null) status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(user, status);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        HttpStatus status = HttpStatus.OK;
        if (user == null) status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(user, status);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String keyword) {
        List<UserDto> users = userService.searchUser(keyword);
        HttpStatus status = HttpStatus.OK;
        if (CollectionUtils.isEmpty(users)) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(users, status);
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("userImage") MultipartFile file,
                                                           @PathVariable int userId) throws IOException {

        String imageName = fileService.uploadFile(file, imageFilePath);
        Map<String, String> map = new HashMap<>();
        map.put("imageName", imageName);
        userService.updateImageForUser(userId, imageName);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void getImage(@PathVariable int userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getUserById(userId);
        logger.info("user image name:{} " + user.getImageName());
        InputStream inputStream = fileService.getResource(imageFilePath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}

package com.personal.gadgetstore.dto;

import com.personal.gadgetstore.validate.ImageNameValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer userId;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 15, message = "Name length should be between 3 and 15")
    private String name;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email should be valid")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min = 3, max = 15, message = "Password must be at least 3 characters long min and max 15 characters long")
    private String password;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "About is required")
    private String about;

    @ImageNameValid
    private String imageName;
}

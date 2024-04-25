package com.personal.gadgetstore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private int categoryId;
    @Size(min = 4, message = "title must be min 4 chars")
    @NotBlank
    private String title;
    @NotBlank(message = "description required")
    private String description;
    @NotBlank
    private String coverImage;
}


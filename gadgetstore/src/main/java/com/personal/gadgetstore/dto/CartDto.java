package com.personal.gadgetstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDto {

    private int cartId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;
    private int userId;

}

package com.personal.gadgetstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private int id;
    @NotBlank(message = "title required")
    private String title;
    @NotBlank(message = "description required")
    private String description;
    private int price;
    private int quantity;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private int categoryId;
    private String productImage;
}

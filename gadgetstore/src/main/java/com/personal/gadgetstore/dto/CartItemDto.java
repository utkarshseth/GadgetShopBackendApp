package com.personal.gadgetstore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemDto {

    private int productId;
    private int quantity;
    private int productPrice;
    private int cartId;

}

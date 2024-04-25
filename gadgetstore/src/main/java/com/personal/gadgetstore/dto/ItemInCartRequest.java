package com.personal.gadgetstore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemInCartRequest {
    private int productId;
    private int quantity;
    private int price;
}

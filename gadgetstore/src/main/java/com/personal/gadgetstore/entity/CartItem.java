package com.personal.gadgetstore.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    private int productId; // will be a foreign key refrence Product(id)
    private int quantity;
    private int productPrice;
    private int cartId;  // will be a foreign key refrence Cart(cartId)

}

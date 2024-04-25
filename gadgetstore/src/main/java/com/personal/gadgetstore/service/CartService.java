package com.personal.gadgetstore.service;

import com.personal.gadgetstore.dto.CartDto;
import com.personal.gadgetstore.dto.CartItemDto;
import com.personal.gadgetstore.dto.ItemInCartRequest;

import java.util.List;

public interface CartService {

//    step1->First get a cart for userId from cart table if not there the create a cart and recieve its cartId.

//    step2->Now check if quantity in itemInCartRequest is less than or equal to zero then remove row from cart_item
//    for the given productId & cartId(which we got in step1)

//    step3->If quantity is positive then for the cartId and given productId update the quantity in cart_items field

    //if quantity comes negative in this then we remove this cartItem row itself orelse i just update quantity
    CartDto updateItemInCart(int userId, ItemInCartRequest itemInCartRequest);

    //remove row from cart table for this userId
    void removeUserCart(int userId);

    List<CartItemDto> getCartItems(int userId);
}

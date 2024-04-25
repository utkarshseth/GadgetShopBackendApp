package com.personal.gadgetstore.controller;

import com.personal.gadgetstore.dto.CartDto;
import com.personal.gadgetstore.dto.CartItemDto;
import com.personal.gadgetstore.dto.ItemInCartRequest;
import com.personal.gadgetstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Updates or removes items in the user's cart based on the provided quantity.
     *
     * @param userId            the ID of the user whose cart is to be updated
     * @param itemInCartRequest the item details and quantity for the cart
     * @return the updated cart data
     */
    @PostMapping("/update/{userId}")
    public ResponseEntity<CartDto> updateItemInCart(@PathVariable int userId,
                                                    @RequestBody ItemInCartRequest itemInCartRequest) {
        CartDto cartDto = cartService.updateItemInCart(userId, itemInCartRequest);
        return ResponseEntity.ok(cartDto);
    }

    /**
     * Removes the cart for a specified user.
     *
     * @param userId the ID of the user whose cart is to be removed
     * @return a response entity indicating the operation's success
     */
    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> removeUserCart(@PathVariable int userId) {
        cartService.removeUserCart(userId);
        return ResponseEntity.ok("Cart removed successfully.");
    }


    /**
     * Endpoint to get all cart items for a given userId.
     *
     * @param userId the ID of the user whose cart items are to be retrieved
     * @return a list of CartItemDto
     */
    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable int userId) {
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

}

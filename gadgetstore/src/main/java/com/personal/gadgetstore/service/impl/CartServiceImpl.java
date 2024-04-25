package com.personal.gadgetstore.service.impl;

import com.personal.gadgetstore.dto.CartDto;
import com.personal.gadgetstore.dto.CartItemDto;
import com.personal.gadgetstore.dto.ItemInCartRequest;
import com.personal.gadgetstore.entity.Cart;
import com.personal.gadgetstore.entity.CartItem;
import com.personal.gadgetstore.exception.ResourceNotFoundException;
import com.personal.gadgetstore.repository.CartItemRepository;
import com.personal.gadgetstore.repository.CartRepository;
import com.personal.gadgetstore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto updateItemInCart(int userId, ItemInCartRequest itemInCartRequest) {
        // Step 1: Retrieve or create a cart for the given userId
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setCreatedAt(new Date());
                    Cart saved = cartRepository.save(newCart);
                    return saved;
                });

        // Step 2: Handle item quantity update or removal
        int productId = itemInCartRequest.getProductId();
        int quantity = itemInCartRequest.getQuantity();
        int price = itemInCartRequest.getPrice();

        if (quantity <= 0) {
            // If quantity is zero or less, remove the item from the cart
            cartItemRepository.deleteByCartIdAndProductId(cart.getCartId(), productId);
        } else {
            // Update the item quantity
            CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getCartId(), productId)
                    .orElse(new CartItem(0, productId, quantity, price, cart.getCartId()));
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        // Map Cart to CartDto
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void removeUserCart(int userId) {
        cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No cart available for " + "userId: " + userId));
        cartRepository.deleteByUserId(userId);
    }

    @Override
    public List<CartItemDto> getCartItems(int userId) {
        return cartRepository.findByUserId(userId)
                .map(cart -> cartItemRepository.findByCartId(cart.getCartId()))
                .orElse(Collections.emptyList())
                .stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .collect(Collectors.toList());
    }

}

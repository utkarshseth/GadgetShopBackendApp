package com.personal.gadgetstore.repository;

import com.personal.gadgetstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Transactional
    void deleteByCartIdAndProductId(int cartId, int productId);

    Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);

    List<CartItem> findByCartId(int cartId);
}

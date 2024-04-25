package com.personal.gadgetstore.repository;

import com.personal.gadgetstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByTitleContainingIgnoreCase(String subTitle,PageRequest pageRequest);

    Page<Product> findByLiveTrue(PageRequest pageRequest);
}

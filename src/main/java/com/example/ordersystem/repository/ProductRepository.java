package com.example.ordersystem.repository;

import com.example.ordersystem.entity.MstProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<MstProduct, Integer> {
    MstProduct findByProductNameIgnoreCase(String name);
}

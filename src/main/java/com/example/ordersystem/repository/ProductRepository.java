package com.example.ordersystem.repository;

import com.example.ordersystem.entity.MstProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends JpaRepository<MstProduct, Integer> {
    MstProduct findByProductNameIgnoreCase(String name);
}

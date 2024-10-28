package com.example.ordersystem.repository;

import com.example.ordersystem.entity.MstProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<MstProductType, Integer> {
    MstProductType findByProductTypeIgnoreCase(String productType);
}

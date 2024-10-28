package com.example.ordersystem.repository;

import com.example.ordersystem.entity.MstUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MstUser, Integer> {
    MstUser findByNameIgnoreCase(String username);
}

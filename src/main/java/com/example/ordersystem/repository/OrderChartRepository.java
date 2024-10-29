package com.example.ordersystem.repository;

import com.example.ordersystem.entity.MstOrderCart;
import com.example.ordersystem.entity.MstUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderChartRepository extends JpaRepository<MstOrderCart,Integer> {
    Page<MstOrderCart> findByIdUser(MstUser mstUser, Pageable pageable);
}

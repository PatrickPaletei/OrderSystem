package com.example.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="mst_product_type")
public class MstProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_product_type")
    private Integer idProductType;

    @Column(name="product_type")
    private String productType;

}

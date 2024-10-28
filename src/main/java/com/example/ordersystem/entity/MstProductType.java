package com.example.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

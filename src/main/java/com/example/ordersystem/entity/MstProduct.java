package com.example.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="mst_product")
public class MstProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_product")
    private int idProduct;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "id_product_type", nullable = false,foreignKey = @ForeignKey(name = "FK_ID_PRODUCT_TYPE"))
    private MstProductType productType;

    @Column(name = "price")
    private double price;

}

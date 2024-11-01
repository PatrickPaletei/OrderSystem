package com.example.ordersystem.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="mst_order_cart")
public class MstOrderCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_order_cart")
    private Integer idOrderCart;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false, foreignKey = @ForeignKey(name = "FK_ID_PRODUCT"))
    private MstProduct idProduct;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "FK_ID_USER"))
    private MstUser idUser;

    @Column(name = "date")
    private Date date;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;
}

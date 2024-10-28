package com.example.ordersystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name="mst_order_cart")
public class MstOrderCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_order_cart")
    private Integer idOrderCart;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false, foreignKey = @ForeignKey(name = "FK_ID_PRODUCT"))
    private MstProduct mstProduct;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "FK_ID_USER"))
    private MstUser mstUser;

    @Column(name = "date")
    private Date date;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;
}

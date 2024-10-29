package com.example.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="mst_user")
public class MstUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_user")
    private Integer idUser;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
}

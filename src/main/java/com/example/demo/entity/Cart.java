package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "cart")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcart")
    private int idCart;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idUser")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<Cart_Product> cartProducts;


}

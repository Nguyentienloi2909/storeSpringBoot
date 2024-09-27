package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "orderdetail")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class OrderDetail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "idproduct")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idorder", nullable = false)
    @JsonIgnore
    private Order order;

    @Column(name = "quantity")
    private int quantity;
}

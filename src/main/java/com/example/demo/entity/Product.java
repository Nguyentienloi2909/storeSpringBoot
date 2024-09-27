package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduct")
    private int idProduct;

    @ManyToOne( cascade = {
        CascadeType.DETACH, CascadeType.MERGE,
        CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "idcategory")
    private Category category;

    @Column(name = "productname")
    private String productName;

    @Column(name = "unitprice")
    private double unitPrice;

    @Column(name = "imageurl")
    private String imageUrl;

    @Column(name = "stockquantity")
    private int stockQuantity;

    @Column(name = "description")
    private String description;

    @Column(name = "productStatus")
    private boolean productStatus;

    @OneToMany(mappedBy = "product")
    private List<Cart_Product> cartProducts;
}

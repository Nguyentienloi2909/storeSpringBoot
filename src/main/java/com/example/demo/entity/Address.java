package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idaddress")
    private int idAddress;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false ,referencedColumnName = "iduser")
    @JsonBackReference
    private User user;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "district",length = 50)
    private String district;

    @Column(name = "province",length = 50)
    private String province;

}

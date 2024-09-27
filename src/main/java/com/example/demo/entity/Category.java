package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategory")
    private int idCategory;

    @Column(name = "categoryname", length = 255)
    private String categoryName;

    @Column(name = "catagoryStatus")
    private boolean categoryStatus;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL
    )
    private List<Product> products;

}

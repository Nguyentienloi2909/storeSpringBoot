package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private int idUser;

    @Column(name = "fullname", length = 255)
    private String fullname;

    @Column(name = "email", length = 255)
    private String email;


    @Column(name = "phonenumber", length = 20)
    private String phoneNumber;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "enble")
    private boolean enble;

    @OneToMany(
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }

    )
    @JsonManagedReference
    private List<Address> addressList;

    @OneToMany(
            mappedBy = "user",
            cascade = {
                CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private List<Order> orderList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<User_Role> userRoles;
}

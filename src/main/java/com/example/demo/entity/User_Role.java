package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_role")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class User_Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "iduser",referencedColumnName = "iduser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idrole",referencedColumnName = "idrole")
    private Role role;


}

package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Table(name = "invoice")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinvoice")
    private int idInvoices;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idOrder", nullable = false)
    private Order orderInvoice;

    @Column(name = "invoicedate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Date invoiceDate;

    @Column(name = "totalamount")
    private double totalAmount;

    @Column(name = "paymentmethod", length = 50)
    private String paymentMethod;


}

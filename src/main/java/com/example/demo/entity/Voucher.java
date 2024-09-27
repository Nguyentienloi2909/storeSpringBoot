package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "voucher")
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvoucher")
    private int idVoucher;

    @Column(name = "vouchercode", length = 50)
    private String voucherCode;

    @Column(name = "discount")
    private double discount;

    @Column(name = "expirydate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Date expriryDate;

    @Column(name = "usagecondition", length = 255)
    private String usageCondition;

    @Column(name = "status", length = 50)
    private String status;

    @OneToMany(
            mappedBy = "voucher",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private List<Order> orderList;
}

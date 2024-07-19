package com.assessment.bank.serverapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 60)
    private String placeOfBirth;

    @Column(nullable = false, length = 16, unique = true)
    private String identificationNumber;

    @Column(nullable = true)
    private Date dateOfBirth;

    @Column(nullable = true, length = 14)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}

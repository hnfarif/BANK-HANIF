package com.assessment.bank.serverapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false, length = 30)
    private String fullName;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false, length = 20)
    private String placeOfBirth;

    @Column(nullable = false, length = 16)
    private String identificationNumber;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false, length = 14)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}

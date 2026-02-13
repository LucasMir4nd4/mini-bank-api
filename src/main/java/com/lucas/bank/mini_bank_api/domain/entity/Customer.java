package com.lucas.bank.mini_bank_api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String password;
    private Date createdAt;

    public Customer(String name, String email, String cpf, String password, Date createdAt) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.createdAt = createdAt;
    }
}

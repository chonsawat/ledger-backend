package com.example.app.demo.Account;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder(setterPrefix = "set")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name = "account_desc")
    private String desc;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "prev_balance")
    private BigDecimal previousBalance;
    @Column(name = "last_update_date")
    private LocalDate updateDate;
    @Column(name = "original_balance")
    private BigDecimal original_balance;
}

package com.example.app.demo.Account;

import com.example.app.demo.Ledger.Ledger;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

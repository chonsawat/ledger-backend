package com.example.app.demo.Ledger;
import com.example.app.demo.Account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
@Table(name="ledger")
public class Ledger {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="date")
    private LocalDate date;
    @Column(name="description")
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "credit_account_id")
    private Account credit_account;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "debit_account_id")

    private Account debit_account;
    @Column(name="credit_amount")
    private BigDecimal credit_amount;
    @Column(name="debit_amount")

    private BigDecimal debit_amount;
    @Column(name="currency_type")
    private String currency_type;
}

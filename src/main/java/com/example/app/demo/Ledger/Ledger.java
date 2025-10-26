package com.example.app.demo.Ledger;
import com.example.app.demo.Account.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
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

    public Ledger() {
    }

    public Ledger(Optional<Account> byId, String thb) {
        this.setCredit_account(null);
        this.setDebit_account(null);
        this.setCurrency_type("THB");
        this.setCredit_amount(BigDecimal.valueOf(0));
        this.setDebit_amount(BigDecimal.valueOf(0));
    }

    public Ledger(Account credit_account, String currency_type) {
        this.setCredit_account(credit_account);
        this.setDebit_account(null);
        this.setCurrency_type(currency_type);
        this.setCredit_amount(BigDecimal.valueOf(0));
        this.setDebit_amount(BigDecimal.valueOf(0));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public BigDecimal getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(BigDecimal credit_amount) {
        this.credit_amount = credit_amount;
    }

    public BigDecimal getDebit_amount() {
        return debit_amount;
    }

    public void setDebit_amount(BigDecimal debit_amount) {
        this.debit_amount = debit_amount;
    }

    public Account getCredit_account() {
        return credit_account;
    }

    public void setCredit_account(Account credit_account) {
        this.credit_account = credit_account;
    }

    public Account getDebit_account() {
        return debit_account;
    }

    public void setDebit_account(Account debit_account) {
        this.debit_account = debit_account;
    }

    @Override
    public String toString() {
        return "Ledger{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", credit_account_id=" + credit_account +
                ", debit_account_id=" + debit_account +
                ", credit_amount=" + credit_amount +
                ", debit_amount=" + debit_amount +
                ", currency_type='" + currency_type + '\'' +
                '}';
    }
}

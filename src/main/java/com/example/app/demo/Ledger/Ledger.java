package com.example.app.demo.Ledger;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "credit_account_id")
    private Integer credit_account_id;
    @Column(name="credit_account")
    private String credit_account;
    @Column(name = "debit_account_id")
    private Integer debit_account_id;
    @Column(name="debit_account")
    private String debit_account;

    @Column(name="credit_amount")
    private BigDecimal credit_amount;
    @Column(name="debit_amount")
    private BigDecimal debit_amount;

    @Column(name="total_credit_balance")
    private BigDecimal total_credit_balance;

    @Column(name="total_debit_balance")
    private BigDecimal total_debit_balance;

    @Column(name="currency_type")
    private String currency_type;

    public Ledger() {
        this.setCredit_account("");
        this.setDebit_account("");
        this.setCurrency_type("THB");
        this.setCredit_amount(BigDecimal.valueOf(0));
        this.setDebit_amount(BigDecimal.valueOf(0));
        this.setTotal_credit_balance(BigDecimal.valueOf(0));
        this.setTotal_debit_balance(BigDecimal.valueOf(0));
    }

    public Ledger(String credit_account, String currency_type) {
        this.setCredit_account(credit_account);
        this.setDebit_account("");
        this.setCurrency_type(currency_type);
        this.setCredit_amount(BigDecimal.valueOf(0));
        this.setDebit_amount(BigDecimal.valueOf(0));
        this.setTotal_credit_balance(BigDecimal.valueOf(0));
        this.setTotal_debit_balance(BigDecimal.valueOf(0));
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

    public String getCredit_account() {
        return credit_account;
    }

    public void setCredit_account(String credit_account) {
        this.credit_account = credit_account;
    }

    public String getDebit_account() {
        return debit_account;
    }

    public void setDebit_account(String debit_account) {
        this.debit_account = debit_account;
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

    public BigDecimal getTotal_credit_balance() {
        return total_credit_balance;
    }

    public void setTotal_credit_balance(BigDecimal total_credit_balance) {
        this.total_credit_balance = total_credit_balance;
    }

    public BigDecimal getTotal_debit_balance() {
        return total_debit_balance;
    }

    public void setTotal_debit_balance(BigDecimal total_debit_balance) {
        this.total_debit_balance = total_debit_balance;
    }

    public Integer getCredit_account_id() {
        return credit_account_id;
    }

    public void setCredit_account_id(Integer credit_account_id) {
        this.credit_account_id = credit_account_id;
    }

    public Integer getDebit_account_id() {
        return debit_account_id;
    }

    public void setDebit_account_id(Integer debit_account_id) {
        this.debit_account_id = debit_account_id;
    }

    @Override
    public String toString() {
        return "Ledger{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", credit_account='" + credit_account + '\'' +
                ", debit_account='" + debit_account + '\'' +
                ", credit_amount=" + credit_amount +
                ", debit_amount=" + debit_amount +
                ", total_credit_balance=" + total_credit_balance +
                ", total_debit_balance=" + total_debit_balance +
                ", currency_type='" + currency_type + '\'' +
                '}';
    }
}

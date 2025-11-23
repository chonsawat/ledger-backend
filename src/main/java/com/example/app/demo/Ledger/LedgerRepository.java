package com.example.app.demo.Ledger;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.demo.Account.Account;

import java.util.List;

public interface LedgerRepository extends JpaRepository<Ledger, Integer> {
    List<Ledger> findAllByOrderByDateDesc(Sort sort);

    List<Ledger> findAllByOrderByDateDescIdDesc();

    List<Ledger> findAllByOrderByDate();

    @Query("SELECT l FROM Ledger l WHERE l.credit_account = :theAccount")
    List<Ledger> findAllByCreditAccount(@Param("theAccount") Account account);

    @Query("SELECT l FROM Ledger l WHERE l.debit_account = :theAccount")
    List<Ledger> findAllByDebitAccount(@Param("theAccount") Account account);

    @Query("SELECT l FROM Ledger l WHERE l.credit_account.id != 0 AND l.debit_account.id != 0 ORDER BY l.date DESC")
    List<Ledger> findAllTransfer();
}

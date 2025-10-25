package com.example.app.demo.Ledger;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository <Ledger, Integer> {
    List<Ledger> findAllByOrderByDateDesc(Sort sort);
    List<Ledger> findAllByOrderByDateDesc();
    List<Ledger> findAllByOrderByDate();
}

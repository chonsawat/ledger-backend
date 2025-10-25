package com.example.app.demo.Ledger;

import java.util.List;
import java.util.Optional;

public interface LedgerService {
    List<Ledger> findAll();
    void save(Ledger ledger);
    List<Ledger> findAllByOrderByDate();
    List<Ledger> findAllByOrderByDateDesc();
    List<Ledger> findAllByOrderByDateDescAndByid();
    Optional<Ledger> findById(Integer id);
}

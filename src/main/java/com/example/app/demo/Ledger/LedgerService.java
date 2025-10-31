package com.example.app.demo.Ledger;

import java.util.List;
import java.util.Optional;

public interface LedgerService {
    List<Ledger> findAll();
    Ledger save(Ledger ledger);
    List<Ledger> findAllByOrderByDate();
    List<Ledger> findAllByOrderByDateDescAndById();
    Optional<Ledger> findById(Integer id);
    Ledger deleteLedger(Ledger theLedger);
}

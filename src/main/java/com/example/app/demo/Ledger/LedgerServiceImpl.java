package com.example.app.demo.Ledger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LedgerServiceImpl implements LedgerService {
    private final LedgerRepository ledgerRepository;

    @Autowired
    public LedgerServiceImpl(LedgerRepository theLedger) {
        ledgerRepository = theLedger;
    }

    @Override
    public List<Ledger> findAll() {
        return ledgerRepository.findAll();
    }

    @Override
    public List<Ledger> findAllByOrderByDateDescAndByid() {
        return ledgerRepository.findAllByOrderByDateDesc(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Ledger> findAllByOrderByDateDesc() {
        return ledgerRepository.findAllByOrderByDateDesc();
    }

    @Override
    public List<Ledger> findAllByOrderByDate() {
        return ledgerRepository.findAllByOrderByDate();
    }

    @Override
    public void save(Ledger ledgerObj) {
        ledgerRepository.save(ledgerObj);
    }

    @Override
    public Optional<Ledger> findById(Integer id) {
        return ledgerRepository.findById(id);
    }
}

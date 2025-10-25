package com.example.app.demo.LedgerPage;

import com.example.app.demo.Ledger.Ledger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LedgerPagerServiceImpl implements LedgerPageService {

    private LedgerPageRepository ledgerPageRepository;

    @Override
    public List<Ledger> findAll() {
        return List.of();
    }
}

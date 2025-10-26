package com.example.app.demo.Ledger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LedgerRestController {

    private LedgerService ledgerService;

    public LedgerRestController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping("/ledger")
    public List<Ledger> findAll() {
        return ledgerService.findAll();
    }

    @GetMapping("/ledger-desc")
    public List<Ledger> findAllByOrderByDateDesc() {
        return ledgerService.findAllByOrderByDateDescAndById();
    }

    @GetMapping("/ledger-asc")
    public List<Ledger> findAllByOrderByDateAsc() {
        return ledgerService.findAllByOrderByDate();
    }

    @GetMapping("/ledger/{theId}")
    public Ledger findById(@PathVariable Integer theId) {
        Optional<Ledger> theLedger = ledgerService.findById(theId);

        if (theLedger.isEmpty()) {
            throw new RuntimeException("Ledger not found - " + theId);
        }
        return theLedger.get();
    }
}

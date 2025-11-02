package com.example.app.demo.Ledger;

import com.example.app.demo.Account.Account;
import com.example.app.demo.Account.AccountRepository;
import com.example.app.demo.LedgerDateGroup.LedgerDateGroup;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class LedgerRestController {

    private final LedgerService ledgerService;
    private final AccountRepository accountRepository;

    public LedgerRestController(LedgerService ledgerService, AccountRepository accountRepository) {
        this.ledgerService = ledgerService;
        this.accountRepository = accountRepository;
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

    @PostMapping("/ledger")
    @Transactional
    public Ledger addLedger(@RequestBody Ledger reqLedger) {
        Ledger newLedger = Ledger.builder()
                .setDate(reqLedger.getDate())
                .setDescription(reqLedger.getDescription())
                .setCredit_amount(reqLedger.getCredit_amount())
                .setDebit_amount(reqLedger.getDebit_amount())
                .setCurrency_type("THB").build();

        var credit = reqLedger.getCredit_account();
        var debit = reqLedger.getDebit_account();

        if (credit != null) {
            accountRepository.findById(credit.getId()).ifPresent(newLedger::setCredit_account);
        }

        if (debit != null) {
            accountRepository.findById(debit.getId()).ifPresent(newLedger::setDebit_account);
        }

        return ledgerService.save(newLedger);
    }


    @PatchMapping("/ledger")
    @Transactional
    public Ledger updateLedger(@RequestBody Ledger reqLedger) {
        Optional<Ledger> theLedger = ledgerService.findById(reqLedger.getId());
        theLedger.ifPresent((Ledger ledger) -> {

            Optional<Account> credit = null;
            Optional<Account> debit = null;

            if (reqLedger.getCredit_account() != null) {
                credit = accountRepository.findById(reqLedger.getCredit_account().getId());
            }

            if (reqLedger.getDebit_account() != null) {
                debit = accountRepository.findById(reqLedger.getDebit_account().getId());
            }

            theLedger.get().setId(reqLedger.getId());
            theLedger.get().setDate(reqLedger.getDate());
            theLedger.get().setDescription(reqLedger.getDescription());
            if (credit != null) {
                credit.ifPresent((item) -> theLedger.get().setCredit_account(item));
            }
            theLedger.get().setCredit_amount(reqLedger.getCredit_amount());
            if (debit != null) {
                debit.ifPresent((item) -> theLedger.get().setDebit_account(item));
            }
            theLedger.get().setDebit_amount(reqLedger.getDebit_amount());
            theLedger.get().setCurrency_type("THB");
        });
        theLedger.orElseThrow(() -> new RuntimeException("Ledger record not found for update - " + reqLedger.getId()));
        return ledgerService.save(theLedger.get());
    }

    @DeleteMapping("/ledger")
    @Transactional
    public Ledger deleteLedger(@RequestBody Ledger reqLedger) {
        Optional<Ledger> theLedger = ledgerService.findById(reqLedger.getId());
        System.out.println(theLedger);

        if (theLedger.isEmpty()) {
            throw new RuntimeException("Ledger for delete not found");
        } else {
            ledgerService.deleteLedger(theLedger.get());
        }
        return new Ledger();
    }

    @GetMapping("/ledgerGroupByDate")
    public TreeMap<LocalDate, List<Ledger>> findAllGroupByDate() {
        List<Ledger> allLedger = ledgerService.findAll();
        TreeMap<LocalDate, List<Ledger>> mapLedger = new TreeMap<>(Collections.reverseOrder());

        allLedger.forEach((item) -> {
            if (mapLedger.get(item.getDate()) == null) {
                mapLedger.put(item.getDate(), new ArrayList<>());
                mapLedger.get(item.getDate()).add(item);
            } else {
                mapLedger.get(item.getDate()).add(item);
            }
        });

        System.out.println(mapLedger);

        return mapLedger;
    }
}

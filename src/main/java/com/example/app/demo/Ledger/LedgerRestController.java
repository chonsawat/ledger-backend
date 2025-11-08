package com.example.app.demo.Ledger;

import com.example.app.demo.Account.Account;
import com.example.app.demo.Account.AccountRepository;
import com.example.app.demo.Ledger.DAO.DateLedgerGroup;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

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
        System.out.println("(updateLedger) Request: " + reqLedger);

        Optional<Ledger> theLedger = ledgerService.findById(reqLedger.getId());
        theLedger.ifPresent((Ledger ledger) -> {

            Optional<Account> credit;
            Optional<Account> debit;

            if (reqLedger.getCredit_account() != null) {
                credit = accountRepository.findById(reqLedger.getCredit_account().getId());
                credit.ifPresentOrElse((item) -> {
                    theLedger.get().setCredit_account(item);
                    theLedger.get().setCredit_amount(reqLedger.getCredit_amount());
                }, () -> {
                    theLedger.get().setCredit_account(reqLedger.getCredit_account());
                    theLedger.get().setCredit_amount(reqLedger.getCredit_amount());
                });
            } else {
                theLedger.get().setCredit_account(null);
                theLedger.get().setCredit_amount(null);
            }

            if (reqLedger.getDebit_account() != null) {
                debit = accountRepository.findById(reqLedger.getDebit_account().getId());
                debit.ifPresentOrElse((item) -> {
                    theLedger.get().setDebit_account(item);
                    theLedger.get().setDebit_amount(reqLedger.getDebit_amount());
                }, () -> {
                    theLedger.get().setCredit_account(reqLedger.getDebit_account());
                    theLedger.get().setDebit_amount(reqLedger.getDebit_amount());
                });
            } else {
                theLedger.get().setDebit_account(null);
                theLedger.get().setDebit_amount(null);
            }

            theLedger.get().setId(reqLedger.getId());
            theLedger.get().setDate(reqLedger.getDate());
            theLedger.get().setDescription(reqLedger.getDescription());
            theLedger.get().setCurrency_type("THB");
        });
        theLedger.orElseThrow(() -> new RuntimeException("Ledger record not found for update - " + reqLedger.getId()));

        System.out.println("(updateLedger) Response: " + theLedger.get());
        return ledgerService.save(theLedger.get());
    }

    @DeleteMapping("/ledger")
    @Transactional
    public Ledger deleteLedger(@RequestBody Ledger reqLedger) {
        Optional<Ledger> theLedger = ledgerService.findById(reqLedger.getId());
        System.out.println("(deleteLedger) request: " + theLedger);

        if (theLedger.isEmpty()) {
            throw new RuntimeException("Ledger for delete not found");
        } else {
            ledgerService.deleteLedger(theLedger.get());
        }
        return theLedger.get();
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

        return mapLedger;
    }

    @GetMapping("/ledgerGroupByDate/v2")
    public List<DateLedgerGroup> findAllGroupByDate2() {
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

        List<DateLedgerGroup> response = new LinkedList<>();
        mapLedger.forEach((key, value) -> response.add(new DateLedgerGroup(key, value)));
        return response;
    }
}

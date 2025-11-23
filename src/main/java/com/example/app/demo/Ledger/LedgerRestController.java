package com.example.app.demo.Ledger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.demo.Account.Account;
import com.example.app.demo.Account.AccountRepository;
import com.example.app.demo.Error.ErrorRestResponseException;
import com.example.app.demo.Ledger.DAO.DateLedgerGroup;
import com.example.app.demo.Ledger.Record.FindLedgerByDateRequest;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class LedgerRestController {

    private final LedgerRepository ledgerRepository;
    private final AccountRepository accountRepository;

    public LedgerRestController(LedgerRepository ledgerService, AccountRepository accountRepository) {
        this.ledgerRepository = ledgerService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/ledger")
    public List<Ledger> findAll() {
        return ledgerRepository.findAll();
    }

    @GetMapping("/ledger-desc")
    public List<Ledger> findAllByOrderByDateDesc() {
        return ledgerRepository.findAllByOrderByDateDescIdDesc();
    }

    @GetMapping("/ledger-asc")
    public List<Ledger> findAllByOrderByDateAsc() {
        return ledgerRepository.findAllByOrderByDate();
    }

    @GetMapping("/ledger/{theId}")
    public Ledger findById(@PathVariable Integer theId) {
        Optional<Ledger> theLedger = ledgerRepository.findById(theId);

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
            if (credit.getId() == null)
                throw new RuntimeException("credit.getId() was null");
            accountRepository.findById(credit.getId()).ifPresent(newLedger::setCredit_account);
        }

        if (debit != null) {
            if (debit.getId() == null)
                throw new RuntimeException("credit.getId() was null");
            accountRepository.findById(debit.getId()).ifPresent(newLedger::setDebit_account);
        }

        return ledgerRepository.save(newLedger);
    }

    @PatchMapping("/ledger")
    @Transactional
    public Ledger updateLedger(@RequestBody Ledger reqLedger) {
        System.out.println("(updateLedger) Request: " + reqLedger);

        try {

            Optional<Ledger> theLedger = ledgerRepository.findById(reqLedger.getId());
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
            theLedger.orElseThrow(
                    () -> new RuntimeException("Ledger record not found for update - " + reqLedger.getId()));

            System.out.println("(updateLedger) Response: " + theLedger.get());
            return ledgerRepository.save(theLedger.get());
        } catch (Exception exception) {
            throw new RuntimeException("Exception: " + exception);
        }
    }

    @DeleteMapping("/ledger")
    @Transactional
    public Ledger deleteLedger(@RequestBody Ledger reqLedger) {
        Optional<Ledger> theLedger = ledgerRepository.findById(reqLedger.getId());
        System.out.println("(deleteLedger) request: " + theLedger);

        if (theLedger.isEmpty()) {
            throw new RuntimeException("Ledger for delete not found");
        } else {
            ledgerRepository.delete(theLedger.get());
        }
        return theLedger.get();
    }

    @GetMapping("/ledgerGroupByDate")
    public TreeMap<LocalDate, List<Ledger>> findAllGroupByDate() {
        List<Ledger> allLedger = ledgerRepository.findAll();
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
        List<Ledger> allLedger = ledgerRepository.findAll();
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

    @GetMapping("/ledgerByCreditAccount/{accountId}")
    public List<Ledger> findByCreaditAccount(@PathVariable Integer accountId) {
        try {
            Optional<Account> creditaccount = accountRepository.findById(accountId);
            if (creditaccount.isEmpty())
                throw new ErrorRestResponseException("Account not found");

            var responseDetail = ledgerRepository.findAllByCreditAccount(creditaccount.get());
            if (responseDetail.isEmpty())
                throw new ErrorRestResponseException("Ledger not found");

            return responseDetail;
        } catch (Exception e) {
            throw new ErrorRestResponseException(e.toString());
        }
    }

    @GetMapping("/ledgerByDebitAccount/{accountId}")
    public List<Ledger> findByDebitAccount(@PathVariable Integer accountId) {
        try {
            Optional<Account> creditaccount = accountRepository.findById(accountId);
            if (creditaccount.isEmpty())
                throw new ErrorRestResponseException("Account not found");

            var responseDetail = ledgerRepository.findAllByDebitAccount(creditaccount.get());
            if (responseDetail.isEmpty())
                throw new ErrorRestResponseException("Ledger not found");

            return responseDetail;
        } catch (Exception e) {
            throw new ErrorRestResponseException(e.toString());
        }
    }

    @GetMapping("/ledgerTransfer")
    public List<Ledger> findAllTransfer() {
        var data = ledgerRepository.findAllTransfer();

        if (data == null)
            throw new ErrorRestResponseException("Data not found");
        return data;
    }

    @GetMapping("/ledgerByDate")
    public List<Ledger> findLedgerByDate(@RequestBody FindLedgerByDateRequest reqBody) {
        try {
            var data = ledgerRepository.findAllByDate(reqBody.date());
            return data;
        } catch (Exception e) {
            throw new ErrorRestResponseException(e.toString());
        }
    }
}

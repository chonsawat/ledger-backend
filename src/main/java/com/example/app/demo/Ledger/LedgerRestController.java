package com.example.app.demo.Ledger;

import com.example.app.demo.Account.Account;
import com.example.app.demo.Account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

        System.out.println("Input");
        System.out.println(reqLedger);

        Account savedCreditAccount = null;
        Account savedDebitAccount = null;

        if (reqLedger.getCredit_account() != null) {
            if (reqLedger.getCredit_amount().compareTo(BigDecimal.valueOf(0)) > 0) {
                Optional<Account> creditAccount = accountRepository.findById(reqLedger.getCredit_account().getId());

                if (creditAccount.isPresent()) {
                    creditAccount.get().setPreviousBalance(creditAccount.get().getBalance());
                    creditAccount.get().setBalance(creditAccount.get().getBalance().subtract(reqLedger.getCredit_amount()));
                    creditAccount.get().setUpdateDate(reqLedger.getCredit_account().getUpdateDate());
                    savedCreditAccount = accountRepository.save(creditAccount.get());
                    System.out.println("Credit: " + savedCreditAccount);
                }
            }
        }

        if (reqLedger.getDebit_account() != null) {
            if (reqLedger.getDebit_amount().compareTo(BigDecimal.valueOf(0)) > 0 ) {
                Optional<Account> debitAccount = accountRepository.findById(reqLedger.getDebit_account().getId());

                if (debitAccount.isPresent()) {
                    debitAccount.get().setPreviousBalance(debitAccount.get().getBalance());
                    debitAccount.get().setBalance(debitAccount.get().getBalance().add(reqLedger.getDebit_amount()));
                    debitAccount.get().setUpdateDate(reqLedger.getDebit_account().getUpdateDate());
                    savedDebitAccount = accountRepository.save(debitAccount.get());
                    System.out.println("Debit: " + savedDebitAccount);
                }
            }
        }


        Ledger newLedger = Ledger.builder()
                .setDate(reqLedger.getDate())
                .setDescription(reqLedger.getDescription())
                .setCredit_account(savedCreditAccount)
                .setCredit_amount(reqLedger.getCredit_amount())
                .setDebit_account(savedDebitAccount)
                .setDebit_amount(reqLedger.getDebit_amount())
                .setCurrency_type("THB")
                .build();

        return ledgerService.save(newLedger);
    }


    @PatchMapping("/ledger")
    @Transactional
    public Ledger updateLedger(@RequestBody Ledger reqLedger) {
        Optional<Ledger> theLedger = ledgerService.findById(reqLedger.getId());
        System.out.println("Before: ");
        System.out.println(theLedger);

        Account savedCreditAccount = null;
        Account savedDebitAccount = null;

        if (reqLedger.getCredit_account() != null) {
            if (reqLedger.getCredit_amount().compareTo(BigDecimal.valueOf(0)) > 0) {
                Optional<Account> creditAccount = accountRepository.findById(reqLedger.getCredit_account().getId());

                System.out.println(creditAccount);

                if (creditAccount.isPresent()) {
                    creditAccount.get().setPreviousBalance(creditAccount.get().getBalance());
                    creditAccount.get().setBalance(creditAccount.get().getBalance().subtract(
                            reqLedger.getCredit_amount().subtract(theLedger.get().getCredit_amount())
                    ));
                    creditAccount.get().setUpdateDate(reqLedger.getCredit_account().getUpdateDate());
                    savedCreditAccount = accountRepository.save(creditAccount.get());
                    System.out.println("Credit: " + savedCreditAccount);
                }
            }
        }

        if (reqLedger.getDebit_account() != null) {
            if (reqLedger.getDebit_amount().compareTo(BigDecimal.valueOf(0)) > 0 ) {
                Optional<Account> debitAccount = accountRepository.findById(reqLedger.getDebit_account().getId());

                System.out.println(debitAccount);

                if (debitAccount.isPresent()) {
                    debitAccount.get().setPreviousBalance(debitAccount.get().getBalance());
                    debitAccount.get().setBalance(debitAccount.get().getBalance().add(
                            reqLedger.getDebit_amount().subtract(theLedger.get().getDebit_amount())
                    ));
                    debitAccount.get().setUpdateDate(reqLedger.getDebit_account().getUpdateDate());
                    savedDebitAccount = accountRepository.save(debitAccount.get());
                    System.out.println("Debit: " + savedDebitAccount);
                }
            }
        }

        if (theLedger.isEmpty()) {
            throw new RuntimeException("Ledger record not found for update - " + reqLedger.getId());
        } else {
            theLedger.get().setId(reqLedger.getId());
            theLedger.get().setDate(reqLedger.getDate());
            theLedger.get().setDescription(reqLedger.getDescription());
            theLedger.get().setCredit_account(savedCreditAccount);
            theLedger.get().setCredit_amount(reqLedger.getCredit_amount());
            theLedger.get().setDebit_account(savedDebitAccount);
            theLedger.get().setDebit_amount(reqLedger.getDebit_amount());
            theLedger.get().setCurrency_type("THB");
            return ledgerService.save(theLedger.get());
        }

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
}

package com.example.app.demo.Account;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    private final AccountRepository accountRepository;

    public AccountRestController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts")
    public List<Account> findAll() {
        return accountRepository.findAllByOrderById();
    }

    @GetMapping("/account/{theId}")
    public Account findById(@PathVariable Integer theId) {

        Optional<Account> account = accountRepository.findById(theId);

        if (account.isEmpty()) {
            throw new RuntimeException("Ledger not found - " + theId);
        }

        return account.get();
    }

    @PostMapping("/accounts")
    public Account addAccount(@RequestBody Account theAccount) {
        Account account = new Account();
        account.setDesc(theAccount.getDesc());
        account.setBalance(theAccount.getBalance());
        account.setPreviousBalance(theAccount.getPreviousBalance());
        account.setUpdateDate(LocalDate.now());

        Account newAccount = accountRepository.save(account);
        return newAccount;
    }
}

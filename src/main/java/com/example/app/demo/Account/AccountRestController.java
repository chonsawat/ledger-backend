package com.example.app.demo.Account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return accountRepository.findAll();
    }

    @GetMapping("/account/{theId}")
    public Account findById(@PathVariable Integer theId) {

        Optional<Account> account = accountRepository.findById(theId);

        if (account.isEmpty()) {
            throw new RuntimeException("Ledger not found - " + theId);
        }

        return account.get();
    }
}

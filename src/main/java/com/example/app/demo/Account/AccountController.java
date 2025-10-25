package com.example.app.demo.Account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts")
    public String accountListing(Model theModel) {
        List<Account> theAccounts = accountRepository.findAll();
        theModel.addAttribute("accounts", theAccounts);
        return "account/account-listing";
    }
}

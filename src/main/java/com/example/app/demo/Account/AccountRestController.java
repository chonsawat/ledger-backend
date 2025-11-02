package com.example.app.demo.Account;

import com.example.app.demo.Ledger.Ledger;
import com.example.app.demo.Ledger.LedgerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    private final AccountRepository accountRepository;
    private final LedgerService ledgerService;

    public AccountRestController(AccountRepository accountRepository, LedgerService ledgerService) {
        this.accountRepository = accountRepository;
        this.ledgerService = ledgerService;
    }

    @GetMapping("/accounts")
    public List<Account> findAll() {
        List<Account> accountsList =  accountRepository.findAllByOrderById();
        accountsList.forEach((Account item) ->
                item.setBalance(findById(item.getId()).getBalance()));

        return accountsList;
    }

    @GetMapping("/account/{theId}")
    public Account findById(@PathVariable Integer theId) {
        var ref = new Object() {
            BigDecimal totalCredit = BigDecimal.valueOf(0);
            BigDecimal totalDebit = BigDecimal.valueOf(0);
            Account accountDetail = new Account();
        };
        List<Ledger> allLedger = ledgerService.findAll();
        List<Ledger> allCreditLedger = allLedger.stream()
                .filter((Ledger item) -> {
                    if (item.getCredit_account() != null)
                        return Objects.equals(item.getCredit_account().getId(), theId);
                    return false;
                })
                .toList();
        List<Ledger> allDebitLedger = allLedger.stream()
                .filter((Ledger item) -> {
                    if (item.getDebit_account() != null)
                        return Objects.equals(item.getDebit_account().getId(), theId);
                    return false;
                })
                .toList();

        allCreditLedger.forEach((Ledger ledger) -> {
                ref.totalCredit = ref.totalCredit.add(ledger.getCredit_amount());
        });
        allDebitLedger.forEach((Ledger ledger) -> {
                ref.totalDebit = ref.totalDebit.add(ledger.getDebit_amount());
        });

        Optional<Account> account = accountRepository.findById(theId);
        account.ifPresent(value -> {
            ref.accountDetail = Account.builder()
                    .setId(value.getId())
                    .setDesc(value.getDesc())
                    .setBalance(value.getOriginal_balance()
                            .subtract(ref.totalCredit)
                            .add(ref.totalDebit))
                    .setOriginal_balance(value.getOriginal_balance())
                    .setUpdateDate(value.getUpdateDate())
                    .setPreviousBalance(value.getBalance())
                    .build();
        });
        account.orElseThrow(() -> new RuntimeException("Accounts not Found"));
        return ref.accountDetail;
    }

    @PostMapping("/accounts")
    public Account addAccount(@RequestBody Account theAccount) {
        Account account = new Account();
        account.setDesc(theAccount.getDesc());
        account.setBalance(theAccount.getBalance());
        account.setPreviousBalance(theAccount.getPreviousBalance());
        account.setUpdateDate(LocalDate.now());
        return accountRepository.save(account);
    }
}

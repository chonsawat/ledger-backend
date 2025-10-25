package com.example.app.demo.Ledger;

import com.example.app.demo.Account.Account;
import com.example.app.demo.Account.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class LedgerController {
    private LedgerService ledgerService;
    private AccountRepository accountRepository;

    public LedgerController(LedgerService ledgerService, AccountRepository accountRepository) {
        this.ledgerService = ledgerService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/ledger")
    public String getLedgers(Model theModel) {
        List<Ledger> theLedger = ledgerService.findAllByOrderByDateDescAndByid();

        theModel.addAttribute("ledgers", theLedger);
        return "ledger/list-ledger";
    }

    @GetMapping("/ledger/add")
    public String addLedger(Model theModel) {
        Ledger ledger = new Ledger("mob-krungthai", "THB");
        ledger.setDate(LocalDate.now());

        theModel.addAttribute("ledger", ledger);
        return "ledger/add-ledger";
    }

    @PostMapping("/ledger/add/save")
    public String saveLedger(@ModelAttribute("ledger") Ledger theModel) {
        theModel.setCurrency_type("THB");
        ledgerService.save(theModel);
        return "redirect:/ledger";
    }

    @GetMapping("/ledger/update")
    public String updateLedger(@RequestParam("id") Integer theId, Model theModel) {
        Optional<Ledger> theLedger = ledgerService.findById(theId);

        if (theLedger.isPresent()) {
            if (theLedger.get().getCredit_account_id() != null) {
                Optional<Account> theCreditAccount = accountRepository.findById(theLedger.get().getCredit_account_id());
                theModel.addAttribute("credit_acc", theCreditAccount);
            } else {
                theModel.addAttribute("credit_acc", new Account());
            }

            if (theLedger.get().getDebit_account_id() != null) {
                Optional<Account> theDebitAccount = accountRepository.findById(theLedger.get().getDebit_account_id());
                theModel.addAttribute("debit_acc", theDebitAccount);
            } else {
                theModel.addAttribute("debit_acc", new Account());
            }
        }

        theModel.addAttribute("ledger", theLedger);
        return "ledger/update-ledger";
    }

    @PostMapping("/ledger/update/save")
    public String saveUpdateLedger(@ModelAttribute("ledger") Ledger theModel) {
        ledgerService.save(theModel);
        return "redirect:/ledger";
    }
}

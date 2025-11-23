package com.example.app.demo.Dashboard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.demo.Error.ErrorRestResponseException;
import com.example.app.demo.Ledger.Ledger;
import com.example.app.demo.Ledger.LedgerRepository;


@RestController
@RequestMapping("/api")
public class DashboardController {
    
    private LedgerRepository ledgerRepository;

    public DashboardController(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @GetMapping("/dashboardTotal")
    // @GetMapping("/mob")
    public Object summaryByTotal() {
        var ref = new Object() {
            BigDecimal totalCredit = BigDecimal.valueOf(0);
            BigDecimal totalDebit = BigDecimal.valueOf(0);
        };
        var ledgers = ledgerRepository.findAll();
        ledgers.forEach((Ledger item) -> {
            try {
                if (item.getCredit_amount() != null)
                    ref.totalCredit = ref.totalCredit.add(item.getCredit_amount());
                
                if (item.getDebit_amount() != null)
                    ref.totalDebit = ref.totalDebit.add(item.getDebit_amount());

            } catch (Exception e) {
                throw new ErrorRestResponseException(e.toString());
            }
        });

        var data = new HashMap<String, Object>();
        data.put("totalCredit", ref.totalCredit);
        data.put("totalDebit", ref.totalDebit);
        return data;
    }
    
}

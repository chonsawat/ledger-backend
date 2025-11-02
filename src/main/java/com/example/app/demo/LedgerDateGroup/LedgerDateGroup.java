package com.example.app.demo.LedgerDateGroup;

import com.example.app.demo.Ledger.Ledger;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LedgerDateGroup {
    private LocalDate date;
    private List<Ledger> ledgerList;
}

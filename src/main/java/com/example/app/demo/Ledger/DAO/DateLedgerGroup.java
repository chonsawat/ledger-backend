package com.example.app.demo.Ledger.DAO;

import com.example.app.demo.Ledger.Ledger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DateLedgerGroup {
    private LocalDate date;
    private List<Ledger> data;
}

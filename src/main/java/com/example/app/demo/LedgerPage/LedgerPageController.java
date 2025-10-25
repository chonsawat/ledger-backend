package com.example.app.demo.LedgerPage;

import com.example.app.demo.Ledger.Ledger;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api")
public class LedgerPageController {

    private LedgerPageRepository ledgerPageRepository;

}

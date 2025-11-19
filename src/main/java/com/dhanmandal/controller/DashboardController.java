package com.dhanmandal.controller;

import com.dhanmandal.dto.Payment;
import com.dhanmandal.dto.Receipt;
import com.dhanmandal.service.CollectionsService;
import com.dhanmandal.service.ExpendituresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ExpendituresService expendituresService;

    @Autowired
    private CollectionsService collectionsService;



    @GetMapping
    public String getDashboard(Model model) {
        double totalCollections = 0;
        double totalExpenditures = 0;

        List<Receipt> receipts = collectionsService.getCollections();
        List<Payment> payments = expendituresService.getPayments();
        if(receipts != null && !receipts.isEmpty()) {
            totalCollections = receipts.stream().mapToDouble(Receipt::getAmount).sum();
        }

        if(payments != null && !payments.isEmpty()) {
            totalExpenditures = payments.stream().mapToDouble(Payment::getEamount).sum();
        }

        model.addAttribute("collections", receipts);
        model.addAttribute("expenditures", payments);
        model.addAttribute("totalCollections", totalCollections);
        model.addAttribute("totalExpenditures", totalExpenditures);
        model.addAttribute("totalRemaining", totalCollections - totalExpenditures);
        return "collection";
    }
}
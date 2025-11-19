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
        double totalOfflineCollections = 0;
        double totalOnlineCollections = 0;

        double totalExpenditures = 0;
        double totalOfflineExpenditures = 0;
        double totalOnlineExpenditures = 0;


        List<Receipt> receipts = collectionsService.getCollections();
        List<Payment> payments = expendituresService.getPayments();
        model.addAttribute("collections", receipts);
        model.addAttribute("expenditures", payments);
        if(receipts != null && !receipts.isEmpty()) {
            totalCollections = receipts.stream().mapToDouble(Receipt::getAmount).sum();
            totalOfflineCollections = receipts.stream().filter(i -> i.getTransactionType().equalsIgnoreCase("offline"))
                    .mapToDouble(Receipt::getAmount).sum();
            totalOnlineCollections = receipts.stream().filter(i -> i.getTransactionType().equalsIgnoreCase("online"))
                    .mapToDouble(Receipt::getAmount).sum();
        }
        model.addAttribute("totalOfflineCollections", totalOfflineCollections);
        model.addAttribute("totalOnlineCollections", totalOnlineCollections);
        model.addAttribute("totalCollections", totalCollections);

        if(payments != null && !payments.isEmpty()) {
            totalExpenditures = payments.stream().mapToDouble(Payment::getEamount).sum();
            totalOfflineExpenditures = payments.stream().filter(i -> i.getEtransactionType().equalsIgnoreCase("offline"))
                    .mapToDouble(Payment::getEamount).sum();
            totalOnlineExpenditures = payments.stream().filter(i -> i.getEtransactionType().equalsIgnoreCase("online"))
                    .mapToDouble(Payment::getEamount).sum();
        }
        model.addAttribute("totalExpenditures", totalExpenditures);
        model.addAttribute("totalOfflineExpenditures", totalOfflineExpenditures);
        model.addAttribute("totalOnlineExpenditures", totalOnlineExpenditures);

        model.addAttribute("totalRemaining", totalCollections - totalExpenditures);
        model.addAttribute("totalOfflineRemaining", totalOfflineCollections - totalOfflineExpenditures);
        model.addAttribute("totalOnlineRemaining", totalOnlineCollections - totalOnlineExpenditures);

        return "collection";
    }
}
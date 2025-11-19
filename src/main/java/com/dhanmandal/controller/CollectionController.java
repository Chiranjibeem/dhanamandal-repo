package com.dhanmandal.controller;

import com.dhanmandal.dto.Collection;
import com.dhanmandal.dto.Payment;
import com.dhanmandal.dto.Receipt;
import com.dhanmandal.service.CollectionsService;
import com.dhanmandal.service.ExpendituresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CollectionController {

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private ExpendituresService expendituresService;

    @GetMapping("/collections/status")
    public String checkStatus() {
        return "server-status";
    }

    @PostMapping("/collections/collection")
    public String collect(@ModelAttribute("collection") Collection collection, Model model) {
        Receipt newReceipt = collectionsService.collectMoney(Receipt.builder()
                .receiptId(collection.getReceiptId())
                .name(collection.getName())
                .amount(collection.getAmount())
                .collectedBy(collection.getCollectedBy())
                .transactionType(collection.getTransactionType())
                .build());
        model.addAttribute("collection", newReceipt);
        return "redirect:/collections";
    }

    @GetMapping("/collections")
    public String getAllCollections(Model model) {
        double totalCollections = 0;
        double totalOfflineCollections = 0;
        double totalOnlineCollections = 0;

        double totalExpenditures = 0;
        double totalOfflineExpenditures = 0;
        double totalOnlineExpenditures = 0;

        List<Receipt> receipts = collectionsService.getCollections();
        List<Payment> payments = expendituresService.getPayments();
        model.addAttribute("expenditures", payments);
        model.addAttribute("collections", receipts);

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

    @GetMapping("/collections/edit/{id}")
    public String editCollection(@PathVariable String id, Model model) {
        Receipt receipt = collectionsService.findById(id);
        model.addAttribute("collection", receipt);
        return "edit-collection";
    }

    @GetMapping("/collections/print/{id}")
    public String printReceipt(@PathVariable String id, Model model) {
        Receipt receipt = collectionsService.findById(id);
        model.addAttribute("collection", receipt);
        return "receipt-print";
    }

    @GetMapping("/collections/delete/{id}")
    public String deleteCollection(@PathVariable String id, Model model) {
        collectionsService.deleteById(id);
        return "redirect:/collections";
    }

    @GetMapping("/collections/search")
    public String searchByName(@ModelAttribute("keyword") String keyword, Model model) {
        List<Receipt> receipts = collectionsService.searchByName(keyword);

        double totalCollections = 0;
        double totalOfflineCollections = 0;
        double totalOnlineCollections = 0;

        double totalExpenditures = 0;
        double totalOfflineExpenditures = 0;
        double totalOnlineExpenditures = 0;

        List<Payment> payments = expendituresService.getPayments();
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


        model.addAttribute("keyword", keyword);
        model.addAttribute("collections", receipts);
        return "collection";
    }
}

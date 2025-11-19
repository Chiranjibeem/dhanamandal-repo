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
        double totalExpenditures = 0;

        List<Receipt> receipts = collectionsService.getCollections();
        List<Payment> payments = expendituresService.getPayments();
        if(receipts != null && !receipts.isEmpty()) {
            totalCollections = receipts.stream().mapToDouble(Receipt::getAmount).sum();
        }
        if(payments != null && !payments.isEmpty()) {
            totalExpenditures = payments.stream().mapToDouble(Payment::getEamount).sum();
        }
        model.addAttribute("expenditures", payments);
        model.addAttribute("collections", receipts);

        model.addAttribute("totalCollections", totalCollections);
        model.addAttribute("totalExpenditures", totalExpenditures);

        model.addAttribute("totalRemaining", totalCollections - totalExpenditures);

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
        double totalCollections = 0;
        double totalExpenditures = 0;
        List<Receipt> receipts = collectionsService.searchByName(keyword);
        List<Receipt> allReceipts = collectionsService.getCollections();
        List<Payment> payments = expendituresService.getPayments();
        if(allReceipts != null && !allReceipts.isEmpty()) {
            totalCollections = allReceipts.stream().mapToDouble(Receipt::getAmount).sum();
        }
        if(payments != null && !payments.isEmpty()) {
            totalExpenditures = payments.stream().mapToDouble(Payment::getEamount).sum();
        }

        model.addAttribute("expenditures", payments);
        model.addAttribute("totalCollections", totalCollections);
        model.addAttribute("totalExpenditures", totalExpenditures);
        model.addAttribute("totalRemaining", totalCollections - totalExpenditures);
        model.addAttribute("keyword", keyword);
        model.addAttribute("collections", receipts);
        return "collection";
    }
}

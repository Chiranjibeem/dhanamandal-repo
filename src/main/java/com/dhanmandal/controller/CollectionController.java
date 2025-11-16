package com.dhanmandal.controller;

import com.dhanmandal.dto.Collection;
import com.dhanmandal.dto.Receipt;
import com.dhanmandal.service.CollectionsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CollectionController {

    @Autowired
    private CollectionsService collectionsService;

    @GetMapping("/status")
    public String checkStatus() {
        return "server-status";
    }

    @PostMapping("/collection")
    public String collect(@ModelAttribute("collection") Collection collection, Model model) {
        Receipt newReceipt = collectionsService.collectMoney(Receipt.builder()
                .name(collection.getName())
                .amount(collection.getAmount())
                .collectedBy(collection.getCollectedBy())
                .transactionType(collection.getTransactionType())
                .build());
        model.addAttribute("collection", newReceipt);
        return "redirect:/";
    }

    @GetMapping
    public String getAllCollections(Model model) {
        List<Receipt> receipts = collectionsService.getCollections();
        model.addAttribute("collections", receipts);
        return "collection";
    }
}

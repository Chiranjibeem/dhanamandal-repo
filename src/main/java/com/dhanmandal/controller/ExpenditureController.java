package com.dhanmandal.controller;

import com.dhanmandal.dto.Expenditure;
import com.dhanmandal.dto.Payment;
import com.dhanmandal.dto.Receipt;
import com.dhanmandal.service.CollectionsService;
import com.dhanmandal.service.ExpendituresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ExpenditureController {

    @Autowired
    private ExpendituresService expendituresService;

    @Autowired
    private CollectionsService collectionsService;

    @GetMapping("/expenditures/status")
    public String checkStatus() {
        return "server-status";
    }

    @PostMapping("/expenditures/expenditure")
    public String collect(@ModelAttribute("expenditure") Expenditure expenditure, Model model) {
        Payment newPayment = expendituresService.sendMoney(Payment.builder()
                .paymentId(expenditure.getPaymentId())
                .descriptions(expenditure.getDescriptions())
                .eamount(expenditure.getEamount())
                .paymentBy(expenditure.getPaymentBy())
                .etransactionType(expenditure.getEtransactionType())
                .build());
        model.addAttribute("expenditure", newPayment);
        return "redirect:/expenditures";
    }

    @GetMapping("/expenditures")
    public String getAllPayments(Model model) {
        double totalCollections = 0;
        double totalOfflineCollections = 0;
        double totalOnlineCollections = 0;

        double totalExpenditures = 0;
        double totalOfflineExpenditures = 0;
        double totalOnlineExpenditures = 0;

        List<Payment> payments = expendituresService.getPayments();
        List<Receipt> receipts = collectionsService.getCollections();
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

        return "expenditure";
    }

    @GetMapping("/expenditures/edit/{id}")
    public String editPayment(@PathVariable String id, Model model) {
        Payment payment = expendituresService.findById(id);
        model.addAttribute("expenditure", payment);
        return "edit-expenditure";
    }

    @GetMapping("/expenditures/print/{id}")
    public String printPayment(@PathVariable String id, Model model) {
        Payment payment = expendituresService.findById(id);
        model.addAttribute("expenditure", payment);
        return "payment-print";
    }

    @GetMapping("/expenditures/delete/{id}")
    public String deletePayment(@PathVariable String id, Model model) {
        expendituresService.deleteById(id);
        return "redirect:/expenditures";
    }

    @GetMapping("/expenditures/search")
    public String searchByName(@ModelAttribute("ekeyword") String keyword, Model model) {
        List<Payment> payments = expendituresService.searchByDescriptions(keyword);

        double totalCollections = 0;
        double totalOfflineCollections = 0;
        double totalOnlineCollections = 0;

        double totalExpenditures = 0;
        double totalOfflineExpenditures = 0;
        double totalOnlineExpenditures = 0;

        List<Receipt> receipts = collectionsService.getCollections();
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


        model.addAttribute("ekeyword", keyword);
        model.addAttribute("expenditures", payments);
        return "expenditure";
    }
}

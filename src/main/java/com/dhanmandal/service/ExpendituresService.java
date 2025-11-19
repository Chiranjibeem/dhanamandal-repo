package com.dhanmandal.service;

import com.dhanmandal.dto.Expenditure;
import com.dhanmandal.dto.Payment;
import com.dhanmandal.dto.Receipt;
import com.dhanmandal.entity.Collections;
import com.dhanmandal.entity.Expenditures;
import com.dhanmandal.repository.CollectionsRepository;
import com.dhanmandal.repository.ExpendituresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpendituresService {

    @Autowired
    private ExpendituresRepository expendituresRepository;

    public Payment sendMoney(Payment payment) {
        Expenditures expenditures = expendituresRepository.save(Expenditures.from(payment));
        return Payment.from(expenditures);
    }

    public List<Payment> getPayments() {
        List<Expenditures> expenditures = expendituresRepository.findAll(Sort.by(Sort.Direction.DESC, "paymentDate"));
        return expenditures.stream().map(Payment::from).collect(Collectors.toList());
    }

    public Payment findById(String id) {
        Expenditures expenditures = expendituresRepository.findById(id).get();
        return Payment.from(expenditures);
    }

    public void deleteById(String id) {
        expendituresRepository.deleteById(id);
    }

    public List<Payment> searchByDescriptions(String descriptions) {
        List<Expenditures> expenditures = expendituresRepository.findByDescriptionsContainingIgnoreCase(descriptions);
        return expenditures.stream().map(Payment::from).collect(Collectors.toList());
    }
}

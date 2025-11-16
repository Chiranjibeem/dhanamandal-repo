package com.dhanmandal.entity;

import com.dhanmandal.dto.Receipt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "collections")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Collections {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "collection_date")
    private LocalDate collectionDate;
    @Column(name = "collected_by")
    private String collectedBy;


    public static Collections from(Receipt receipt) {
        return Collections.builder()
                .name(receipt.getName())
                .amount(receipt.getAmount())
                .collectedBy(receipt.getCollectedBy())
                .collectionDate(LocalDate.now())
                .transactionType(receipt.getTransactionType())
                .build();
    }

}


package com.dhanmandal.dto;

import com.dhanmandal.entity.Collections;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
    private String receiptId;
    @NonNull
    private String name;
    @NonNull
    private Double amount;
    @NonNull
    private String transactionType;
    private LocalDate collectionDate;
    private String collectedBy;

    public static Receipt from(Collections collections) {
        return Receipt.builder()
                .receiptId(collections.getId())
                .name(collections.getName())
                .amount(collections.getAmount())
                .collectedBy(collections.getCollectedBy())
                .collectionDate(collections.getCollectionDate())
                .transactionType(collections.getTransactionType())
                .build();
    }
}

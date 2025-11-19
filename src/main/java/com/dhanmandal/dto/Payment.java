package com.dhanmandal.dto;

import com.dhanmandal.entity.Expenditures;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String paymentId;
    @NonNull
    private String descriptions;
    @NonNull
    private Double eamount;
    @NonNull
    private String etransactionType;
    private LocalDate paymentDate;
    private String paymentBy;

    public static Payment from(Expenditures expenditure) {
        return Payment.builder()
                .paymentId(expenditure.getId())
                .descriptions(expenditure.getDescriptions())
                .eamount(expenditure.getAmount())
                .paymentBy(expenditure.getPaymentBy())
                .paymentDate(expenditure.getPaymentDate())
                .etransactionType(expenditure.getTransactionType())
                .build();
    }
}

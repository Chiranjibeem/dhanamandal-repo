package com.dhanmandal.entity;

import com.dhanmandal.dto.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "expenditures")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expenditures {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    @Column(name = "payment_by")
    private String paymentBy;


    public static Expenditures from(Payment payment) {
        return Expenditures.builder()
                .id(payment.getPaymentId())
                .descriptions(payment.getDescriptions())
                .amount(payment.getEamount())
                .paymentBy(payment.getPaymentBy())
                .paymentDate(LocalDate.now())
                .transactionType(payment.getEtransactionType())
                .build();
    }

}


package com.productManagement.PaymentService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.crypto.prng.RandomGenerator;

import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRANSACTION_DETAILS")
@Builder
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paymentId")
    private long id;
    @Column(name = "orderId")
    private long orderId;
    @Column(name = "paymentMode")
    private String paymentMode;
    @Column(name = "referenceNumber")
    private String referenceNumber;
    @Column(name = "Status")
    private  String paymentStatus;
    @Column(name = "Date")
    private Instant paymentDate;
    @Column(name = "Amount")
    private long amount;
}

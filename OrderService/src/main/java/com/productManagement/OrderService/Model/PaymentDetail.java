package com.productManagement.OrderService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetail {

    private long paymentId;
    private String paymentStatus;
    private long orderId;
    private long amount;
    private String paymentMode;

    private Instant paymentDate;
}

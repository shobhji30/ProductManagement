package com.productManagement.OrderService.External.Request;

import com.productManagement.OrderService.Model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private long orderId;
    private String referenceNumber;
    private long amount;

    private PaymentMode paymentMode;
}

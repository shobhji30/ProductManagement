package com.productManagement.PaymentService.Service;

import com.productManagement.PaymentService.Model.PaymentRequest;
import com.productManagement.PaymentService.Model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}

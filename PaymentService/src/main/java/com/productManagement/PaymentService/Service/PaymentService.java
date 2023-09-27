package com.productManagement.PaymentService.Service;

import com.productManagement.PaymentService.Model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}

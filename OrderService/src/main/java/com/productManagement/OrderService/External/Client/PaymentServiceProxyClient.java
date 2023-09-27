package com.productManagement.OrderService.External.Client;

import com.productManagement.OrderService.External.Request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE",url = "localhost:8081")
public interface PaymentServiceProxyClient {
    @PostMapping("/payment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
}

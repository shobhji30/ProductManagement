package com.productManagement.PaymentService.Controller;

import com.productManagement.PaymentService.Model.PaymentRequest;
import com.productManagement.PaymentService.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        Long paymentId=paymentService.doPayment(paymentRequest);

        return new ResponseEntity<>(paymentId, HttpStatus.OK);

    }

}

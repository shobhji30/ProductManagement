package com.productManagement.PaymentService.Service;

import com.productManagement.PaymentService.Entity.PaymentEntity;
import com.productManagement.PaymentService.Model.PaymentRequest;
import com.productManagement.PaymentService.Model.PaymentResponse;
import com.productManagement.PaymentService.Repository.PaymentRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepo paymentRepo;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Initiating Payment");
        PaymentEntity paymentEntity= PaymentEntity.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        paymentRepo.save(paymentEntity);
        log.info("Payment Done");

        return paymentEntity.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Fetching Payment details for orderId {} ",orderId);
        PaymentEntity paymentEntity=paymentRepo.findByOrderId(orderId);
        PaymentResponse paymentResponse=PaymentResponse.builder()
                .paymentId(paymentEntity.getId())
                .paymentStatus(paymentEntity.getPaymentStatus())
                .paymentDate(paymentEntity.getPaymentDate())
                .amount(paymentEntity.getAmount())
                .orderId(paymentEntity.getOrderId())
                .paymentMode(paymentEntity.getPaymentMode())
                .build();

        return paymentResponse;
    }
}

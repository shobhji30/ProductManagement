package com.productManagement.PaymentService.Repository;

import com.productManagement.PaymentService.Entity.PaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends CrudRepository<PaymentEntity, Long> {
}

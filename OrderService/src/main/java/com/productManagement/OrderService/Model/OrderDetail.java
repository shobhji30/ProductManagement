package com.productManagement.OrderService.Model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    public Long orderId;
    public Instant orderDate;
    public String orderStatus;
    public Long amount;

    private ProductDetail productDetail;
}

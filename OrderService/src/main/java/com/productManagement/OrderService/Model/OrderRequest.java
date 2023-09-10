package com.productManagement.OrderService.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    public Long productId;
    public Long quantity;
    public PaymentMode paymentMode;
    public Long amount;

}

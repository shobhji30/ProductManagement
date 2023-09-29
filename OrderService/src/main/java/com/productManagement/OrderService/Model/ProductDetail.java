package com.productManagement.OrderService.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetail {

    private Long productId;

    private String productName;

    private long productPrice;
    private long productQuantity;
}

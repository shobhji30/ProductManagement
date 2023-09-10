package com.productManagement.OrderService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @Column(name = "OrderId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "ProductId")
    public Long productId;
    @Column(name = "Quantity")
    public Long quantity;
    @Column(name = "OrderDate")
    public Instant orderDate;
    @Column(name = "OrderStatus")
    public String orderStatus;
    @Column(name = "OrderAmount")
    public Long amount;

}

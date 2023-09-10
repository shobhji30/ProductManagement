package com.productManagement.OrderService.Service;

import com.productManagement.OrderService.Entity.Order;
import com.productManagement.OrderService.Model.OrderRequest;
import com.productManagement.OrderService.Repository.OrderRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepository orderRepository;
    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.debug("Creating order");
        Order order= Order.builder().
                quantity(orderRequest.quantity).
                productId(orderRequest.productId).
                orderDate(Instant.now()).
                orderStatus("CREATED").
                amount(orderRequest.amount).
                build();
        orderRepository.save(order);
        log.debug("Order created");
        return order.id;
    }
}

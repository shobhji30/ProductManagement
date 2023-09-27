package com.productManagement.OrderService.Service;

import com.productManagement.OrderService.Entity.Order;
import com.productManagement.OrderService.External.Client.PaymentServiceProxyClient;
import com.productManagement.OrderService.External.Client.ProductServiceProxyClient;
import com.productManagement.OrderService.External.Request.PaymentRequest;
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
    ProductServiceProxyClient productServiceProxyClient;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PaymentServiceProxyClient paymentServiceProxyClient;
    @Override
    public Long placeOrder(OrderRequest orderRequest) {

        log.info("Calling product service reduce api");
        productServiceProxyClient.reduceQuantity(orderRequest.productId, orderRequest.quantity);
        log.info("Creating order");
        Order order= Order.builder().
                quantity(orderRequest.quantity).
                productId(orderRequest.productId).
                orderDate(Instant.now()).
                orderStatus("CREATED").
                amount(orderRequest.amount).
                build();
        orderRepository.save(order);
        log.info("Order created");
        log.info("Calling Payment service");
        PaymentRequest paymentRequest= PaymentRequest.builder()
                .orderId(order.getId())
                .amount(orderRequest.getAmount())
                .paymentMode(orderRequest.getPaymentMode())
                .build();

        String orderStatus=null;

        try {
            paymentServiceProxyClient.doPayment(paymentRequest);
            log.info("Payment done successfully");
            orderStatus="PLACED";
        }catch (Exception e){
            log.error("Error Occurred while placing the order");
            orderStatus="FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        if (orderStatus.equals("PLACED")) {
            log.info("Order Placed successfully");
        }

        return order.id;
    }
}

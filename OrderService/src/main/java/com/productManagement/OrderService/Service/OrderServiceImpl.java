package com.productManagement.OrderService.Service;

import com.productManagement.OrderService.Entity.Order;
import com.productManagement.OrderService.Exception.CustomException;
import com.productManagement.OrderService.External.Client.PaymentServiceProxyClient;
import com.productManagement.OrderService.External.Client.ProductServiceProxyClient;
import com.productManagement.OrderService.External.Request.PaymentRequest;
import com.productManagement.OrderService.Model.OrderDetail;
import com.productManagement.OrderService.Model.OrderRequest;
import com.productManagement.OrderService.Model.PaymentDetail;
import com.productManagement.OrderService.Model.ProductDetail;
import com.productManagement.OrderService.Repository.OrderRepository;
import com.productManagement.PaymentService.Model.PaymentResponse;
import lombok.extern.log4j.Log4j2;
import com.productManagement.ProductService.Model.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    ProductServiceProxyClient productServiceProxyClient;
//    @Autowired
//    RestTemplate restTemplate;

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

    @Override
    public OrderDetail getOrderById(long id) {
        log.info("Getting order Detail with id {}",id);
        Optional<Order> order= Optional.ofNullable(orderRepository.findById(id).orElseThrow(() -> new CustomException("Order Not found with the given id", "Not_Found", 404)));

        RestTemplate restTemplate = new RestTemplate();
        log.info("Invoking Product Service to get product detail with product id {}",order.get().getProductId());
        ProductResponse productResponse=restTemplate.getForObject("http://localhost:8080/product/"+order.get().getProductId(), ProductResponse.class);

        ProductDetail productDetail=ProductDetail.builder()
                .productPrice(productResponse.getProductPrice())
                .productQuantity(productResponse.getProductQuantity())
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        log.info("Invoking Payment Service to get product detail with order id {}",order.get().getId());

        restTemplate=new RestTemplate();

        PaymentResponse paymentResponse=restTemplate.getForObject("http://localhost:8081/payment/order/"+order.get().getId(), PaymentResponse.class);

        PaymentDetail paymentDetail=PaymentDetail.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getPaymentStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .amount(paymentResponse.getAmount())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();



        OrderDetail orderDetail=OrderDetail.builder()
                .amount(order.get().getAmount())
                .orderDate(order.get().getOrderDate())
                .orderId(order.get().getId())
                .orderStatus(order.get().getOrderStatus())
                .productDetail(productDetail)
                .paymentDetail(paymentDetail)
                .build();
        return orderDetail;
    }
}

package com.productManagement.OrderService.Service;

import com.productManagement.OrderService.Entity.Order;
import com.productManagement.OrderService.External.Client.PaymentServiceProxyClient;
import com.productManagement.OrderService.External.Client.ProductServiceProxyClient;
import com.productManagement.OrderService.Model.OrderDetail;
import com.productManagement.OrderService.Repository.OrderRepository;
import com.productManagement.PaymentService.Model.PaymentResponse;
import com.productManagement.ProductService.Model.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    ProductServiceProxyClient productServiceProxyClient;
    @Mock
    RestTemplate restTemplate;

    @Mock
    OrderRepository orderRepository;
    @Mock
    PaymentServiceProxyClient paymentServiceProxyClient;
    @InjectMocks
    OrderService orderService=new OrderServiceImpl();
    @DisplayName("Get Order - Success Scenario")
    @Test
    public void when_order_success(){
        //Mocking
        Optional<Order> order = Optional.ofNullable(getMockOrder());
        when(orderRepository.findById(anyLong())).thenReturn((Optional<Order>)(order));

        PaymentResponse paymentResponse=getMockPaymentResponse();
        when(restTemplate.getForObject("http://localhost:8081/payment/order/"+order.get().getId(), PaymentResponse.class)).thenReturn(paymentResponse);

        ProductResponse productResponse=getMockProductResponse();
        when(restTemplate.getForObject("http://localhost:8080/product/"+order.get().getProductId(), ProductResponse.class)).thenReturn(productResponse);

        //Actual
        OrderDetail orderDetail=orderService.getOrderById(1);

        //Verify
        verify(orderRepository,times(1)).findById(anyLong());
        verify(restTemplate,times(1)).getForObject("http://localhost:8080/product/"+order.get().getProductId(), ProductResponse.class);
        verify(restTemplate,times(1)).getForObject("http://localhost:8081/payment/order/"+order.get().getId(), PaymentResponse.class);

        //Assertion
        assertNotNull(orderDetail);
        assertEquals(order.get().getId(), orderDetail.getOrderId());
    }

    private ProductResponse getMockProductResponse() {
        return ProductResponse.builder()
                .productQuantity(2)
                .productPrice(100)
                .productName("Iphone")
                .productId(Long.valueOf(2))
                .build();
    }

    private PaymentResponse getMockPaymentResponse() {
        return PaymentResponse.builder()
                .paymentId(2)
                .orderId(1)
                .paymentMode("UPI")
                .paymentStatus("SUCCESS")
                .paymentDate(Instant.now())
                .amount(100)
                .build();
    }

    private Order getMockOrder() {
        return Order.builder()
                .orderStatus("PLACED")
                .amount(Long.valueOf(100))
                .orderDate(Instant.now())
                .id(Long.valueOf(1))
                .quantity(Long.valueOf(2))
                .productId(Long.valueOf(2))
                .build();
    }

}
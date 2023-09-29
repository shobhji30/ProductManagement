package com.productManagement.OrderService.Controller;

import com.productManagement.OrderService.Model.OrderDetail;
import com.productManagement.OrderService.Model.OrderRequest;
import com.productManagement.OrderService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){

        Long orderId= orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(orderId, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrder(@PathVariable long id){
        OrderDetail orderDetail=orderService.getOrderById(id);
        return new ResponseEntity(orderDetail,HttpStatus.OK);
    }
}

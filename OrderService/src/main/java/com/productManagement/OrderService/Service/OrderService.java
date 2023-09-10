package com.productManagement.OrderService.Service;

import com.productManagement.OrderService.Model.OrderRequest;

public interface OrderService{
    Long placeOrder(OrderRequest orderRequest);
}

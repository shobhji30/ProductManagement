package com.productManagement.OrderService.Repository;

import com.productManagement.OrderService.Entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}

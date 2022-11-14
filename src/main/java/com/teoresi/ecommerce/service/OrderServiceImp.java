package com.teoresi.ecommerce.service;

import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public double getSales() {
        List<Order> orders = orderRepository.findAllByStatus(true);
        double totSales = 0.0;
        for (Order o : orders) {
            totSales += o.getTotalAmount();
        }
        return totSales;

    }
}

package com.teoresi.ecommerce.service;

import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    OrderRepository orderRepository;


    @Override
    public List<Order> Carrello(User customer, boolean x) {
        return orderRepository.findByCustomerAndStatus(customer, x);
    }


    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}

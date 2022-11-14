package com.teoresi.ecommerce.service;



import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.User;


import java.util.List;

public interface OrderService {
    List<Order> Carrello(User customer, boolean x);
    void save(Order order);
}

package com.teoresi.ecommerce.service;





import com.teoresi.ecommerce.model.Order;

import java.util.List;

public interface OrderService {
    public double getTotal(List<Order> cart);
    public Order getOrderById(long id);
    public List<Order> getCart();
    public void save(Order order);
    public void remove(Order order);
}

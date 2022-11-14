package com.teoresi.ecommerce.service;

import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.repository.OrderRepository;
import com.teoresi.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
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

    @Override
    public double getTotal(List<Order> cart) {
        double res = 0;
        for (Order o : cart) {
            res = res + o.getTotalAmount();
        }
        return res;
    }

    @Override
    public Order getOrderById(long id) {
        Optional<Order> res = orderRepository.findById(id);
        if (res.isPresent()){
            return res.get();
        } else
            throw new IllegalArgumentException("Errore in getOrderById() di OrderServiceImp");
    }

    @Override
    public List<Order> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<ArrayList<Order>> res = orderRepository.findByCustomerAndStatus(user ,false);
        if (res.isPresent()){
            //System.out.println(res.get());
            return res.get();
        } else
            throw new IllegalArgumentException("Errore in getCart() di OrderServiceImp");
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }


    @Override
    public void remove(Order order) {
        orderRepository.delete(order);
    }
}

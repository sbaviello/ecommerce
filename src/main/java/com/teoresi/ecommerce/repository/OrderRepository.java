package com.teoresi.ecommerce.repository;


import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAllByStatus(boolean status);
    List<Order> findByCustomerAndStatus(User customer, boolean x);
}

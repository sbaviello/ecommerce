package com.teoresi.ecommerce.repository;


import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<ArrayList<Order>> findByCustomerAndStatus(User user, boolean status);
}

package com.teoresi.ecommerce.repository;


import com.teoresi.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByNameContaining(String name);
    public List<Product> findByPriceLessThan(double price);
    public List<Product> findByCategory(String category);
}

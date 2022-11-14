package com.teoresi.ecommerce.service;

import com.teoresi.ecommerce.model.Product;
import com.teoresi.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{
    private ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> getByPriceLessThan(double price) {
        System.out.println("Prezzo al service: "+price);
        return productRepository.findByPriceLessThan(price);
    }

    @Override
    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product getById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
            return product.get();
        else
            throw new IllegalArgumentException("Nessun prodotto con id "+id);
    }

}

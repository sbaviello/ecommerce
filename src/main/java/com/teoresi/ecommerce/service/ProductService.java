package com.teoresi.ecommerce.service;



import com.teoresi.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();
    public List<Product> getByName(String name);
    public List<Product> getByPriceLessThan(double price);
    public List<Product> getByCategory(String category);
    public Product getById(long id);
    public void save(Product product);
    public void deleteById(long id);

}

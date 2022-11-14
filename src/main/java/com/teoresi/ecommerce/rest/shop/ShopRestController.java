package com.teoresi.ecommerce.rest.shop;

import com.teoresi.ecommerce.model.Product;
import com.teoresi.ecommerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopRestController {
    private ProductRepository productRepository;

    public ShopRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> getProductList(){
        return productRepository.findAll();
    }
}

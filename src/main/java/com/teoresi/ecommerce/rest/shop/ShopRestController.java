package com.teoresi.ecommerce.rest.shop;

import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.Product;
import com.teoresi.ecommerce.model.Ruolo;
import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.repository.ProductRepository;
import com.teoresi.ecommerce.service.OrderServiceImp;
import com.teoresi.ecommerce.service.ProductServiceImp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shop")
public class ShopRestController {
    private ProductServiceImp productServiceImp;
    private OrderServiceImp orderServiceImp;

    public ShopRestController(ProductServiceImp productServiceImp, OrderServiceImp orderServiceImp) {
        this.productServiceImp = productServiceImp;
        this.orderServiceImp = orderServiceImp;
    }

    @GetMapping("/products")
    public List<Product> getProductList(){
        return productServiceImp.getAll();
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable("productId") long productId){
        Product res = productServiceImp.getById(productId);
        if (res != null){
            return res;
        } else
            throw new IllegalArgumentException("Prodotto con id "+productId+" non trovato.");
    }

    @PutMapping("/products/{productId}/{qty}")
    public String addProductToCart(@PathVariable("productId") long productId, @PathVariable("qty") int qty){
        Product p = productServiceImp.getById(productId);
        if (p != null){
            if (qty > 0){
                if (qty <= p.getAvailableQuantity()) {
                    //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    //User user = (User) authentication.getPrincipal();

                    User user = new User();
                    user.setId(1);
                    user.setRuolo(Ruolo.USER);

                    Order o = new Order();
                    o.setCustomer(user);
                    o.setProduct(p);
                    o.setQuantity(qty);
                    o.setStatus(false);
                    o.setTotalAmount(qty * p.getPrice());

                    orderServiceImp.save(o);
                    return "Hai aggiunto al carrello X"+o.getQuantity()+" "+p.getName()+" (id: "+p.getId()+").";

                } else {
                    throw new IllegalArgumentException("La quantità inserita (" + qty + ") eccede le scorte in magazzino (" + p.getAvailableQuantity() + ").");
                }
            } else {
                throw new IllegalArgumentException("Quantità inserita ("+qty+") non valida.");
            }
        } else
            throw new IllegalArgumentException("Prodotto con id "+productId+" non trovato.");
    }
}

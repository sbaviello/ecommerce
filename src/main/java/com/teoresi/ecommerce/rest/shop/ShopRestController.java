package com.teoresi.ecommerce.rest.shop;

import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.Product;
import com.teoresi.ecommerce.model.Ruolo;
import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.service.OrderService;
import com.teoresi.ecommerce.service.ProductService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopRestController {
    private ProductService productService;
    private OrderService orderService;

    private final KafkaTemplate kafkaTemplate;

    public ShopRestController(KafkaTemplate kafkaTemplate, ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/products")
    public List<Product> getProductList(){
        return productService.getAll();
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable("productId") long productId){
        Product res = productService.getById(productId);
        if (res != null){
            return res;
        } else
            throw new IllegalArgumentException("Prodotto con id "+productId+" non trovato.");
    }

    @PutMapping("/products/{productId}/{qty}")
    public String addProductToCart(@PathVariable("productId") long productId, @PathVariable("qty") int qty){
        Product p = productService.getById(productId);
        if (p != null){
            if (qty > 0){
                if (qty <= p.getAvailableQuantity()) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    User user = (User) authentication.getPrincipal();

                    //user.setId(1);
                    //user.setRuolo(Ruolo.USER);

                    Order o = new Order();
                    o.setCustomer(user);
                    o.setProduct(p);
                    o.setQuantity(qty);
                    o.setStatus(false);
                    o.setTotalAmount(qty * p.getPrice());

                    orderService.save(o);


                    List<Order> cart = orderService.getCart();
                    int totalQty = 0;

                    for (Order ord : cart) {
                        if (p.getId() == ord.getProduct().getId()){
                            totalQty = totalQty + (ord.getQuantity());
                        }
                    }

                    if (totalQty >= 5){
                        //manda messaggio in coda ad AdministratorService
                        kafkaTemplate.send("ecommerce","L'user "+user.getId()+" ("+user.getEmail()+") ha nel carrello almeno 5 articoli con id "+p.getId()+" ("+p.getName()+").");
                        System.out.println("PIU DI 5 PRODOTTI NEL CARRELLO");
                    }


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

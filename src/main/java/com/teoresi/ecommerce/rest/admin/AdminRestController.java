package com.teoresi.ecommerce.rest.admin;

import com.teoresi.ecommerce.model.Product;
import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.service.OrderService;
import com.teoresi.ecommerce.service.ProductService;
import com.teoresi.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private ProductService productService;
    private WebClient.Builder webClientBuilder;
    private UserService userService;
    private OrderService orderService;
    private KafkaTemplate<String, String> kafkaTemplate;
    private List<String> messages;

    @Autowired
    public AdminRestController(ProductService productService, WebClient.Builder webClientBuilder,
                               UserService userService, OrderService orderService, KafkaTemplate kafkaTemplate) {
        this.productService = productService;
        this.webClientBuilder = webClientBuilder;
        this.userService = userService;
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;
        this.messages = new ArrayList<>();
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        this.productService.save(product);
        return product;
    }

    @DeleteMapping("/delete/{idProduct}")
    public String deleteProduct(@PathVariable long idProduct) {
        /*Product product = webClientBuilder.build()
                .get()
                .uri("localhost:8081/api/shop/products/" + idProduct)
                .retrieve().bodyToMono(Product.class).block();*/
        Product product = this.productService.getById(idProduct);
        if (product != null) {
            this.productService.deleteById(idProduct);
            return "Prodotto con codice " + idProduct + " eliminato";
        } else {
            throw new RuntimeException("Nessun prodotto con id " + idProduct);
        }
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        /*Product product = webClientBuilder.build()
                .get()
                .uri("localhost:8081/api/shop/products/" + idProduct)
                .retrieve().bodyToMono(Product.class).block();*/
        Product oldProduct = this.productService.getById(product.getId());
        if (oldProduct.getPrice() != product.getPrice()) {
            double oldPrice = oldProduct.getPrice();
            double newPrice = product.getPrice();
            // manda messaggio
            String msg = "Product " + product.getId() + " has a new price: from € " + oldPrice + " to € " + newPrice;
            kafkaTemplate.send("newprice", product.getName() + ";" + oldPrice + ";" + newPrice);
        }
        this.productService.save(product);
        return product;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users = this.userService.getAllUsers();
        return users;
    }

    @GetMapping("/totSales")
    public double getTotSales() {
        double tot = this.orderService.getSales();
        return tot;
    }

    @KafkaListener(topics = {"ecommerce", "newuser"}, groupId = "groupId")
    @GetMapping("/messages/last")
    public String getLastMessage(String data) {
        this.messages.add(data);
        return "New message received: " + data;
    }

    @GetMapping("/messages")
    public List<String> getMessages() {
        return this.messages;
    }

}

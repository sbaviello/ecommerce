package com.teoresi.ecommerce.rest.user;

import com.teoresi.ecommerce.model.Order;
import com.teoresi.ecommerce.model.Product;
import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.repository.UserRepository;
import com.teoresi.ecommerce.service.OrderService;
import com.teoresi.ecommerce.service.ProductService;
import com.teoresi.ecommerce.service.UserServiceImpl;
import org.hibernate.internal.util.StringHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserServiceImpl userserviceimpl;
    private OrderService orderService;

    private ProductService productService;

    public UserRestController(UserServiceImpl userserviceimpl, OrderService orderService, ProductService productService) {
        this.userserviceimpl = userserviceimpl;
        this.orderService=orderService;
        this.productService=productService;
    }

    @GetMapping("/getUser")
    public List<User> getUsers(){
        return userserviceimpl.findAll();
    }

    @GetMapping("/getUser/{codice}")
    public User getuser(@PathVariable long codice){
        User user = userserviceimpl.findById(codice);
        return user;
    }


    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        userserviceimpl.saveUser(user);
        return user;
    }

    @GetMapping("/updateUser/{nome}/{cognome}")
    public String updateUser(@PathVariable String nome, @PathVariable String cognome){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User utente = (User) authentication.getPrincipal();
        utente.setNome(nome);
        utente.setCognome(cognome);
        userserviceimpl.save(utente);
        return "Aggiornato Correttamente";
    }


    @GetMapping("/cart")
    public List<Order> carrello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User utente = (User) authentication.getPrincipal();
        List<Order> OrderList = orderService.Carrello(utente,false);
        return OrderList;
    }

    @GetMapping("/cart/buy")
    public String acquista(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User utente = (User) authentication.getPrincipal();
        List<Order> ordini = orderService.Carrello(utente,false);

        for(Order ordine : ordini){
            ordine.setStatus(true);
            Product prodotto =  ordine.getProduct();
            prodotto.setAvailableQuantity(prodotto.getAvailableQuantity()-ordine.getQuantity());

            orderService.save(ordine);
            productService.save(prodotto);
        }
        return "Acquisto avvenuto con successo";
    }

}

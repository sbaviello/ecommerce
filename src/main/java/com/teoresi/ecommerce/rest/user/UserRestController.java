package com.teoresi.ecommerce.rest.user;

import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.repository.UserRepository;
import com.teoresi.ecommerce.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserServiceImpl userserviceimpl;

    public UserRestController(UserServiceImpl userserviceimpl) {
        this.userserviceimpl = userserviceimpl;
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


}
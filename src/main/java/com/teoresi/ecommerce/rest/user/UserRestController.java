package com.teoresi.ecommerce.rest.user;

import com.teoresi.ecommerce.model.User;
import com.teoresi.ecommerce.repository.UserRepository;
import com.teoresi.ecommerce.service.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserServiceImpl userserviceimpl;

    public UserRestController(UserServiceImpl userserviceimpl) {
        this.userserviceimpl = userserviceimpl;
    }

    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userserviceimpl.findAll();
    }
}

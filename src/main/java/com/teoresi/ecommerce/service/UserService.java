package com.teoresi.ecommerce.service;




import com.teoresi.ecommerce.model.User;

import java.util.List;

public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);
    public List<User> findAll();
    User findById(long codice);
    void save(User user);
}
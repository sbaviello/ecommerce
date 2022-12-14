package com.teoresi.ecommerce.repository;

import com.teoresi.ecommerce.model.Ruolo;
import com.teoresi.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRuolo(Ruolo ruolo);
    List<User> findAll();
    User findById(long codice);
}

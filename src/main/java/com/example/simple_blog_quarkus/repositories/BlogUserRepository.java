package com.example.simple_blog_quarkus.repositories;

import com.example.simple_blog_quarkus.model.BlogUser;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class BlogUserRepository implements PanacheRepository<BlogUser> {

    public Optional<BlogUser> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
}

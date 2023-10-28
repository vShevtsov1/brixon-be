package com.example.demo.users.services;

import com.example.demo.users.data.users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepo extends MongoRepository<users, String> {

    boolean existsByRegistrationCode(String code);
    users findByRegistrationCode(String code);
    users findByEmail(String email);
    boolean existsByEmail(String email);
}

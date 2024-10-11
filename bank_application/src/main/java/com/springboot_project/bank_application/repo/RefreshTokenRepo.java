package com.springboot_project.bank_application.repo;

import com.springboot_project.bank_application.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends MongoRepository<RefreshToken, String> {

  Optional<RefreshToken> findByTokenAndEmail(String refreshToken, String email);
}


package org.resumehub.backend.repository;

import org.resumehub.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(String email);

    Optional<User> findByEmail(String email);
}

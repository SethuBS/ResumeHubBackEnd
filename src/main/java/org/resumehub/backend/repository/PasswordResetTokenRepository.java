package org.resumehub.backend.repository;

import org.resumehub.backend.entity.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    PasswordResetToken findByResetToken(String resetToken);
}

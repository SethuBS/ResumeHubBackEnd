package org.resumehub.backend.repository;

import org.resumehub.backend.entity.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    PasswordResetToken findByResetToken(String resetToken);

    List<PasswordResetToken> findAllByUserId(String userId);
}

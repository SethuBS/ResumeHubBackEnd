package org.resumehub.backend.service;

import org.resumehub.backend.dto.PasswordResetTokenDTO;

public interface PasswordResetTokenService {
    PasswordResetTokenDTO generateResetToken(String userId);

    PasswordResetTokenDTO getResetToken(String resetToken);

    void deleteResetToken(String token);
}

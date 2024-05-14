package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.dto.PasswordResetTokenDTO;
import org.resumehub.backend.entity.PasswordResetToken;
import org.resumehub.backend.map.Mapper;
import org.resumehub.backend.repository.PasswordResetTokenRepository;
import org.resumehub.backend.service.PasswordResetTokenService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private static final Logger logger = LogManager.getLogger(PasswordResetTokenServiceImpl.class);

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetTokenDTO generateResetToken(String userId) {
        String token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date expiryDate = calendar.getTime();

        var newResetToken = new PasswordResetToken(
                token,
                expiryDate,
                userId
        );

        logger.info("generateResetToken Token: {}", newResetToken);
        passwordResetTokenRepository.save(newResetToken);
        return Mapper.mapToDto(newResetToken);
    }

    @Override
    public PasswordResetTokenDTO getResetToken(String resetToken) {
        var token = passwordResetTokenRepository.findByResetToken(resetToken);
        logger.info("getResetToken Token: {}", token);
        return Mapper.mapToDto(token);
    }

    @Override
    public void deleteResetToken(String token) {
        var resetToken = passwordResetTokenRepository.findByResetToken(token);
        logger.info("deleteResetToken Token: {}", token);
        if (resetToken != null) {
            passwordResetTokenRepository.delete(resetToken);
        }
    }

}

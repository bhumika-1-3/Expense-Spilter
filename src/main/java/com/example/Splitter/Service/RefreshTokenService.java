package com.example.Splitter.Service;

import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Entity.RefreshToken;
import com.example.Splitter.Repo.RefreshRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshRepo refreshRepo;

    public String createRefreshToken(AppUser user) {

        // Find existing refresh token or create a new one
        RefreshToken token = refreshRepo
                .findByUserUserId(user.getUserId())
                .orElse(new RefreshToken());

        // Associate token with the user if it's new
        token.setUser(user);

        // Generate new token string
        token.setToken(UUID.randomUUID().toString());

        // Set expiry date
        token.setExpiryDate(new Date(System.currentTimeMillis() + refreshTokenDurationMs));

        // Save token
        refreshRepo.save(token);

        return token.getToken();
    }

    public boolean isValid(RefreshToken token) {
        return token.getExpiryDate().after(new Date());
    }
}


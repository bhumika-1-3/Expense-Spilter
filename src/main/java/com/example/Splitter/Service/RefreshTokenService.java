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
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshRepo.save(refreshToken);
        return refreshToken.getToken();
    }

    public boolean isValid(RefreshToken token) {
        return token.getExpiryDate().after(new Date());
    }
}


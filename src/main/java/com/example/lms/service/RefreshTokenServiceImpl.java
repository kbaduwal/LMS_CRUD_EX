package com.example.lms.service;

import com.example.lms.entity.RefreshToken;
import com.example.lms.entity.User;
import com.example.lms.repository.RefreshTokenRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.refreshTokenExpirationMs}")
    private long refreshTokenExpireMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(String userName) {
        User user = userRepository.findByEmail(userName).get();
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken==null) {
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenExpireMs))
                    .user(userRepository.findByEmail(userName).get())
                    .build();
        }else {
            refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenExpireMs));
        }

        user.setRefreshToken(refreshToken);

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refreshTokenOb = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new RuntimeException( "Token not found."));

        if(refreshTokenOb.getExpiry().compareTo(Instant.now())<0) {
            refreshTokenRepository.delete(refreshTokenOb);
            throw new RuntimeException("Refresh token expired.");
        }

        return refreshTokenOb;
    }

}

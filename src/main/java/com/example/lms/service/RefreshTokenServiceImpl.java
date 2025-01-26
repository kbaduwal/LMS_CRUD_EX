package com.example.lms.service;

import com.example.lms.entity.RefreshToken;
import com.example.lms.repository.RefreshTokenRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl {

    private long refreshTokenExpireMs = 5*60*60*1000;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private RefreshToken createRefreshToken(String userName) {

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .expiry(Instant.now().plusMillis(refreshTokenExpireMs))
                .user(userRepository.findByEmail(userName).get())
                .build();

        return refreshToken;
    }

    private RefreshToken verifyRefreshToken(Long id) {
        RefreshToken refreshTokenOb = refreshTokenRepository.findById(id).orElseThrow(
                () -> new RuntimeException( "Token not found."));

        if(refreshTokenOb.getExpiry().compareTo(Instant.now())<0) {
            throw new RuntimeException("Refresh token expired.");

        }

        return refreshTokenOb;
    }

}

package com.example.lms.service;

import com.example.lms.entity.RefreshToken;

public interface RefreshTokenService {
    public RefreshToken createRefreshToken(String userName);
    public RefreshToken verifyRefreshToken(Long id);

}

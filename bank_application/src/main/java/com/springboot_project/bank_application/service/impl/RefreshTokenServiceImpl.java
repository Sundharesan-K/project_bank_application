package com.springboot_project.bank_application.service.impl;

import com.springboot_project.bank_application.dto.RefreshTokenRequest;
import com.springboot_project.bank_application.exception.RefreshTokenException;
import com.springboot_project.bank_application.model.AuthenticationResponse;
import com.springboot_project.bank_application.model.RefreshToken;
import com.springboot_project.bank_application.repo.RefreshTokenRepo;
import com.springboot_project.bank_application.service.JWTService;
import com.springboot_project.bank_application.service.RefreshTokenService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepo refreshTokenRepo;
  private final JWTService jwtService;

  @Override
  public String generateRefreshToken(String email, int seconds) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setEmail(email);
    refreshToken.setCreatedAt(Instant.now());
    refreshToken.setExpiresAt(Instant.now().plusSeconds(seconds));
    refreshTokenRepo.save(refreshToken);
    return refreshToken.getToken();
  }

  @Override
  public AuthenticationResponse refreshToken(RefreshTokenRequest tokenRequest) {
    RefreshToken refreshToken = validateRefreshToken(tokenRequest.getRefreshToken(),
        tokenRequest.getEmail());
    return AuthenticationResponse.builder()
        .authenticationToken(jwtService.generateToken(refreshToken.getEmail()))
        .refreshToken(refreshToken.getToken())
        .expiresAt(refreshToken.getExpiresAt())
        .email(refreshToken.getEmail())
        .build();

  }

  public RefreshToken validateRefreshToken(String token, String email) {
    return refreshTokenRepo.findByTokenAndEmail(token, email)
        .orElseThrow(() -> new RefreshTokenException("Invalid Refresh token"));
  }
}

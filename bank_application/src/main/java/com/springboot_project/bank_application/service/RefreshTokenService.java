package com.springboot_project.bank_application.service;

import com.springboot_project.bank_application.dto.RefreshTokenRequest;
import com.springboot_project.bank_application.model.AuthenticationResponse;

public interface RefreshTokenService {

  String generateRefreshToken(String email, int seconds);

  AuthenticationResponse refreshToken(RefreshTokenRequest tokenRequest);
}

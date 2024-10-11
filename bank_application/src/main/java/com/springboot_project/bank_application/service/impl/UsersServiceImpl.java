package com.springboot_project.bank_application.service.impl;

import com.springboot_project.bank_application.dto.LoginRequest;
import com.springboot_project.bank_application.dto.UsersDto;
import com.springboot_project.bank_application.model.AuthenticationResponse;
import com.springboot_project.bank_application.model.Users;
import com.springboot_project.bank_application.repo.UserRepo;
import com.springboot_project.bank_application.service.JWTService;
import com.springboot_project.bank_application.service.RefreshTokenService;
import com.springboot_project.bank_application.service.UsersService;
import java.lang.module.FindException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  @Value("${refresh-token-expire-in-seconds}")
  int refreshTokenExpireInSeconds;

  private final RefreshTokenService refreshTokenService;
  private final UserRepo userRepo;
  private final PasswordEncoder encoder;
  private final JWTService jwtService;
  private final AuthenticationManager manager;

  @Override
  public String registerUser(UsersDto usersDto) {
    Users user = userRepo.findByEmailId(usersDto.getEmailId());
    if (Objects.nonNull(user)) {
      throw new FindException("User with email ID " + usersDto.getEmailId() + " already exists");
    }
    Users users = new Users();
    users.setUsername(usersDto.getUsername());
    users.setLastname(usersDto.getLastname());
    users.setPassword(encoder.encode(usersDto.getPassword()));
    users.setEmailId(usersDto.getEmailId());
    users.setCreateTs(LocalDateTime.now());
    users.setUpdateTs(LocalDateTime.now());
    userRepo.save(users);
    return "User registered successfully with email ID: " + usersDto.getEmailId();
  }

  @Override
  public AuthenticationResponse verifyUser(LoginRequest loginRequest) {
    Authentication authentication =
        manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(),
            loginRequest.getPassword()));
    if (authentication.isAuthenticated()) {
      return AuthenticationResponse.builder()
          .authenticationToken(jwtService.generateToken(loginRequest.getEmailId()))
          .refreshToken(refreshTokenService.generateRefreshToken(loginRequest.getEmailId(),
              refreshTokenExpireInSeconds))
          .expiresAt(Instant.now().plusSeconds(refreshTokenExpireInSeconds))
          .email(loginRequest.getEmailId())
          .build();
    }
    return null;
  }
}

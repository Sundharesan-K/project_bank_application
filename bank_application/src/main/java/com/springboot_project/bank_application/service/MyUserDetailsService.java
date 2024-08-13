package com.springboot_project.bank_application.service;

import com.springboot_project.bank_application.model.UserConfig;
import com.springboot_project.bank_application.model.Users;
import com.springboot_project.bank_application.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userRepo.findByEmailId(username);
    if (user == null) {
      throw new UsernameNotFoundException("user not found");
    }
    return new UserConfig(user);
  }
}

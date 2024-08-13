package com.springboot_project.bank_application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/check-point")
public class CheckController {

  @GetMapping
  public String check() {
    return "Welcome to the bank";
  }
}

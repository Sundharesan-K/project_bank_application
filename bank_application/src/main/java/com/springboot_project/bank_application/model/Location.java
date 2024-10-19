package com.springboot_project.bank_application.model;

import static com.springboot_project.bank_application.repo.AccountRepo.ADDRESS;
import static com.springboot_project.bank_application.repo.AccountRepo.CITY;
import static com.springboot_project.bank_application.repo.AccountRepo.COUNTRY;
import static com.springboot_project.bank_application.repo.AccountRepo.STATE;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Location {

  @Field(ADDRESS)
  private String address;

  @Field(CITY)
  private String city;

  @Field(STATE)
  private String state;

  @Field(COUNTRY)
  private String country;
}

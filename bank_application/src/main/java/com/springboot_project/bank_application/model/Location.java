package com.springboot_project.bank_application.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Location {

  @Field("address")
  private String address;

  @Field("city")
  private String city;

  @Field("state")
  private String state;

  @Field("country")
  private String country;
}

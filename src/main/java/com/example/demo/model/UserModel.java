package com.example.demo.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class UserModel {
  @Id
  private String id;

  @NotBlank
  public String firstName;

  @NotBlank
  public String lastName;

  public UserModel() {
  }

  public UserModel(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  // @Override
  public String toString() {
    return String.format("Customer[id=%s, firstName='%s', lastName='%s']", id, firstName, lastName);
  }
}
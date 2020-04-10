package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class UserModel {
  @Id
  private String id;

  public String firstName;
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
package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.UserModel;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepo extends MongoRepository<UserModel, String> {
  public UserModel findByFirstName(String firstName);

  public List<UserModel> findByLastName(String lastName);
}
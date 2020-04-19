package com.example.demo;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.example.demo.model.UserModel;
import com.example.demo.repository.IUserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
  private static final String template = "Hello %s !";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private IUserRepo repository;

  @GetMapping("/greet")
  public Greeting greet(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }

  @GetMapping("/test")
  public UserModel getUsers() {
    repository.deleteAll();
    return repository.save(new UserModel("Karan", null));
  }

  @GetMapping("/testget")
  public List<UserModel> getAllUsers() {
    return repository.findAll();
  }

  @PostMapping("/post")
  public ResponseEntity<Object> postUser(@RequestBody UserModel user) {
    repository.save(user);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
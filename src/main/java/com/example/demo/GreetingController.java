package com.example.demo;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.example.demo.model.UserModel;
import com.example.demo.repository.IUserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GreetingController {
  private static final String template = "Hello %s !";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private IUserRepo repository;

  public static void main(String[] args) {
    SpringApplication.run(GreetingController.class, args);
  }

  @GetMapping("/greet")
  public Greeting greet(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }

  @GetMapping("/test")
  public void getUsers() {
    repository.deleteAll();
    repository.save(new UserModel("Karan", "nehra"));
  }

  @GetMapping("/testget")
  public List<UserModel> getAllUsers() {
    return repository.findAll();
  }
}
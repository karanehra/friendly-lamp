package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;

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

  public static void main(String[] args) {
    SpringApplication.run(GreetingController.class, args);
  }

  @GetMapping("/greet")
  public Greeting greet(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));

  }
}
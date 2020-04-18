package com.example.demo.controllers;

import com.example.demo.Greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
  @GetMapping("/greet")
  public Greeting greet(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(0, String.format("Hello %s", name));
  }
}
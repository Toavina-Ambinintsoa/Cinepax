package com.example.demo.endpoint.rest.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> findAll() {
    return userService.getAll();
  }

  @PostMapping("register")
  public ResponseEntity<?> register(@RequestBody User user) {
    return ResponseEntity.status(200).body(userService.save(user));
  }
}

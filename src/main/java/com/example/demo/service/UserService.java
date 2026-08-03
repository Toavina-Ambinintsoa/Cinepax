package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Transactional(readOnly = true)
  public User getById(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("User not found: " + id));
  }

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  @Transactional
  public void delete(UUID id) {
    User user = getById(id);
    userRepository.delete(user);
  }

  @Transactional
  public User update(UUID id, User user) {
    try {
      getById(id);
      return userRepository.save(user);
    } catch (Exception e) {
      throw new NotFoundException("User not found: " + id);
    }
  }
}

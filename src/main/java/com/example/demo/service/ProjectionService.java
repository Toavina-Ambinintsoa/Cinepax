package com.example.demo.service;

import com.example.demo.entity.Projection;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProjectionRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectionService {

  private final ProjectionRepository projectionRepository;

  @Transactional(readOnly = true)
  public List<Projection> getAll() {
    return projectionRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Projection> getByMovie(UUID movieId) {
    return projectionRepository.findByMovie_Id(movieId);
  }

  @Transactional(readOnly = true)
  public Projection getById(UUID id) {
    return projectionRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Projection not found: " + id));
  }

  @Transactional
  public Projection save(Projection projection) {
    return projectionRepository.save(projection);
  }

  @Transactional
  public void delete(UUID id) {
    Projection projection = getById(id);
    projectionRepository.delete(projection);
  }

  @Transactional
  public Projection update(UUID id, Projection projection) {
    try {
      getById(id);
      return projectionRepository.save(projection);
    } catch (Exception e) {
      throw new NotFoundException("Projection not found: " + id);
    }
  }
}

package com.example.demo.service;

import com.example.demo.entity.Movie;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.MovieRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;

  @Transactional(readOnly = true)
  public List<Movie> getAll() {
    return movieRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Movie getById(UUID id) {
    return movieRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Movie not found: " + id));
  }

  @Transactional
  public Movie save(Movie movie) {
    return movieRepository.save(movie);
  }

  @Transactional
  public void delete(UUID id) {
    Movie movie = getById(id);
    movieRepository.delete(movie);
  }

  @Transactional
  public Movie update(UUID id, Movie movie) {
    try {
      getById(id);
      return movieRepository.save(movie);
    } catch (Exception e) {
      throw new NotFoundException("Movie not found: " + id);
    }
  }
}

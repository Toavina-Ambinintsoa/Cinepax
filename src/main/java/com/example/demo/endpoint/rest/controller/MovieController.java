package com.example.demo.endpoint.rest.controller;

import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class MovieController {
  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @PutMapping("{id}")
  public ResponseEntity<Movie> updateMovie(@PathVariable UUID id, @RequestBody Movie movie) {
    return ResponseEntity.status(200).body(movieService.update(id, movie));
  }
  
  @GetMapping()
  public ResponseEntity<List<Movie>> getAllMovies() {
    return ResponseEntity.status(200).body(movieService.getAll());
  }
}

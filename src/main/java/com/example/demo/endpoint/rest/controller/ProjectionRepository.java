package com.example.demo.endpoint.rest.controller;

import com.example.demo.entity.Projection;
import com.example.demo.service.ProjectionService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projection")
public class ProjectionRepository {
  private final ProjectionService projectionService;

  public ProjectionRepository(ProjectionService projectionService) {
    this.projectionService = projectionService;
  }

  @GetMapping()
  public ResponseEntity<List<Projection>> findAll() {
    return ResponseEntity.status(200).body(projectionService.getAll());
  }

  @PutMapping("{id}")
  public ResponseEntity<Projection> updateProjection(
      @PathVariable UUID id, @RequestBody Projection projection) {
    return ResponseEntity.status(200).body(projectionService.update(id, projection));
  }
}

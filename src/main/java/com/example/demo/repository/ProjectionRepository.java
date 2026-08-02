package com.example.demo.repository;

import com.example.demo.entity.Projection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectionRepository extends JpaRepository<Projection, UUID> {
    List<Projection> findByMovie_Id(UUID movieId);
}

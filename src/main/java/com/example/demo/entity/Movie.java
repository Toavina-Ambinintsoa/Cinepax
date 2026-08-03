package com.example.demo.entity;

import com.example.demo.entity.Enum.Gender;
import jakarta.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String description;
  private Duration duration;

  @OneToMany(mappedBy = "movie")
  private List<Projection> projecions = new ArrayList<>();
}

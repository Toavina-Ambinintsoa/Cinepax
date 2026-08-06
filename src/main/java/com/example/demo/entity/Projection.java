package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Projection {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Instant datetime;
  private BigDecimal price;

  @ManyToOne(optional = false)
  @JoinColumn(name = "movie_id", nullable = false)
  private Movie movie;

  @ManyToOne(optional = false)
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;

  @OneToMany(mappedBy = "projection")
  private Set<Reservation> reservations = new HashSet<>();
}

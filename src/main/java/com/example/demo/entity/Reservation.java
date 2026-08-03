package com.example.demo.entity;

import com.example.demo.entity.Enum.ReservationStatus;
import jakarta.persistence.*;
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
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Instant createdAt;
  private Boolean reservation;

  @Enumerated(EnumType.STRING)
  private ReservationStatus status;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  public User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "projection_id", nullable = false)
  private Projection projection;

  @ManyToMany
  @JoinTable(
      name = "reservation_seat",
      joinColumns = @JoinColumn(name = "reservation_id"),
      inverseJoinColumns = @JoinColumn(name = "seat_id"))
  private Set<Seat> seats = new HashSet<>();
}

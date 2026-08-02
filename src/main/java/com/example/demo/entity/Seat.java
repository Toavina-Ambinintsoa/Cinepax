package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seat {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String number;

  @ManyToOne(optional = false)
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;

  @ManyToMany(mappedBy = "seats")
  private List<Reservation> reservations = new ArrayList<>();
}

package com.example.demo.endpoint.rest.controller;

import com.example.demo.entity.Enum.ReservationStatus;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.service.ReservationService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservations")
public class ReservationController {
  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public ResponseEntity<List<Reservation>> findAll() {
    return ResponseEntity.status(200).body(reservationService.getAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Reservation> findById(
      @PathVariable UUID id, @AuthenticationPrincipal User currentUser) {
    return ResponseEntity.status(200).body(reservationService.getByIdForUser(id, currentUser));
  }

  @PutMapping("{id}")
  public ResponseEntity<Reservation> updateStatus(
      @PathVariable UUID id,
      @RequestBody ReservationStatus reservationStatus,
      @AuthenticationPrincipal User currentUser) {
    return ResponseEntity.status(200)
        .body(reservationService.updateStatus(id, reservationStatus, currentUser));
  }
}

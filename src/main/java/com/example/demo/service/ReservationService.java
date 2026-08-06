package com.example.demo.service;

import com.example.demo.entity.Enum.ReservationStatus;
import com.example.demo.entity.Enum.UserRole;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Seat;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ReservationRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  @Transactional(readOnly = true)
  public List<Reservation> getAll() {
    return reservationRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Reservation> getByUser(UUID userId) {
    return reservationRepository.findByUser_Id(userId);
  }

  @Transactional(readOnly = true)
  public List<Reservation> getByProjection(UUID projectionId) {
    return reservationRepository.findByProjection_Id(projectionId);
  }

  @Transactional(readOnly = true)
  public Reservation getById(UUID id) {
    return reservationRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Reservation not found: " + id));
  }

  @Transactional(readOnly = true)
  public Reservation getByIdForUser(UUID id, User currentUser) {
    Reservation reservation = getById(id);

    boolean isStaff =
        currentUser.getRole() == UserRole.MANAGER || currentUser.getRole() == UserRole.EMPLOYEE;

    if (!isStaff && !isOwnedBy(reservation, currentUser.getId())) {
      throw new AccessDeniedException("You don't own this reservation");
    }

    return reservation;
  }

  public boolean isOwnedBy(Reservation reservation, UUID userId) {
    return reservation.getUser().getId().equals(userId);
  }

  @Transactional
  public Reservation create(Reservation reservation) {
    checkSeatsAvailability(reservation);
    reservation.setCreatedAt(Instant.now());
    reservation.setStatus(ReservationStatus.PENDING);
    return reservationRepository.save(reservation);
  }

  @Transactional
  public Reservation updateStatus(UUID id, ReservationStatus newStatus, User currentUser) {
    Reservation reservation = getById(id);

    boolean isStaff =
        currentUser.getRole() == UserRole.MANAGER || currentUser.getRole() == UserRole.EMPLOYEE;

    if (newStatus == ReservationStatus.SUCCESS && !isStaff) {
      return null;
    }

    reservation.setStatus(newStatus);
    return reservationRepository.save(reservation);
  }

  @Transactional
  public void delete(UUID id) {
    Reservation reservation = getById(id);
    reservationRepository.delete(reservation);
  }

  private void checkSeatsAvailability(Reservation reservation) {
    List<Reservation> existing =
        reservationRepository.findByProjection_IdAndStatus(
            reservation.getProjection().getId(), ReservationStatus.SUCCESS);

    for (Seat seat : reservation.getSeats()) {
      boolean alreadyTaken = existing.stream().anyMatch(r -> r.getSeats().contains(seat));
      if (alreadyTaken) {
        throw new BadRequestException(
            "Seat " + seat.getNumber() + " is already booked for this projection");
      }
    }
  }
}

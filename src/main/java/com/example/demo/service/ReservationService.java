package com.example.demo.service;

import com.example.demo.entity.Reservation;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ReservationRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
  public Reservation getById(UUID id) {
    return reservationRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Reservation not found: " + id));
  }

  @Transactional
  public Reservation save(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  @Transactional
  public void delete(UUID id) {
    Reservation reservation = getById(id);
    reservationRepository.delete(reservation);
  }

  @Transactional
  public Reservation update(UUID id, Reservation reservation) {
    try {
      getById(id);
      return reservationRepository.save(reservation);
    } catch (Exception e) {
      throw new NotFoundException("Reservation not found: " + id);
    }
  }
}

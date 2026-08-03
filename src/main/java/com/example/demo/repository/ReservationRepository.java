package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
  List<Reservation> findByUser_Id(UUID userId);
}

package com.example.demo.service;

import com.example.demo.entity.Seat;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.SeatRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeatService {

  private final SeatRepository seatRepository;

  @Transactional(readOnly = true)
  public List<Seat> getByRoom(UUID roomId) {
    return seatRepository.findByRoom_Id(roomId);
  }

  @Transactional(readOnly = true)
  public Seat getById(UUID id) {
    return seatRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Seat not found: " + id));
  }

  @Transactional
  public Seat save(Seat seat) {
    return seatRepository.save(seat);
  }

  @Transactional
  public void delete(UUID id) {
    Seat seat = getById(id);
    seatRepository.delete(seat);
  }
}

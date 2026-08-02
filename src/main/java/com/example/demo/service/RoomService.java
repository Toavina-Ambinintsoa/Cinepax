package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.RoomRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;

  @Transactional(readOnly = true)
  public List<Room> getAll() {
    return roomRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Room getById(UUID id) {
    return roomRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Room not found: " + id));
  }

  @Transactional
  public Room save(Room room) {
    return roomRepository.save(room);
  }

  @Transactional
  public void delete(UUID id) {
    Room room = getById(id);
    roomRepository.delete(room);
  }

  @Transactional
  public Room update(UUID id, Room room) {
    try {
      getById(id);
      return roomRepository.save(room);
    } catch (Exception e) {
      throw new NotFoundException("Room not found: " + id);
    }
  }
}

package com.example.demo.exception;

/**
 * A mapper vers un 400 côté controller. Utilisée pour les règles métier (ex: siège déjà réservé,
 * salle pleine...).
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}

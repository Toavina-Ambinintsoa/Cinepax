package com.example.demo.exception;

/**
 * A mapper dans le controller (ou un @ControllerAdvice) vers un 404, une fois que tu attaqueras la
 * couche web / sécurité.
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
}

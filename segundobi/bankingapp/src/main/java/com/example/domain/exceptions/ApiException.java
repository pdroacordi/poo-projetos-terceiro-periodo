package com.example.domain.exceptions;

public class ApiException extends RuntimeException {
  public ApiException(String message) {
    super(message);
  }
}

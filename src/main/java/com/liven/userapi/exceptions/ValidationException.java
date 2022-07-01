package com.liven.userapi.exceptions;

public class ValidationException extends BaseException {
  public ValidationException(String message) {
    super(message, 400);
  }

}

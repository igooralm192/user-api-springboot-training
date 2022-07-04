package com.liven.userapi.exceptions;

import java.util.Map;

public class BaseException extends Exception {
  public String message;
  public int statusCode;
  public Map<String, String> errors;
  public String stack;

  public BaseException(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public BaseException(String message, int statusCode, Map<String, String> errors) {
    this(message, statusCode);
    this.errors = errors;
  }

  public BaseException(String message, int statusCode, Map<String, String> errors, String stack) {
    this(message, statusCode, errors);
    this.stack = stack;
  }
}

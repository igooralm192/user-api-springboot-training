package com.liven.userapi.exceptions;

public class BaseException extends Exception {
  public String message;
  public int statusCode;
  public String stack;

  public BaseException(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public BaseException(String message, int statusCode, String stack) {
    this(message, statusCode);
    this.stack = stack;
  }
}

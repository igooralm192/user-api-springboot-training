package com.liven.userapi.utils;

import java.util.Map;

import com.liven.userapi.exceptions.BaseException;

public class ApiResponse {
  public static Map<String, Object> getErrorResponse(BaseException exception) {
    return Map.of(
        "statusCode", exception.statusCode,
        "message", exception.message,
        "stack", exception.stack != null ? exception.stack : exception.toString());
  }
}

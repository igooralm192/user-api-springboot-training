package com.liven.userapi.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.liven.userapi.exceptions.BaseException;
import com.liven.userapi.utils.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    if (ex instanceof BaseException) {
      BaseException exception = (BaseException) ex;
      return ResponseEntity.status(status).body(ApiResponse.getErrorResponse(exception));
    } else {
      BaseException exception = new BaseException(
          ex.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(ex.getStackTrace()).toString());

      return ResponseEntity
          .status(HttpStatus.valueOf(exception.statusCode))
          .body(ApiResponse.getErrorResponse(exception));
    }
  }
}
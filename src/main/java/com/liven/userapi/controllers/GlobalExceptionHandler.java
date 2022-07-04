package com.liven.userapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.liven.userapi.exceptions.BaseException;
import com.liven.userapi.utils.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ BaseException.class })
  protected ResponseEntity<Object> handleBaseException(BaseException ex, WebRequest request) {
    return ResponseEntity.status(ex.statusCode).body(ApiResponse.getErrorResponse(ex));
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    if (ex instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;

      Map<String, String> errors = new HashMap<>();

      methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      });

      BaseException exception = new BaseException(
          "Validation error",
          HttpStatus.BAD_REQUEST.value(),
          errors);

      return ResponseEntity
          .status(HttpStatus.valueOf(exception.statusCode))
          .body(ApiResponse.getErrorResponse(exception));
    } else {
      BaseException exception = new BaseException(
          ex.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(ex.getStackTrace()).toString());

      return ResponseEntity
          .status(HttpStatus.valueOf(exception.statusCode))
          .body(ApiResponse.getErrorResponse(exception));
    }
  }
}
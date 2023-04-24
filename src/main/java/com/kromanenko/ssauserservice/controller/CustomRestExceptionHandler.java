package com.kromanenko.ssauserservice.controller;

import java.util.ArrayList;

import com.kromanenko.ssauserservice.dto.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatusCode status,
      final WebRequest request) {
    final var fieldErrors = new ArrayList<String>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      fieldErrors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    final var apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Request validation failed", fieldErrors);
    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
  }
}
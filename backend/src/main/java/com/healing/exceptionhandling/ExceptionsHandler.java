package com.healing.exceptionhandling;

import com.healing.exceptionhandling.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {AlreadyCastingException.class})
  protected ResponseEntity<Object> handleAlreadyCasting(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Already casting!";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
  }

  @ExceptionHandler(value = {GlobalCooldownException.class})
  protected ResponseEntity<Object> handleGlobalCooldown(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Global cooldown not ready yet!";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
  }

  @ExceptionHandler(value = {InsufficientManaException.class})
  protected ResponseEntity<Object> handleInsufficientMana(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Not enough mana!";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
  }

  @ExceptionHandler(value = {SpellOnCooldownException.class})
  protected ResponseEntity<Object> handleSpellOnCooldown(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Spell is not ready yet!";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
  }

  @ExceptionHandler(value = {TargetNotFoundException.class})
  protected ResponseEntity<Object> handleTargetNotFound(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Target not found!";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = {SpellNotFoundException.class})
  protected ResponseEntity<Object> handleSpellNotFound(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Spell not found!";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }
}

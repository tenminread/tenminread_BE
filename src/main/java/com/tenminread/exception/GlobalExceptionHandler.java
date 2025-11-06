package com.tenminread.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * ResourceNotFoundException이 발생하면 404 에러를 반환합니다.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {

    // 에러 메시지를 JSON 형태로 반환
    Map<String, String> errorBody = Map.of(
      "status", "404",
      "error", "Not Found",
      "message", ex.getMessage()
    );

    return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
  }
}

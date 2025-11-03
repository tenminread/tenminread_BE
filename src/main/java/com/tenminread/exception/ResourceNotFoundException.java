package com.tenminread.exception; // (패키지는 예시입니다)

// 예: @ResponseStatus(HttpStatus.NOT_FOUND) // (나중에 전역 예외 처리기에서 처리)
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}

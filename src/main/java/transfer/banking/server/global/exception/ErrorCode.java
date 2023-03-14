package transfer.banking.server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ErrorCode 를 정의하는 Enum Class 이다. ErrorCode 는 HttpStatus, ErrorCode, ErrorMessage 를 가진다.
 */
@Getter
public enum ErrorCode {

  // Common (공통적으로 사용)
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "값이 올바르지 않습니다."),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "지원하지 않는 Http Method 입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 에러"),
  INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "입력 값의 타입이 올바르지 않습니다."),
  HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근이 거부 되었습니다."),

  // Member (회원 관련)
  USERNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "M001", "이미 존재하는 아이디입니다."),
  EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 이메일입니다."),
  PHONE_DUPLICATION(HttpStatus.BAD_REQUEST, "M003", "이미 존재하는 전화번호입니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  public int getStatus() {
    return this.status.value();
  }

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}

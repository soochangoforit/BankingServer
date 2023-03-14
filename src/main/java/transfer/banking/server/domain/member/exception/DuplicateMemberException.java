package transfer.banking.server.domain.member.exception;

import transfer.banking.server.global.exception.BusinessException;
import transfer.banking.server.global.exception.ErrorCode;

/**
 * 회원가입 시 중복된 필드가 있을 때 발생하는 예외.
 */
public class DuplicateMemberException extends BusinessException {

  public DuplicateMemberException(ErrorCode errorCode) {
    super(errorCode);
  }

}

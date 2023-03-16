package transfer.banking.server.domain.member.exception;

import transfer.banking.server.global.exception.BusinessException;
import transfer.banking.server.global.exception.ErrorCode;

/**
 * 멤버를 찾을 수 없을 때 발생하는 예외
 */
public class MemberNotFoundException extends BusinessException {

  public MemberNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}

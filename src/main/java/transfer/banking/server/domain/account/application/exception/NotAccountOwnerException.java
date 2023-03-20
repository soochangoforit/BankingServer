package transfer.banking.server.domain.account.application.exception;

import transfer.banking.server.global.exception.BusinessException;
import transfer.banking.server.global.exception.ErrorCode;

/**
 * 계좌 소유자가 아닌 경우 발생하는 예외
 */
public class NotAccountOwnerException extends BusinessException {

  public NotAccountOwnerException(ErrorCode errorCode) {
    super(errorCode);
  }
}

package transfer.banking.server.domain.account.exception;

import transfer.banking.server.global.exception.BusinessException;
import transfer.banking.server.global.exception.ErrorCode;

/**
 * 계좌를 찾을 수 없을 때 발생하는 예외
 */
public class NotFoundAccountException extends BusinessException {

  public NotFoundAccountException(ErrorCode errorCode) {
    super(errorCode);
  }
}

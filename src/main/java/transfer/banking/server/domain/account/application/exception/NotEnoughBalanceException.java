package transfer.banking.server.domain.account.application.exception;

import transfer.banking.server.global.exception.BusinessException;
import transfer.banking.server.global.exception.ErrorCode;

/**
 * 잔액 부족 예외
 */
public class NotEnoughBalanceException extends BusinessException {

  public NotEnoughBalanceException(ErrorCode errorCode) {
    super(errorCode);
  }
}

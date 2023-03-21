package transfer.banking.server.domain.account.application.exception;

import transfer.banking.server.global.exception.BusinessException;
import transfer.banking.server.global.exception.ErrorCode;

/**
 * 친구 계좌로만 이체 가능합니다.
 */
public class CanTransferWithOnlyFriend extends BusinessException {

  public CanTransferWithOnlyFriend(ErrorCode errorCode) {
    super(errorCode);
  }
}

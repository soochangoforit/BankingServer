package transfer.banking.server.domain.friendship.application.exception;

import transfer.banking.server.global.exception.BusinessException;
import transfer.banking.server.global.exception.ErrorCode;

/**
 * 이미 친구인 경우 예외
 */
public class AlreadyFriendException extends BusinessException {

  public AlreadyFriendException(ErrorCode errorCode) {
    super(errorCode);
  }
}

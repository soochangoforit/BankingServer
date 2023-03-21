package transfer.banking.server.domain.account.application.port.in;

import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountTransferDtoCommand;

/**
 * 계좌 이체 유스케이스
 */
public interface AccountTransferUseCase {

  void transfer(AccountTransferDtoCommand command);
}

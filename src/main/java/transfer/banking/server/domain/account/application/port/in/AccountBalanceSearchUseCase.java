package transfer.banking.server.domain.account.application.port.in;

import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountSearchDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountSearchDtoResCommand;

/**
 * 계좌 잔액 조회 유스케이스
 */
public interface AccountBalanceSearchUseCase {

  AccountSearchDtoResCommand searchAccountBalance(AccountSearchDtoCommand command);
}

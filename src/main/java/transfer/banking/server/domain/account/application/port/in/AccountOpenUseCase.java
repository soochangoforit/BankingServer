package transfer.banking.server.domain.account.application.port.in;

import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDtoCommand;

/**
 * 계좌 개설 유스케이스
 * 입력 값으로 Command 객체를 받고, 결과 값으로 Command 객체를 반환한다.
 */
public interface AccountOpenUseCase {

  AccountOpenedDtoCommand openAccount(AccountOpenDtoCommand accountOpenDtoCommand);

}

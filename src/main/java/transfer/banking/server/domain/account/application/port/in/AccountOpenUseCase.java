package transfer.banking.server.domain.account.application.port.in;

import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDtoCommand;

public interface AccountOpenUseCase {

  AccountOpenedDtoCommand openAccount(AccountOpenDtoCommand accountOpenDtoCommand);

}

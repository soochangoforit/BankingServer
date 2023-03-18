package transfer.banking.server.domain.account.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDtoCommand;
import transfer.banking.server.domain.account.application.port.in.AccountOpenUseCase;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.member.domain.MemberDomain;

@Service
@RequiredArgsConstructor
public class AccountOpenService implements AccountOpenUseCase {

  private final MemberService memberService;
  private final AccountNumberGenerator accountNumberGenerator;
  private final AccountOpenTxService accountOpenTxService;

  @Override
  public AccountOpenedDtoCommand openAccount(AccountOpenDtoCommand accountOpenDtoCommand) {
    MemberDomain memberDomain = memberService.findMemberById(accountOpenDtoCommand.getMemberId());
    String accountNumber = accountNumberGenerator.generateAccountNumber();
    AccountDomain accountDomain = accountOpenDtoCommand.toAccountDomain(accountNumber);
    AccountDomain domain = accountOpenTxService.saveAccount(accountDomain, memberDomain);
    return new AccountOpenedDtoCommand(domain, memberDomain);
  }
}

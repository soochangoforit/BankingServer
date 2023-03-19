package transfer.banking.server.domain.account.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDtoCommand;
import transfer.banking.server.domain.account.application.port.in.AccountOpenUseCase;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 계좌 개설 복합 서비스
 * 하위 순수 서비스와 Port Interface 를 통해서 협력하는 것도 좋지만
 * 현 프로젝트에서는 비니지스 로직을 수행하기 위해서 외부 서비스를 호출하는 경우가 없기 때문에
 * 하위 서비스를 직접 호출하는 방식으로 구현한다.
 */
@Service
@RequiredArgsConstructor
public class AccountOpenService implements AccountOpenUseCase {

  private final MemberService memberService;
  private final AccountNumberGenerator accountNumberGenerator;
  private final AccountOpenTxService accountOpenTxService;

  /**
   * 계좌 개설
   *
   * @param accountOpenDtoCommand 계좌 개설 정보를 담은 Command 객체
   * @return 계좌 개설 결과를 담은 Command 객체
   */
  @Override
  public AccountOpenedDtoCommand openAccount(AccountOpenDtoCommand accountOpenDtoCommand) {
    MemberDomain memberDomain = memberService.findMemberById(accountOpenDtoCommand.getMemberId());
    String accountNumber = accountNumberGenerator.generateAccountNumber();
    AccountDomain accountDomain = accountOpenDtoCommand.toAccountDomain(accountNumber);
    AccountDomain savedAccountDomain = accountOpenTxService.saveAccount(accountDomain, memberDomain);
    return new AccountOpenedDtoCommand(savedAccountDomain, memberDomain);
  }
}

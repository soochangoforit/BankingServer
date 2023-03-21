package transfer.banking.server.domain.account.application.service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountSearchDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountSearchDtoResCommand;
import transfer.banking.server.domain.account.application.port.in.AccountBalanceSearchUseCase;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

/**
 * 계좌 잔액 조회 복합 서비스
 */
@Service
@RequiredArgsConstructor
public class AccountBalanceSearchService implements AccountBalanceSearchUseCase {

  private final AccountService accountService;
  private final MemberAccountService memberAccountService;

  /**
   * 계좌 잔액 조회
   *
   * @param command 계좌 잔액을 조회할 계좌 정보
   * @return 계좌 잔액 데이터를 담은 DTO
   */
  @Override
  public AccountSearchDtoResCommand searchAccountBalance(AccountSearchDtoCommand command) {
    // bank 랑 number 를 통해서 account id 조회
    AccountDomain accountDomain = accountService.findAccountByBankAndNumber(
        command.getAccountBank(), command.getAccountNumber());

    // account id 와 member id를 통해서 주인 확인
    memberAccountService.checkIfMemberOwnsAccount(command.getMemberId(), accountDomain.getId());

    // account id 를 통해서 잔액 조회
    BigDecimal balance = accountDomain.getBalance();
    return new AccountSearchDtoResCommand(accountDomain, balance);
  }
}

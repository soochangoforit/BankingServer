package transfer.banking.server.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.dto.request.AccountOpenDto;
import transfer.banking.server.domain.account.dto.response.AccountOpenedDto;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.application.service.MemberService;


/**
 * 계좌 개설 복합 서비스
 */
@Service
@RequiredArgsConstructor
public class AccountOpenService {

  private final MemberService memberService;
  private final AccountNumberGenerator accountNumberGenerator;
  private final AccountOpenTxService accountOpenTxService;

  /**
   * 계좌 개설
   *
   * @param accountOpenDto 계좌 개설 요청 DTO
   * @return 계좌 개설 응답 DTO
   */
  public AccountOpenedDto openAccount(AccountOpenDto accountOpenDto) {
    Member member = memberService.findMemberById(accountOpenDto.getMemberId());
    String accountNumber = accountNumberGenerator.generateAccountNumber();
    Account account = accountOpenDto.toEntity(accountNumber);
    Account savedAccount = accountOpenTxService.saveAccount(member, account);
    return new AccountOpenedDto(savedAccount, member);
  }
}

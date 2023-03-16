package transfer.banking.server.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

/**
 * 계좌 개설 (트랜잭션 서비스)
 */
@Service
@RequiredArgsConstructor
public class AccountOpenTxService {

  private final MemberAccountService memberAccountService;
  private final AccountService accountService;

  /**
   * 계좌와 멤버 계좌를 저장한다. 하나의 트랜잭션으로 처리한다.
   *
   * @param member  멤버
   * @param account 개설된 계좌
   * @return 저장된 계좌
   */
  @Transactional
  public Account saveAccount(Member member, Account account) {
    Account accountEntity = accountService.openAccount(account);
    memberAccountService.saveMemberAccount(member, account);
    return accountEntity;
  }
}

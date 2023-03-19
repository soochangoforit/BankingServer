package transfer.banking.server.domain.account.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

/**
 * 계좌 생성 트랜잭션 (중간 복합 서비스)
 * 입력 값으로 Domain 객체를 주입 받거나, Primitive Type 을 사용한다.
 * 응답 값으로 Domain 객체를 사용 하거나, Primitive Type 을 사용한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountOpenTxService {

  private final MemberAccountService memberAccountService;
  private final AccountService accountService;

  /**
   * 계좌 생성 트랜잭션
   *
   * @param accountDomain 계좌 도메인
   * @param memberDomain 계좌를 개설하고자 하는 회원 도메인
   * @return 개설된 계좌 도메인
   */
  @Transactional
  public AccountDomain saveAccount(AccountDomain accountDomain, MemberDomain memberDomain) {
    AccountDomain savedAccountDomain = accountService.openAccount(accountDomain);
    memberAccountService.saveMemberAccount(memberDomain, savedAccountDomain);
    return savedAccountDomain;
  }


}

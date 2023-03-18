package transfer.banking.server.domain.account.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountOpenTxService {

  private final MemberAccountService memberAccountService;
  private final AccountService accountService;

  @Transactional
  public AccountDomain saveAccount(AccountDomain accountDomain, MemberDomain memberDomain) {
    AccountDomain domain = accountService.openAccount(accountDomain);
    memberAccountService.saveMemberAccount(memberDomain, domain);
    return domain;
  }


}

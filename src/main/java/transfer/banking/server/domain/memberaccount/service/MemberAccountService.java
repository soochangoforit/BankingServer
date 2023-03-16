package transfer.banking.server.domain.memberaccount.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;
import transfer.banking.server.domain.memberaccount.repository.MemberAccountRepository;

/**
 * 멤버 계좌 순수 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberAccountService {

  private final MemberAccountRepository memberAccountRepository;

  @Transactional
  public void saveMemberAccount(Member member, Account account) {
    log.info("멤버 계좌를 저장합니다. memberName: {}, accountNumber: {}", member.getName(), account.getAccountNumber());
    MemberAccount memberAccount = new MemberAccount(member, account);
    memberAccountRepository.save(memberAccount);
  }
}

package transfer.banking.server.domain.memberaccount.service;

import static transfer.banking.server.global.exception.ErrorCode.FRIEND_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.account.entity.Bank;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.member.exception.MemberNotFoundException;
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
    log.info("멤버 계좌를 저장합니다. memberName: {}, accountNumber: {}", member.getName(),
        account.getAccountNumber());
    MemberAccount memberAccount = new MemberAccount(member, account);
    memberAccountRepository.save(memberAccount);
  }

  /**
   * 친구 이름과 계좌번호를 통해 친구 계좌를 조회
   *
   * @param friendAccountBank 친구 계좌 은행
   * @param friendAccountNumber 친구 계좌번호
   * @return 친구
   */
  @Transactional(readOnly = true)
  public Member findFriendByBankAndAccountNumber(Bank friendAccountBank, String friendAccountNumber) {
    log.info("친구 이름과 계좌번호를 통해 친구를 조회합니다. friendAccountBank: {}, friendAccountNumber: {}", friendAccountBank,
        friendAccountNumber);
    return memberAccountRepository.findFriendByBankAndAccountNumber(friendAccountBank, friendAccountNumber)
        .orElseThrow(() -> new MemberNotFoundException(FRIEND_NOT_FOUND));
  }
}

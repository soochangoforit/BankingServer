package transfer.banking.server.domain.memberaccount.service;

import static transfer.banking.server.global.exception.ErrorCode.FRIEND_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.entity.Account;
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
   * @param friendName 친구 이름
   * @param friendAccountNumber 친구 계좌번호
   * @return 친구
   */
  @Transactional(readOnly = true)
  public Member findFriendByNameAndAccountNumber(String friendName, String friendAccountNumber) {
    log.info("친구 이름과 계좌번호를 통해 친구를 조회합니다. friendName: {}, friendAccountNumber: {}", friendName,
        friendAccountNumber);
    return memberAccountRepository.findFriendByNameAndAccountNumber(friendName, friendAccountNumber)
        .orElseThrow(() -> new MemberNotFoundException(FRIEND_NOT_FOUND));
  }
}

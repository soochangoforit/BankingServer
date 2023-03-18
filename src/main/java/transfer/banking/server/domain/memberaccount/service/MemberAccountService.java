package transfer.banking.server.domain.memberaccount.service;

import static transfer.banking.server.global.exception.ErrorCode.FRIEND_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;
import transfer.banking.server.domain.memberaccount.mapper.MemberAccountMapper;
import transfer.banking.server.domain.memberaccount.repository.MemberAccountRepository;

/**
 * 멤버 계좌 순수 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberAccountService {

  private final MemberAccountRepository memberAccountRepository;
  private final MemberAccountMapper memberAccountMapper;

  @Transactional
  public void saveMemberAccount(MemberDomain memberDomain, AccountDomain accountDomain) {
    log.info("멤버 계좌를 저장합니다. memberName: {}, accountNumber: {}", memberDomain.getName(),
        accountDomain.getAccountNumber());
    MemberAccount memberAccount = memberAccountMapper.toEntity(memberDomain, accountDomain);
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

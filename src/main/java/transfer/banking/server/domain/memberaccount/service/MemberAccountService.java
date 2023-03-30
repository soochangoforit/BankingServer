package transfer.banking.server.domain.memberaccount.service;

import static transfer.banking.server.global.exception.ErrorCode.FRIEND_NOT_FOUND;
import static transfer.banking.server.global.exception.ErrorCode.NOT_ACCOUNT_OWNER;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.exception.NotAccountOwnerException;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.port.out.MemberAccountRepositoryPort;

/**
 * 멤버 계좌 순수 서비스
 * 입력 값으로 Domain 객체 혹은 Primitive Type 을 받아서 처리한다.
 * 응답 값으로 Domain 객체 혹은 Primitive Type 을 반환한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberAccountService {

  private final MemberAccountRepositoryPort memberAccountRepository;


  /**
   * 멤버 계좌를 저장
   *
   * @param memberDomain 계좌를 개설하고자 하는 멤버 도메인
   * @param accountDomain 개설된 계좌 도메인
   */
  @Transactional
  public void saveMemberAccount(MemberDomain memberDomain, AccountDomain accountDomain) {
    memberAccountRepository.save(memberDomain, accountDomain);
  }

  /**
   * 친구 MemberAccount 찾기 위해 친구 계좌번호와 은행을 통해 친구 계좌를 조회
   *
   * @param friendAccountNumber 추가하고자 하는 친구 계좌번호
   * @param friendAccountBank 추가하고자 하는 친구 계좌 은행
   * @return 친구 계좌 도메인
   */
  @Transactional(readOnly = true)
  public MemberAccountDomain findFriendAccountByNumAndBank(String friendAccountNumber,
      Bank friendAccountBank) {
    return memberAccountRepository.findFriendAccountByNumAndBank(friendAccountNumber, friendAccountBank)
        .orElseThrow(() -> new MemberNotFoundException(FRIEND_NOT_FOUND));
  }


  /**
   * 멤버가 해당 계좌를 소유하고 있는지 확인
   *
   * @param memberId 잔액을 조회하고자 하는 멤버 아이디
   * @param accountId 잔액을 조회하고자 하는 계좌 아이디
   */
  @Transactional(readOnly = true)
  public void checkIfMemberOwnsAccount(Long memberId, Long accountId) {
    if(!memberAccountRepository.checkIfMemberOwnsAccount(memberId, accountId)){
      throw new NotAccountOwnerException(NOT_ACCOUNT_OWNER);
    }
  }
}

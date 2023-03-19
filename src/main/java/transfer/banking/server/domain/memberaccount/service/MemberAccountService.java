package transfer.banking.server.domain.memberaccount.service;

import static transfer.banking.server.global.exception.ErrorCode.FRIEND_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.port.out.MemberAccountRepositoryPort;

/**
 * 멤버 계좌 순수 서비스
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
  public void saveMemberAccount(MemberDomain memberDomain, AccountDomain accountDomain) {
    log.info("멤버 계좌를 저장합니다. memberName: {}, accountNumber: {}", memberDomain.getName(),
        accountDomain.getAccountNumber());
    memberAccountRepository.save(memberDomain, accountDomain);
  }

  public MemberAccountDomain findFriendAccountByNumAndBank(String friendAccountNumber,
      Bank friendAccountBank) {
    return memberAccountRepository.findFriendAccountByNumAndBank(friendAccountNumber, friendAccountBank)
        .orElseThrow(() -> new MemberNotFoundException(FRIEND_NOT_FOUND));
  }

  public List<MemberAccountDomain> searchFriendsAccount(List<String> myFriendsAccountNumbers) {
    return memberAccountRepository.searchFriendsAccount(myFriendsAccountNumbers);
  }
//
//  /**
//   * 친구 이름과 계좌번호를 통해 친구 계좌를 조회
//   *
//   * @param friendAccountBank 친구 계좌 은행
//   * @param friendAccountNumber 친구 계좌번호
//   * @return 친구
//   */
//  @Transactional(readOnly = true)
//  public Member findFriendByBankAndAccountNumber(Bank friendAccountBank, String friendAccountNumber) {
//    log.info("친구 이름과 계좌번호를 통해 친구를 조회합니다. friendAccountBank: {}, friendAccountNumber: {}", friendAccountBank,
//        friendAccountNumber);
//    return memberAccountRepository.findFriendByBankAndAccountNumber(friendAccountBank, friendAccountNumber)
//        .orElseThrow(() -> new MemberNotFoundException(FRIEND_NOT_FOUND));
//  }
}

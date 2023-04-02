package transfer.banking.server.domain.memberaccount.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.exception.NotAccountOwnerException;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.port.out.MemberAccountRepositoryPort;

@ExtendWith(MockitoExtension.class)
class MemberAccountServiceTest {

  @Mock
  private MemberAccountRepositoryPort memberAccountRepository;

  @InjectMocks
  private MemberAccountService memberAccountService;

  private final String FRIEND_ACCOUNT_NUMBER = "123456789";
  private final Bank FRIEND_ACCOUNT_BANK = Bank.NH;

  @Test
  @DisplayName("멤버 계좌 정보를 저장한다.. (정상적인 경우)")
  void saveMemberAccount() {
    // given
    MemberDomain memberDomain = mock(MemberDomain.class);
    AccountDomain accountDomain = mock(AccountDomain.class);

    // when
    memberAccountService.saveMemberAccount(memberDomain, accountDomain);

    // then
    verify(memberAccountRepository, times(1)).save(memberDomain, accountDomain);
  }

  @Test
  @DisplayName("친구 MemberAccount 정보를 조회한다. (정상적인 경우)")
  void findFriendAccountByNumAndBank() {
    // given
    MemberAccountDomain expected = mock(MemberAccountDomain.class);
    when(memberAccountRepository.findFriendAccountByNumAndBank(FRIEND_ACCOUNT_NUMBER, FRIEND_ACCOUNT_BANK))
        .thenReturn(Optional.of(expected));

    // when
    MemberAccountDomain actual = memberAccountService.findFriendAccountByNumAndBank(FRIEND_ACCOUNT_NUMBER, FRIEND_ACCOUNT_BANK);

    // then
    assertSame(expected, actual);
    verify(memberAccountRepository, times(1)).findFriendAccountByNumAndBank(FRIEND_ACCOUNT_NUMBER, FRIEND_ACCOUNT_BANK);
  }

  @Test
  @DisplayName("친구 MemberAccount 정보를 조회한다. (실패한 경우)")
  void findFriendAccountByNumAndBank_ThrowException() {
    // given
    when(memberAccountRepository.findFriendAccountByNumAndBank(FRIEND_ACCOUNT_NUMBER, FRIEND_ACCOUNT_BANK))
        .thenReturn(Optional.empty());

    // when, then
    assertThrows(MemberNotFoundException.class,
        () -> memberAccountService.findFriendAccountByNumAndBank(FRIEND_ACCOUNT_NUMBER, FRIEND_ACCOUNT_BANK));
    verify(memberAccountRepository, times(1)).findFriendAccountByNumAndBank(FRIEND_ACCOUNT_NUMBER, FRIEND_ACCOUNT_BANK);
  }

  @Test
  @DisplayName("멤버가 소유한 계좌인지 확인한다. (정상적인 경우)")
  void checkIfMemberOwnsAccount() {
    // given
    Long memberId = 1L;
    Long accountId = 2L;
    when(memberAccountRepository.checkIfMemberOwnsAccount(memberId, accountId)).thenReturn(true);

    // when, then
    assertDoesNotThrow(() -> memberAccountService.checkIfMemberOwnsAccount(memberId, accountId));
    verify(memberAccountRepository, times(1)).checkIfMemberOwnsAccount(memberId, accountId);
  }

  @Test
  @DisplayName("멤버가 소유한 계좌인지 확인한다. (실패한 경우)")
  void checkIfMemberOwnsAccount_ThrowException() {
    // given
    Long memberId = 1L;
    Long accountId = 2L;
    when(memberAccountRepository.checkIfMemberOwnsAccount(memberId, accountId)).thenReturn(false);

    // when, then
    assertThrows(NotAccountOwnerException.class, () -> memberAccountService.checkIfMemberOwnsAccount(memberId, accountId));
    verify(memberAccountRepository, times(1)).checkIfMemberOwnsAccount(memberId, accountId);
  }
}
package transfer.banking.server.domain.memberaccount.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.account.entity.Bank;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;
import transfer.banking.server.domain.memberaccount.repository.MemberAccountRepository;

@ExtendWith(MockitoExtension.class)
class MemberAccountServiceTest {

  @Mock
  private MemberAccountRepository memberAccountRepository;

  @InjectMocks
  private MemberAccountService memberAccountService;

  @Test
  @DisplayName("회원 계좌 저장 성공")
  public void testSaveMemberAccount() {
    Member member = new Member("username", "password", "email", "name", "phone");
    Account account = new Account("accountNumber", "accountName", Bank.NH, BigDecimal.ZERO);

    memberAccountService.saveMemberAccount(member, account);

    verify(memberAccountRepository, times(1)).save(any(MemberAccount.class));
  }

  @Test
  @DisplayName("은행과 계좌번호를 통해 친구를 조회한다. - 성공")
  void findFriendByBankAndAccountNumber() {

    Member expectedFriend = new Member("username", "password", "email", "name", "phone");
    when(memberAccountRepository.findFriendByBankAndAccountNumber(any(Bank.class), any(String.class)))
        .thenReturn(Optional.of(expectedFriend));

    Member actualFriend = memberAccountService.findFriendByBankAndAccountNumber(Bank.NH,
        "accountNumber");

    verify(memberAccountRepository, times(1)).findFriendByBankAndAccountNumber(any(Bank.class), any(String.class));

    assertThat(actualFriend).isNotNull();
    assertEquals(expectedFriend, actualFriend);
  }

  @Test
  @DisplayName("은행과 계좌번호를 통해 친구를 조회한다. - 실패")
  void findFriendByBankAndAccountNumberFail() {

    when(memberAccountRepository.findFriendByBankAndAccountNumber(any(Bank.class), any(String.class)))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(MemberNotFoundException.class, () -> {
      memberAccountService.findFriendByBankAndAccountNumber(Bank.NH, "accountNumber");
    });

    verify(memberAccountRepository, times(1)).findFriendByBankAndAccountNumber(any(Bank.class), any(String.class));
  }

}
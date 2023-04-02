package transfer.banking.server.domain.account.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

@ExtendWith(MockitoExtension.class)
class AccountOpenTxServiceTest {

  @Mock
  private MemberAccountService memberAccountService;

  @Mock
  private AccountService accountService;

  @InjectMocks
  private AccountOpenTxService accountOpenTxService;

  @Test
  @DisplayName("계좌를 개설하고 멤버 계좌 정보를 저장한다. (정상적인 경우)")
  void saveAccount() {
    // Given
    MemberDomain inputMemberDomain = mock(MemberDomain.class);
    AccountDomain inputAccountDomain = mock(AccountDomain.class);
    AccountDomain savedAccountDomain = mock(AccountDomain.class);
    when(accountService.openAccount(inputAccountDomain)).thenReturn(savedAccountDomain);

    // When
    AccountDomain expectedAccountDomain = accountOpenTxService.saveAccount(inputAccountDomain, inputMemberDomain);

    // Then
    verify(accountService, times(1)).openAccount(inputAccountDomain);
    verify(memberAccountService, times(1)).saveMemberAccount(inputMemberDomain, savedAccountDomain);
    assertEquals(expectedAccountDomain, savedAccountDomain);
  }
}
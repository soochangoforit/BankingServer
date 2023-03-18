package transfer.banking.server.domain.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
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
  @DisplayName("계좌 개설 성공")
  void saveAccount() {
    // given
    Member member = new Member();
    Account account = new Account();

    Account accountEntity = new Account();
    when(accountService.openAccount(account)).thenReturn(accountEntity);

    // when
    Account savedAccount = accountOpenTxService.saveAccount(member, account);

    // then
    verify(accountService).openAccount(account);
    verify(memberAccountService).saveMemberAccount(member, account);
    assertThat(savedAccount).isEqualTo(accountEntity);
  }
}
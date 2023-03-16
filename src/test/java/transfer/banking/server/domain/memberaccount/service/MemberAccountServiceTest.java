package transfer.banking.server.domain.memberaccount.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.account.entity.Bank;
import transfer.banking.server.domain.member.entity.Member;
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
}
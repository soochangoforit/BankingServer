package transfer.banking.server.domain.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.dto.request.AccountOpenDto;
import transfer.banking.server.domain.account.dto.response.AccountOpenedDto;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.account.entity.Bank;
import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.application.service.MemberService;

@ExtendWith(MockitoExtension.class)
class AccountOpenServiceTest {

  @Mock
  private MemberService memberService;

  @Mock
  private AccountNumberGenerator accountNumberGenerator;

  @Mock
  private AccountOpenTxService accountOpenTxService;

  @InjectMocks
  private AccountOpenService accountOpenService;


  @Test
  @DisplayName("계좌 개설 성공")
  void openAccount() {
    // Create a test Member
    Member member = new Member("username", "password", "email", "name", "phone");
    AccountOpenDto accountOpenDto = new AccountOpenDto(1L, Bank.NH, "accountName");
    String accountNumber = "1234567890";

    // Set up the mocks for the AccountNumberGenerator and AccountOpenTxService
    when(memberService.findMemberById(anyLong())).thenReturn(member);
    when(accountNumberGenerator.generateAccountNumber()).thenReturn(accountNumber);

    Account account = new Account(accountNumber, accountOpenDto.getAccountName(), accountOpenDto.getBank(), BigDecimal.ZERO);
    when(accountOpenTxService.saveAccount(eq(member), any(Account.class))).thenReturn(account);

    // Call the method being tested
    AccountOpenedDto result = accountOpenService.openAccount(accountOpenDto);

    // Verify that the memberService was called to find the member by ID
    verify(memberService, times(1)).findMemberById(anyLong());

    // Verify that the accountNumberGenerator was called to generate an account number
    verify(accountNumberGenerator, times(1)).generateAccountNumber();

    // Verify that the accountOpenTxService was called to save the account
    verify(accountOpenTxService, times(1)).saveAccount(eq(member), any(Account.class));

    // Verify that the returned AccountOpenedDto object contains the expected values
    assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
    assertThat(result.getAccountName()).isEqualTo(accountOpenDto.getAccountName());
    assertThat(result.getBank()).isEqualTo(accountOpenDto.getBank().toString());
    assertThat(result.getBalance()).isEqualTo(BigDecimal.ZERO);
    assertThat(result.getMemberName()).isEqualTo(member.getName());
  }
}
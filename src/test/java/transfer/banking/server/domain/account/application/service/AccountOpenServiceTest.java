package transfer.banking.server.domain.account.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountOpenDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountOpenedDtoCommand;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.member.domain.MemberDomain;

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
  @DisplayName("계좌를 개설한다. (정상적인 경우)")
  void openAccount() {
    // Given
    Long memberId = 1L;
    Bank bank = Bank.NH;
    String accountName = "accountName";
    AccountOpenDtoCommand accountOpenDtoCommand = new AccountOpenDtoCommand(memberId, bank,
        accountName);

    String generatedAccountNumber = "123456789";

    MemberDomain findedMemberDomain = MemberDomain.builder()
        .id(memberId)
        .email("email")
        .name("name")
        .username("username")
        .password("password")
        .phoneNumber("phoneNumber").build();


    AccountDomain savedAccountDomain = AccountDomain.builder()
        .id(1L)
        .accountNumber(generatedAccountNumber)
        .accountName(accountName)
        .balance(BigDecimal.ZERO)
        .bank(bank)
        .build();


    when(memberService.findMemberById(memberId)).thenReturn(findedMemberDomain);
    when(accountNumberGenerator.generateAccountNumber()).thenReturn(generatedAccountNumber);
    when(accountOpenTxService.saveAccount(any(), eq(findedMemberDomain))).thenReturn(
        savedAccountDomain);

    // When
    AccountOpenedDtoCommand result = accountOpenService.openAccount(accountOpenDtoCommand);

    // Then
    assertThat(result.getAccountNumber()).isEqualTo(generatedAccountNumber);
    assertThat(result.getMemberId()).isEqualTo(memberId);
    verify(memberService, times(1)).findMemberById(memberId);
    verify(accountNumberGenerator, times(1)).generateAccountNumber();
    verify(accountOpenTxService, times(1)).saveAccount(any(), eq(findedMemberDomain));
  }
}
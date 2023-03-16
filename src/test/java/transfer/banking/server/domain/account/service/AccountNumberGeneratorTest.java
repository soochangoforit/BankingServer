package transfer.banking.server.domain.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AccountNumberGeneratorTest {

  @Mock
  private AccountService accountService;

  @InjectMocks
  private AccountNumberGenerator accountNumberGenerator;

  @Test
  @DisplayName("계좌번호 생성 성공_생성된 계좌 번호가 중복 안될경우")
  void testGenerateAccountNumber() {
    when(accountService.checkIfAccountNumberExists(anyString())).thenReturn(false);
    String accountNumber = accountNumberGenerator.generateAccountNumber();

    verify(accountService, times(1)).checkIfAccountNumberExists(anyString());

    assertNotNull(accountNumber);
    assertEquals(9, accountNumber.length());
  }

  @Test
  @DisplayName("계좌번호 생성 성공_생성된 계좌 번호가 중복 될경우")
  void testGenerateAccountNumber_Duplicate() {
    when(accountService.checkIfAccountNumberExists(anyString())).thenReturn(true, false);
    String accountNumber = accountNumberGenerator.generateAccountNumber();

    verify(accountService, times(2)).checkIfAccountNumberExists(anyString());

    assertNotNull(accountNumber);
    assertEquals(9, accountNumber.length());
  }



}
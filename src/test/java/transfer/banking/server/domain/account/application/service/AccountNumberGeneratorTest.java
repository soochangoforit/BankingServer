package transfer.banking.server.domain.account.application.service;

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
  @DisplayName("계좌번호를 생성한다. (성공)")
  void generateAccountNumber() {
    // given
    when(accountService.checkIfAccountNumberExists(anyString())).thenReturn(false);

    // when
    String accountNumber = accountNumberGenerator.generateAccountNumber();

    // then
    assertNotNull(accountNumber);
    assertTrue(accountNumber.matches("\\d{9}")); // Check if the generated number is 9 digits long
    verify(accountService, times(1)).checkIfAccountNumberExists(accountNumber);
  }

  @Test
  @DisplayName("계좌번호를 생성한다. 2번은 실패, 1번은 성공")
  void testGenerateAccountNumber_whenAccountNumberExists() {
    // given
    when(accountService.checkIfAccountNumberExists(anyString())).thenReturn(true, true, false);

    // when
    String accountNumber = accountNumberGenerator.generateAccountNumber();

    // then
    assertNotNull(accountNumber);
    assertTrue(accountNumber.matches("\\d{9}"));
    // 전체적으로는 특정 문자열에 대해서 3번 호출
    verify(accountService, times(3)).checkIfAccountNumberExists(anyString());
    // account Number 를 통해 exist 확인한 횟수는 1번이다.
    verify(accountService, times(1)).checkIfAccountNumberExists(accountNumber);
  }

}
package transfer.banking.server.domain.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.account.entity.Bank;
import transfer.banking.server.domain.account.exception.NotFoundAccountException;
import transfer.banking.server.domain.account.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountService accountService;

  @Test
  @DisplayName("계좌 개설 성공")
  void openAccount_Success() {
    // Arrange
    Account expectedAccount = new Account("1234567890", "수찬계좌", Bank.NH, BigDecimal.ZERO);

    when(accountRepository.save(expectedAccount)).thenReturn(expectedAccount);

    // Act
    Account actualAccount = accountService.openAccount(expectedAccount);

    // Assert
    assertEquals(expectedAccount, actualAccount);
    verify(accountRepository, times(1)).save(expectedAccount);
  }

  @Test
  @DisplayName("계좌 조회 성공")
  void findAccountByAccountNumber_Success() {
    // Arrange
    String accountNumber = "1234567890";
    Account expectedAccount = new Account(accountNumber, "수찬계좌", Bank.NH, BigDecimal.ZERO);

    when(accountRepository.findByAccountNumber(accountNumber))
        .thenReturn(Optional.of(expectedAccount));

    // Act
    Account actualAccount = accountService.findAccountByAccountNumber(accountNumber);

    // Assert
    assertEquals(expectedAccount, actualAccount);
    verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
  }


  @Test
  @DisplayName("계좌 조회 실패 - 계좌번호가 존재하지 않음")
  void findAccountByAccountNumber_NotFoundAccountException() {
    // Arrange
    String accountNumber = "1234567890";

    when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NotFoundAccountException.class,
        () -> accountService.findAccountByAccountNumber(accountNumber));
    verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
  }


  @Test
  @DisplayName("계좌 번호가 이미 존재하는지 확인 - 존재하지 않음")
  void checkIfAccountNumberExists() {
    // Arrange
    String accountNumber = "123456";

    when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

    // Act & Assert
    assertFalse(accountService.checkIfAccountNumberExists(accountNumber));
    verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
  }

  @Test
  @DisplayName("계좌 번호가 이미 존재하는지 확인 - 존재함")
  void checkIfAccountNumberExists2() {
    // Arrange
    String accountNumber = "123456";
    Account expectedAccount = new Account(accountNumber, "수찬계좌", Bank.NH, BigDecimal.ZERO);

    when(accountRepository.findByAccountNumber(accountNumber))
        .thenReturn(Optional.of(expectedAccount));

    // Act & Assert
    assertTrue(accountService.checkIfAccountNumberExists(accountNumber));
    verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
  }
}
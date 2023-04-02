package transfer.banking.server.domain.account.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
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
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.exception.NotFoundAccountException;
import transfer.banking.server.domain.account.application.port.out.AccountRepositoryPort;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepositoryPort accountRepository;

  @InjectMocks
  private AccountService accountService;

  @Test
  @DisplayName("계좌번호가 존재하는지 확인한다. (이미 존재하는 경우)")
  void checkIfAccountNumberExists() {
    // given
    String accountNumber = "1234567890";
    when(accountRepository.existsByAccountNumber(accountNumber)).thenReturn(true);

    // when
    boolean result = accountService.checkIfAccountNumberExists(accountNumber);

    // then
    assertTrue(result);
    verify(accountRepository, times(1)).existsByAccountNumber(accountNumber);
  }

  @Test
  @DisplayName("계좌번호가 존재하는지 확인한다. (존재하지 않는 경우)")
  void testCheckIfAccountNumberExists_whenAccountNumberDoesNotExist() {
    // given
    String accountNumber = "0987654321";
    when(accountRepository.existsByAccountNumber(accountNumber)).thenReturn(false);

    // when
    boolean result = accountService.checkIfAccountNumberExists(accountNumber);

    // then
    assertFalse(result);
    verify(accountRepository, times(1)).existsByAccountNumber(accountNumber);
  }

  @Test
  @DisplayName("계좌를 개설한다.")
  void openAccount() {
    // given
    AccountDomain accountDomain = AccountDomain.builder()
        .accountNumber("1234567890")
        .accountName("홍길동")
        .balance(BigDecimal.ZERO)
        .build();

    // when
    when(accountRepository.save(accountDomain)).thenReturn(accountDomain);
    AccountDomain savedAccount = accountService.openAccount(accountDomain);

    // then
    assertNotNull(savedAccount);
    assertEquals(accountDomain.getAccountNumber(), savedAccount.getAccountNumber());
    assertEquals(accountDomain.getAccountName(), savedAccount.getAccountName());
    assertEquals(accountDomain.getBalance(), savedAccount.getBalance());
    verify(accountRepository, times(1)).save(accountDomain);
  }

  @Test
  @DisplayName("은행과 계좌번호로 계좌를 조회한다. (존재하는 경우)")
  void findAccountByBankAndNumber() {
    // Given
    Bank accountBank = Bank.NH;
    String accountNumber = "1234567890";
    AccountDomain expectedAccountDomain = AccountDomain.builder()
        .accountNumber(accountNumber)
        .accountName("홍길동")
        .balance(BigDecimal.ZERO)
        .bank(accountBank)
        .build();

    when(accountRepository.findAccountByBankAndNumber(accountBank, accountNumber))
        .thenReturn(Optional.of(expectedAccountDomain));

    // When
    AccountDomain actualAccount = accountService.findAccountByBankAndNumber(accountBank, accountNumber);

    // Then
    assertNotNull(actualAccount);
    assertEquals(expectedAccountDomain, actualAccount);
    verify(accountRepository, times(1)).findAccountByBankAndNumber(accountBank, accountNumber);
  }

  @Test
  @DisplayName("은행과 계좌번호로 계좌를 조회한다. (존재하지 않는 경우)")
  void testFindAccountByBankAndNumber_whenAccountDoesNotExist() {
    // Given
    Bank accountBank = Bank.NH;
    String accountNumber = "1234567890";
    when(accountRepository.findAccountByBankAndNumber(accountBank, accountNumber))
        .thenReturn(Optional.empty());

    // when & then
    assertThrows(NotFoundAccountException.class,
        () -> accountService.findAccountByBankAndNumber(accountBank, accountNumber));
    verify(accountRepository, times(1)).findAccountByBankAndNumber(accountBank, accountNumber);
  }

  @Test
  @DisplayName("계좌 이체 로직을 도메인 계층으로 넘겨주는 코드")
  void transfer() {
    // given
    AccountDomain myAccountDomain = mock(AccountDomain.class);
    MemberAccountDomain friendAccountDomain = mock(MemberAccountDomain.class);
    BigDecimal transferAmount = BigDecimal.valueOf(1000);

    // when
    accountService.transfer(myAccountDomain, friendAccountDomain, transferAmount);

    // then
    verify(myAccountDomain, times(1)).withdraw(transferAmount);
    verify(friendAccountDomain, times(1)).deposit(transferAmount);
  }

  @Test
  @DisplayName("계좌 변경 사항을 database 에 반영한다.")
  void flushAndSave() {
    // given
    AccountDomain accountDomain = mock(AccountDomain.class);

    // when
    accountService.flushAndSave(accountDomain);

    // then
    verify(accountRepository, times(1)).save(accountDomain);
  }

  @Test
  @DisplayName("은행과 계좌 번호를 통해 계좌를 조회한다.")
  void findAccountByBankAndNumberWithLock() {
    // given
    Bank bank = Bank.NH;
    String accountNumber = "1234";
    AccountDomain account = mock(AccountDomain.class);
    when(accountRepository.findAccountByBankAndNumberWithLock(bank, accountNumber))
        .thenReturn(Optional.of(account));

    // when
    AccountDomain result = accountService.findAccountByBankAndNumberWithLock(bank, accountNumber);

    // then
    assertEquals(account, result);
  }

  @Test
  @DisplayName("은행과 계좌 번호를 통해 계좌를 조회한다. (계좌가 존재하지 않는 경우)")
  void findAccountByBankAndNumberWithLock_ThrowException() {
    // Arrange
    Bank bank = Bank.NH;
    String accountNumber = "1234";
    when(accountRepository.findAccountByBankAndNumberWithLock(bank, accountNumber))
        .thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NotFoundAccountException.class,
        () -> accountService.findAccountByBankAndNumberWithLock(bank, accountNumber));
  }

}
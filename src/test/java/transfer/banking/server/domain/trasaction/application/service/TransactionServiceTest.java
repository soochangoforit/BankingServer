package transfer.banking.server.domain.trasaction.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.trasaction.application.port.out.TransactionRepositoryPort;
import transfer.banking.server.domain.trasaction.domain.TransactionDomain;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock
  private TransactionRepositoryPort transactionRepository;

  @InjectMocks
  private TransactionService transactionService;

  @Test
  @DisplayName("거래내역을 저장한다. (성공)")
  void saveTransactionRecord() {
    // given
    AccountDomain myAccount = AccountDomain.builder()
        .id(1L)
        .balance(new BigDecimal("9000"))
        .build();

    AccountDomain friendAccount = AccountDomain.builder()
        .id(2L)
        .balance(new BigDecimal("51000"))
        .build();

    BigDecimal transferAmount = new BigDecimal("1000.00");

    BigDecimal expectedSenderLeftBalance = new BigDecimal("9000");
    BigDecimal expectedReceiverLeftBalance = new BigDecimal("51000");

    // when
    transactionService.saveTransactionRecord(myAccount, friendAccount, transferAmount);

    // Then
    verify(transactionRepository, times(1)).save(myAccount, friendAccount, transferAmount, expectedSenderLeftBalance,
        expectedReceiverLeftBalance);

    assertThat(myAccount.getBalance()).isEqualTo(expectedSenderLeftBalance);
    assertThat(friendAccount.getBalance()).isEqualTo(expectedReceiverLeftBalance);
  }

  @Test
  @DisplayName("거래내역을 조회한다. (성공)")
  void getAllTransactionHistory() {
    // given - parameter
    AccountDomain accountDomain = AccountDomain.builder()
        .id(1L)
        .accountNumber("1234567890")
        .build();

    // given - return
    List<TransactionDomain> transactionDomains = new ArrayList<>();
    TransactionDomain transaction1 = TransactionDomain.builder()
        .id(1L)
        .build();
    TransactionDomain transaction2 = TransactionDomain.builder()
        .id(2L)
        .build();
    transactionDomains.add(transaction1);
    transactionDomains.add(transaction2);

    // when
    when(transactionRepository.findAllByAccount(accountDomain)).thenReturn(transactionDomains);

    // when - real call
    List<TransactionDomain> result = transactionService.getAllTransactionHistory(accountDomain);

    // then
    verify(transactionRepository, times(1)).findAllByAccount(accountDomain);
    assertThat(result.size()).isEqualTo(2);
    assertThat(result).contains(transaction1, transaction2);
  }
}
package transfer.banking.server.domain.account.application.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;


@SpringBootTest
class TransferServiceTest {

  @Autowired
  private TransferService transferService;

  // 내부 필드 1
  @Autowired
  private AccountService accountService;



  private final int MULTIPLE_TIME = 2;


  @BeforeEach
  void setUp() {
    // from Account 1 생성
    AccountDomain fromAccount_A = AccountDomain.builder()
        .bank(Bank.NH)
        .accountName("이수찬1")
        .accountNumber("111111111")
        .balance(new BigDecimal("100000.00"))
        .build();

    // from Account 2 생성
    AccountDomain fromAccount_B = AccountDomain.builder()
        .bank(Bank.NH)
        .accountName("이수찬1")
        .accountNumber("222222222")
        .balance(new BigDecimal("100000.00"))
        .build();

    // to Account 생성
    AccountDomain toAccount_C = AccountDomain.builder()
        .bank(Bank.NH)
        .accountName("홍길동")
        .accountNumber("999999999")
        .balance(new BigDecimal("100000.00"))
        .build();

    // 영속화
    accountService.openAccount(fromAccount_A);
    accountService.openAccount(fromAccount_B);
    accountService.openAccount(toAccount_C);
  }

  @Test
  @DisplayName("A 계좌에서 C 계좌로 100원을 전송합니다. (동시에 여러번 이체하는 경우)")
  void transfer() throws InterruptedException {

    // from Account A
    Bank fromAccountBank_A = Bank.NH;
    String fromAccountNumber_A = "111111111";

    // to Account C
    Bank toAccountBank_C = Bank.NH;
    String toAccountNumber_C = "999999999";
    MemberAccountDomain toAccountDomain_C = MemberAccountDomain.builder()
        .account(AccountDomain.builder().bank(toAccountBank_C).accountNumber(toAccountNumber_C).build())
        .build();

    // 이체할 금액
    BigDecimal transferAmount = new BigDecimal("100.00");

    // when
    int threadCount = 10;
    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(threadCount);


    for (int i = 0; i < threadCount; i++) {
      executorService.submit(() -> {
        try {
          // A 계좌에서 C 계좌로 100원을 전송합니다.
          transferService.transfer(fromAccountBank_A, fromAccountNumber_A,
              toAccountDomain_C, transferAmount);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();


    // 계좌 이체 전, 최초 잔액
    BigDecimal fromAccountInitialBalance_A = new BigDecimal("100000.00");
    BigDecimal toAccountInitialBalance_C = new BigDecimal("100000.00");

    // 계좌 이체 후, 남은 금액
    BigDecimal fromAccountFinalBalance_A = accountService.findAccountByBankAndNumber(fromAccountBank_A, fromAccountNumber_A).getBalance();

    BigDecimal toAccountFinalBalance_C = accountService.findAccountByBankAndNumber(
        toAccountBank_C, toAccountNumber_C).getBalance();


    // 검증
    assertEquals(fromAccountInitialBalance_A.subtract(transferAmount.multiply(new BigDecimal(threadCount))),
        fromAccountFinalBalance_A);

    assertEquals(toAccountInitialBalance_C.add(transferAmount.multiply(new BigDecimal(threadCount))),
        toAccountFinalBalance_C);

  }




  @Test
  @DisplayName("A 계좌와 B 계좌가 동시에 C 계좌로 100원을 전송합니다. (동시에 여러번 이체하는 경우)")
  void transfer_2() throws InterruptedException {

    // from Account A
    Bank fromAccountBank_A = Bank.NH;
    String fromAccountNumber_A = "111111111";

    // from Account B
    Bank fromAccountBank_B = Bank.NH;
    String fromAccountNumber_B = "222222222";

    // to Account C
    Bank toAccountBank_C = Bank.NH;
    String toAccountNumber_C = "999999999";
    MemberAccountDomain toAccountDomain_C = MemberAccountDomain.builder()
        .account(AccountDomain.builder().bank(toAccountBank_C).accountNumber(toAccountNumber_C).build())
        .build();

    // 이체할 금액
    BigDecimal transferAmount = new BigDecimal("100.00");


    int threadCount = 10;
    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(threadCount);


    for (int i = 0; i < threadCount; i++) {
      executorService.submit(() -> {
        try {
          // A 계좌가 C 계좌로 100원을 이체합니다.
          transferService.transfer(fromAccountBank_A, fromAccountNumber_A,
              toAccountDomain_C, transferAmount);

          // B 계좌가 C 계좌로 100원을 이체합니다.
          transferService.transfer(fromAccountBank_B, fromAccountNumber_B,
              toAccountDomain_C, transferAmount);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();


    // 3개의 계좌 초기 잔액
    BigDecimal fromAccountInitialBalance_A = new BigDecimal("100000.00");
    BigDecimal fromAccountInitialBalance_B = new BigDecimal("100000.00");
    BigDecimal toAccountInitialBalance_C = new BigDecimal("100000.00");

    // 거래 후 각각의 계좌 잔액
    BigDecimal fromAccountFinalBalance_A = accountService.findAccountByBankAndNumber(
        fromAccountBank_A, fromAccountNumber_A).getBalance();

    BigDecimal fromAccountFinalBalance_B = accountService.findAccountByBankAndNumber(
        fromAccountBank_B, fromAccountNumber_B).getBalance();

    BigDecimal toAccountFinalBalance_C = accountService.findAccountByBankAndNumber(
        toAccountBank_C, toAccountNumber_C).getBalance();

    // 잔액 검증
    assertEquals(fromAccountInitialBalance_A.subtract(transferAmount.multiply(new BigDecimal(threadCount))),
        fromAccountFinalBalance_A);

    assertEquals(fromAccountInitialBalance_B.subtract(transferAmount.multiply(new BigDecimal(threadCount))),
        fromAccountFinalBalance_B);

    assertEquals(toAccountInitialBalance_C.add(transferAmount.multiply(new BigDecimal(threadCount*MULTIPLE_TIME))),
        toAccountFinalBalance_C);
  }
}
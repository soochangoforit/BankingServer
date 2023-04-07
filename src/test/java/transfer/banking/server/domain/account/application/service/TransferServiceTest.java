package transfer.banking.server.domain.account.application.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.trasaction.application.service.TransactionService;


@SpringBootTest
class TransferServiceTest {

  @Autowired
  private TransferService transferService;
  @Autowired
  private AccountService accountService;
  @Autowired
  private TransactionService transactionService;


  private final int MULTIPLE_TIME = 2;
  private final BigDecimal TRANSFER_AMOUNT = new BigDecimal("100.00");
  private final int THREAD_COUNT = 10;


  @BeforeEach
  void setUp() {
    createAndPersistAccount("이수찬A", "111111111", "100000.00");
    createAndPersistAccount("이수찬B", "222222222", "100000.00");
    createAndPersistAccount("홍길동C", "999999999", "100000.00");
  }

  @AfterEach
  void tearDown() {
    transactionService.deleteAll();
    accountService.deleteAll();
  }

  private void createAndPersistAccount(String accountName, String accountNumber, String balance) {
    AccountDomain account = AccountDomain.builder()
        .bank(Bank.NH)
        .accountName(accountName)
        .accountNumber(accountNumber)
        .balance(new BigDecimal(balance))
        .build();
    accountService.openAccount(account);
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
        .account(
            AccountDomain.builder().bank(toAccountBank_C).accountNumber(toAccountNumber_C).build())
        .build();


    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

    for (int i = 0; i < THREAD_COUNT; i++) {
      executorService.submit(() -> {
        try {
          // A 계좌에서 C 계좌로 100원을 전송합니다.
          transferService.transfer(fromAccountBank_A, fromAccountNumber_A,
              toAccountDomain_C, TRANSFER_AMOUNT);
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
    BigDecimal fromAccountFinalBalance_A = accountService.findAccountByBankAndNumber(
        fromAccountBank_A, fromAccountNumber_A).getBalance();

    BigDecimal toAccountFinalBalance_C = accountService.findAccountByBankAndNumber(
        toAccountBank_C, toAccountNumber_C).getBalance();

    // 검증
    assertEquals(
        fromAccountInitialBalance_A.subtract(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
        fromAccountFinalBalance_A);

    assertEquals(
        toAccountInitialBalance_C.add(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
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
        .account(
            AccountDomain.builder().bank(toAccountBank_C).accountNumber(toAccountNumber_C).build())
        .build();


    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT * 2);

    for (int i = 0; i < THREAD_COUNT; i++) {
      // task1 : A 계좌에서 C 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(fromAccountBank_A, fromAccountNumber_A,
              toAccountDomain_C, TRANSFER_AMOUNT);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      });

      // task2 : B 계좌에서 C 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(fromAccountBank_B, fromAccountNumber_B,
              toAccountDomain_C, TRANSFER_AMOUNT);
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
    assertEquals(
        fromAccountInitialBalance_A.subtract(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
        fromAccountFinalBalance_A);

    assertEquals(
        fromAccountInitialBalance_B.subtract(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
        fromAccountFinalBalance_B);

    assertEquals(toAccountInitialBalance_C.add(
            TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT * MULTIPLE_TIME))),
        toAccountFinalBalance_C);
  }


  @Test
  @DisplayName("A는 B에게, B는 C에게 동시에 이체합니다.")
  void transfer_3() throws InterruptedException {

    // from Account A
    Bank fromAccountBank_A = Bank.NH;
    String fromAccountNumber_A = "111111111";

    // from Account B
    Bank fromAccountBank_B = Bank.NH;
    String fromAccountNumber_B = "222222222";
    MemberAccountDomain toAccountDomain_B = MemberAccountDomain.builder()
        .account(AccountDomain.builder().bank(fromAccountBank_B).accountNumber(fromAccountNumber_B)
            .build())
        .build();

    // to Account C
    Bank toAccountBank_C = Bank.NH;
    String toAccountNumber_C = "999999999";
    MemberAccountDomain toAccountDomain_C = MemberAccountDomain.builder()
        .account(
            AccountDomain.builder().bank(toAccountBank_C).accountNumber(toAccountNumber_C).build())
        .build();


    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT * 2);

    for (int i = 0; i < THREAD_COUNT; i++) {
      // task1. A 계좌에서 B 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(fromAccountBank_A, fromAccountNumber_A,
              toAccountDomain_B, TRANSFER_AMOUNT);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      });

      // task2. B 계좌에서 C 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(fromAccountBank_B, fromAccountNumber_B,
              toAccountDomain_C, TRANSFER_AMOUNT);
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
    // A 계좌는 최종적으로 100 * 10 (1000원 차감)
    assertEquals(
        fromAccountInitialBalance_A.subtract(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
        fromAccountFinalBalance_A);

    // B 계좌는 C 계좌로 1000 원을 이체하지만, A로부터 돈을 또 받기 때문에 (초기 금액 그대로 유지)
    assertEquals(fromAccountInitialBalance_B, fromAccountFinalBalance_B);

    // C 계좌는 B 계좌로부터 1000원을 입금 받아 최종적으로 1000원 증가
    assertEquals(
        toAccountInitialBalance_C.add(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
        toAccountFinalBalance_C);

  }

  @Test
  @DisplayName("A는 B에게, C는 A에게 동시에 이체한다.")
  void transfer_4() throws InterruptedException {

    // from Account A
    Bank fromAccountBank_A = Bank.NH;
    String fromAccountNumber_A = "111111111";

    // to Account A
    MemberAccountDomain toAccountDomain_A = MemberAccountDomain.builder()
        .account(AccountDomain.builder().bank(fromAccountBank_A).accountNumber(fromAccountNumber_A)
            .build())
        .build();

    // from Account B
    Bank fromAccountBank_B = Bank.NH;
    String fromAccountNumber_B = "222222222";

    // to Account B
    MemberAccountDomain toAccountDomain_B = MemberAccountDomain.builder()
        .account(AccountDomain.builder().bank(fromAccountBank_B).accountNumber(fromAccountNumber_B)
            .build())
        .build();

    // to Account C
    Bank toAccountBank_C = Bank.NH;
    String toAccountNumber_C = "999999999";

    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT * 2);

    for (int i = 0; i < THREAD_COUNT; i++) {
      // task1. A 계좌에서 B 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(fromAccountBank_A, fromAccountNumber_A,
              toAccountDomain_B, TRANSFER_AMOUNT);
        } catch (Exception e){
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      });

      // task2. C 계좌에서 A 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(toAccountBank_C, toAccountNumber_C,
              toAccountDomain_A, TRANSFER_AMOUNT);
        } catch (Exception e){
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
    // A 계좌는 B 계좌에 이체함으로써 최종적으로 100 * 10 (1000원 차감) 하지만, C로부터 돈을 받기 때문에 원상태 그대로 유지
    assertEquals(fromAccountInitialBalance_A, fromAccountFinalBalance_A);

    // B 계좌는 A 계좌로부터 1000원 돈만 받는다.
    assertEquals(
        fromAccountInitialBalance_B.add(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
        fromAccountFinalBalance_B);

    // C 계좌는 A 계좌로 1000원 이체를 하기에 최종적으로 1000원 감소
    assertEquals(
        toAccountInitialBalance_C.subtract(TRANSFER_AMOUNT.multiply(new BigDecimal(THREAD_COUNT))),
        toAccountFinalBalance_C);
  }


  @Test
  @DisplayName("A와 B가 서로서로 동시에 이체하고자 한다.")
  void transfer_each_other() throws InterruptedException {

    // from Account A
    Bank fromAccountBank_A = Bank.NH;
    String fromAccountNumber_A = "111111111";

    // to Account A
    MemberAccountDomain toAccountDomain_A = MemberAccountDomain.builder()
        .account(AccountDomain.builder().bank(fromAccountBank_A).accountNumber(fromAccountNumber_A)
            .build())
        .build();

    // from Account B
    Bank fromAccountBank_B = Bank.NH;
    String fromAccountNumber_B = "222222222";

    // to Account B
    MemberAccountDomain toAccountDomain_B = MemberAccountDomain.builder()
        .account(AccountDomain.builder().bank(fromAccountBank_B).accountNumber(fromAccountNumber_B)
            .build())
        .build();


    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT * 2);

    for (int i = 0; i < THREAD_COUNT; i++) {
      // Task 1: B 계좌에서 A 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(fromAccountBank_B, fromAccountNumber_B,
              toAccountDomain_A, TRANSFER_AMOUNT);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      });

      // Task 2: A 계좌에서 B 계좌로 100원을 전송합니다.
      executorService.submit(() -> {
        try {
          transferService.transfer(fromAccountBank_A, fromAccountNumber_A,
              toAccountDomain_B, TRANSFER_AMOUNT);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();

    // 2개의 계좌 초기 잔액
    BigDecimal fromAccountInitialBalance_A = new BigDecimal("100000.00");
    BigDecimal fromAccountInitialBalance_B = new BigDecimal("100000.00");

    // 거래 후 각각의 계좌 잔액
    BigDecimal fromAccountFinalBalance_A = accountService.findAccountByBankAndNumber(
        fromAccountBank_A, fromAccountNumber_A).getBalance();

    BigDecimal fromAccountFinalBalance_B = accountService.findAccountByBankAndNumber(
        fromAccountBank_B, fromAccountNumber_B).getBalance();

    // 잔액 검증
    // A 계좌는 B 계좌에 100 원 이체하지만, B 계좌로부터 100원을 받기 때문에 원상태 그대로 유지
    assertEquals(fromAccountInitialBalance_A, fromAccountFinalBalance_A);

    // B 계좌는 A 계좌로부터 100원을 받고, A 계좌로 100원을 이체하기에 원상태 그대로 유지
    assertEquals(fromAccountInitialBalance_B, fromAccountFinalBalance_B);

  }


}
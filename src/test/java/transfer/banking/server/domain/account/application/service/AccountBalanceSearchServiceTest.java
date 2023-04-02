package transfer.banking.server.domain.account.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountSearchDtoCommand;
import transfer.banking.server.domain.account.adapter.in.web.dto.response.AccountSearchDtoResCommand;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

@ExtendWith(MockitoExtension.class)
class AccountBalanceSearchServiceTest {

  @Mock
  private AccountService accountService;

  @Mock
  private MemberAccountService memberAccountService;

  @InjectMocks
  private AccountBalanceSearchService accountBalanceSearchService;

  @Test
  @DisplayName("계좌 잔액을 조회한다. (정상적인 경우)")
  void searchAccountBalance() {
    // command 생성
    Long memberId = 1L;
    Bank bank = Bank.NH;
    String accountNumber = "123456789";
    AccountSearchDtoCommand inputCommand = AccountSearchDtoCommand.builder()
        .accountNumber(accountNumber)
        .accountBank(bank)
        .memberId(memberId)
        .build();

    // 찾고자 하는 계좌 도메인 생성
    Long accountId = 1L;
    String accountName = "accountName";
    BigDecimal balance = BigDecimal.valueOf(5000);
    AccountDomain findedaccountDomain = new AccountDomain(accountId, accountNumber, accountName, bank, balance);

    // 내부 동작 설정
    when(accountService.findAccountByBankAndNumber(bank, accountNumber)).thenReturn(findedaccountDomain);
    doNothing().when(memberAccountService).checkIfMemberOwnsAccount(memberId, accountId);

    // 실제 동작
    AccountSearchDtoResCommand result = accountBalanceSearchService.searchAccountBalance(inputCommand);

    // 결과 검증
    assertEquals(findedaccountDomain.getId(), result.getAccountId());
    assertEquals(findedaccountDomain.getAccountNumber(), result.getAccountNumber());
    assertEquals(findedaccountDomain.getBank().toString(), result.getAccountBank());
    assertEquals(findedaccountDomain.getBalance(), result.getBalance());

  }
}
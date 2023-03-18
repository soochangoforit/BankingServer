package transfer.banking.server.domain.account.application.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountNumberGenerator {

  private final AccountService accountService;

  public String generateAccountNumber() {
    Random rnd = new Random();
    String accountNumber = String.valueOf(rnd.nextInt(1000000000));
    boolean hasNumber = accountService.checkIfAccountNumberExists(accountNumber);

    if (hasNumber) {
      log.info("계좌번호가 중복되었습니다. 다시 생성을 시도합니다.");
      return generateAccountNumber();
    }
    return accountNumber;
  }

}

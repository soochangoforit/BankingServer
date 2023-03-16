package transfer.banking.server.domain.account.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import transfer.banking.server.domain.account.entity.Account;
import transfer.banking.server.domain.account.entity.Bank;
import transfer.banking.server.domain.member.entity.Member;

/**
 * 계좌 개설 요청 DTO
 */
@Getter
@NoArgsConstructor
public class AccountOpenDto {

  /**
   * 계좌를 개설하고자 하는 사용자의 ID
   */
  private Long memberId;

  /**
   * 은행
   */
  private Bank bank;

  /**
   * 계좌 이름
   */
  private String accountName;

  public AccountOpenDto(Long id, Bank bank, String accountName) {
    this.memberId = id;
    this.bank = bank;
    this.accountName = accountName;
  }

  /**
   * 계좌 개설 요청 DTO 를 Entity 로 변환한다.
   *
   * @param accountNumber 계좌 번호
   * @return 계좌 Entity
   */
  public Account toEntity(String accountNumber) {
    return Account.builder()
        .accountNumber(accountNumber)
        .accountName(accountName)
        .bank(bank)
        .balance(BigDecimal.ZERO)
        .build();
  }
}

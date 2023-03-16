package transfer.banking.server.domain.account.entity;

import lombok.Getter;

@Getter
public enum Bank {

  KAKAO("카카오"), NH("농협"), HANA("하나");

  private final String bankName;

  Bank(String bankName) {
    this.bankName = bankName;
  }

}

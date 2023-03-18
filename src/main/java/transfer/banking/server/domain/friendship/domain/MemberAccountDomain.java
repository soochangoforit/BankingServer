package transfer.banking.server.domain.friendship.domain;

import lombok.Builder;
import lombok.Getter;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.member.domain.MemberDomain;

@Getter
public class MemberAccountDomain {

  private final Long id;
  private final MemberDomain member;
  private final AccountDomain account;

  @Builder
  public MemberAccountDomain(Long id, MemberDomain member, AccountDomain account) {
    this.id = id;
    this.member = member;
    this.account = account;
  }

}

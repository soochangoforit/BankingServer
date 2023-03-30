package transfer.banking.server.domain.memberaccount.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import transfer.banking.server.domain.account.application.mapper.AccountMapper;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.domain.MemberAccountDomain;
import transfer.banking.server.domain.member.application.mapper.MemberMapper;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.entity.MemberAccount;

@Component
@RequiredArgsConstructor
public class MemberAccountMapper {

  private final MemberMapper memberMapper;
  private final AccountMapper accountMapper;

  public MemberAccount toJpaEntity(MemberDomain memberDomain, AccountDomain accountDomain) {
    return MemberAccount.builder()
        .member(memberMapper.toJpaEntity(memberDomain))
        .account(accountMapper.toJpaEntity(accountDomain))
        .build();
  }

  public MemberAccountDomain toDomain(MemberAccount memberAccountEntity) {
    return MemberAccountDomain.builder()
        .id(memberAccountEntity.getId())
        .member(memberMapper.toDomain(memberAccountEntity.getMember()))
        .account(accountMapper.toDomain(memberAccountEntity.getAccount()))
        .build();
  }
}

package transfer.banking.server.domain.account.application.service;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transfer.banking.server.domain.account.adapter.in.web.dto.reqest.AccountTransferDtoCommand;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Bank;
import transfer.banking.server.domain.account.application.exception.CanTransferWithOnlyFriend;
import transfer.banking.server.domain.account.application.port.in.AccountTransferUseCase;
import transfer.banking.server.domain.account.domain.AccountDomain;
import transfer.banking.server.domain.friendship.application.service.FriendShipService;
import transfer.banking.server.domain.member.application.service.MemberService;
import transfer.banking.server.domain.member.domain.MemberDomain;
import transfer.banking.server.domain.memberaccount.service.MemberAccountService;

@SpringBootTest
class AccountTransferServiceTest {

  @Autowired
  private AccountTransferUseCase accountTransferService;

  @Autowired
  private MemberAccountService memberAccountService;

  @Autowired
  private FriendShipService friendShipService;

  @Autowired
  private MemberService memberService;
  @Autowired
  private AccountService accountService;

  private MemberDomain savedMyMemberDomain;
  private MemberDomain savedFriendDomain;

  @BeforeEach
  void setUp() {
    // given
    // 나 회원가입
    MemberDomain myMemberDomain = MemberDomain.builder()
        .name("이수찬")
        .email("email")
        .username("username")
        .password("password")
        .phoneNumber("010-1234-1234")
        .build();

    savedMyMemberDomain = memberService.signUp(myMemberDomain);



    // 내 계좌 등록
    AccountDomain myAccountDomain = AccountDomain.builder()
        .accountName("이수찬")
        .accountNumber("111111111")
        .bank(Bank.NH)
        .balance(new BigDecimal("100000.00"))
        .build();

    AccountDomain savedMyAccountDomain = accountService.openAccount(myAccountDomain);

    memberAccountService.saveMemberAccount(savedMyMemberDomain,savedMyAccountDomain);

    // 친구 회원가입
    MemberDomain friendMemberDomain = MemberDomain.builder()
        .name("홍길동")
        .email("email.hong")
        .username("username.hong")
        .password("password.hong")
        .phoneNumber("010-2222-2222")
        .build();

    savedFriendDomain = memberService.signUp(friendMemberDomain);

    // 친구 계좌 등록
    AccountDomain friendAccountDomain = AccountDomain.builder()
        .accountName("홍길동")
        .accountNumber("222222222")
        .bank(Bank.NH)
        .balance(new BigDecimal("100000.00"))
        .build();

    AccountDomain savedFriendAccountDomain = accountService.openAccount(friendAccountDomain);

    memberAccountService.saveMemberAccount(savedFriendDomain, savedFriendAccountDomain);

  }

  @Test
  void 친구가_등록된_경우_정상적인_계좌이체() {

    // 친구 등록
    friendShipService.saveFriendShip(savedMyMemberDomain.getId(), savedFriendDomain.getId());


    // when
    AccountTransferDtoCommand command = AccountTransferDtoCommand.builder()
        .memberId(1L)
        .myBank(Bank.NH)
        .fromAccountNumber("111111111")
        .friendBank(Bank.NH)
        .toAccountNumber("222222222")
        .transferAmount(new BigDecimal("10000.00"))
        .build();

    accountTransferService.transfer(command);

    // then
    AccountDomain myAccount = accountService.findAccountByBankAndNumber(Bank.NH, "111111111");
    AccountDomain friendAccount = accountService.findAccountByBankAndNumber(Bank.NH, "222222222");

    assertThat(myAccount.getBalance()).isEqualTo(new BigDecimal("90000.00"));
    assertThat(friendAccount.getBalance()).isEqualTo(new BigDecimal("110000.00"));

  }

  @Test
  void 친구_관계가_아닌_경우_계좌이체_불가(){
    // when
    AccountTransferDtoCommand command = AccountTransferDtoCommand.builder()
        .memberId(1L)
        .myBank(Bank.NH)
        .fromAccountNumber("111111111")
        .friendBank(Bank.NH)
        .toAccountNumber("222222222")
        .transferAmount(new BigDecimal("10000.00"))
        .build();

    // then
    assertThatThrownBy(() -> accountTransferService.transfer(command))
        .isInstanceOf(CanTransferWithOnlyFriend.class);

  }
}
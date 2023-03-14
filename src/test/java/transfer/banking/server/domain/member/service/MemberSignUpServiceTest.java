package transfer.banking.server.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.member.dto.request.SignUpDto;
import transfer.banking.server.domain.member.dto.response.MemberInfoDto;
import transfer.banking.server.domain.member.entity.Member;
import transfer.banking.server.domain.member.exception.DuplicateMemberException;
import transfer.banking.server.global.exception.ErrorCode;


@ExtendWith(MockitoExtension.class)
class MemberSignUpServiceTest {

  @Mock
  private MemberService memberService;

  @Mock
  private MemberValidatorService memberValidatorService;

  @InjectMocks
  private MemberSignUpService memberSignUpService;

  @Test
  @DisplayName("회원가입 시 validation 통과하면 회원가입이 성공한다.")
  void signUp_Success() {
    // given
    Member member = new Member("username", "password", "email@example.com", "이수찬", "01012345678");
    when(memberService.signUp(any(Member.class))).thenReturn(member);

    MemberInfoDto expected = new MemberInfoDto(member);

    // when
    SignUpDto signUpDto = new SignUpDto("이수찬", "username", "password","email@example.com","01012345678");
    MemberInfoDto actual = memberSignUpService.signUp(signUpDto);

    // then
    assertEquals(expected, actual);
    verify(memberValidatorService).validate(signUpDto);
    verify(memberService).signUp(any(Member.class));
  }

  @Test
  @DisplayName("회원가입 시 validation 통과하지 못 하면 예외가 발생한다.")
  void signUp_Failed() {
    // given
    SignUpDto signUpDto = new SignUpDto("이수찬", "username", "password","email@example.com","01012345678");
    doThrow(new DuplicateMemberException(ErrorCode.EMAIL_DUPLICATION)).when(memberValidatorService).validate(signUpDto);

    // when & then
    assertThrows(DuplicateMemberException.class, () -> memberSignUpService.signUp(signUpDto));
    verify(memberValidatorService).validate(signUpDto);
    verify(memberService, never()).signUp(any(Member.class));
  }

}
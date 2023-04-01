package transfer.banking.server.domain.member.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static transfer.banking.server.global.exception.ErrorCode.EMAIL_DUPLICATION;
import static transfer.banking.server.global.exception.ErrorCode.PHONE_DUPLICATION;
import static transfer.banking.server.global.exception.ErrorCode.USERNAME_DUPLICATION;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.member.application.exception.DuplicateMemberException;
import transfer.banking.server.domain.member.domain.MemberDomain;

@ExtendWith(MockitoExtension.class)
class MemberValidatorServiceTest {

  @Mock
  private MemberService memberService;

  @InjectMocks
  private MemberValidatorService memberValidatorService;

  @Test
  @DisplayName("중복된 필드가 있는지 검사한다. (성공)")
  void validateDuplicate() {
    MemberDomain memberDomain = MemberDomain.builder()
        .email("email")
        .phoneNumber("phoneNumber")
        .username("username")
        .password("password")
        .name("name")
        .build();

    // 이름이 중복되는 경우
    when(memberService.existsByUsername(memberDomain.getUsername())).thenReturn(true);
    DuplicateMemberException ex = assertThrows(DuplicateMemberException.class,
        () -> memberValidatorService.validateDuplicate(memberDomain));
    assertEquals(USERNAME_DUPLICATION.getMessage(), ex.getMessage());

    // 이메일이 중복되는 경우
    when(memberService.existsByUsername(memberDomain.getUsername())).thenReturn(false);
    when(memberService.existsByEmail(memberDomain.getEmail())).thenReturn(true);
    ex = assertThrows(DuplicateMemberException.class,
        () -> memberValidatorService.validateDuplicate(memberDomain));
    assertEquals(EMAIL_DUPLICATION.getMessage(), ex.getMessage());

    // 전화번호가 중복되는 경우
    when(memberService.existsByEmail(memberDomain.getEmail())).thenReturn(false);
    when(memberService.existsByPhoneNumber(memberDomain.getPhoneNumber())).thenReturn(true);
    ex = assertThrows(DuplicateMemberException.class,
        () -> memberValidatorService.validateDuplicate(memberDomain));
    assertEquals(PHONE_DUPLICATION.getMessage(), ex.getMessage());

    // 중복되는 필드가 없는 경우
    when(memberService.existsByPhoneNumber(memberDomain.getPhoneNumber())).thenReturn(false);
    memberValidatorService.validateDuplicate(memberDomain);
  }
}
package transfer.banking.server.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDto;
import transfer.banking.server.domain.member.application.service.MemberValidatorService;
import transfer.banking.server.domain.member.application.exception.DuplicateMemberException;
import transfer.banking.server.domain.member.adapter.out.persistence.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberValidatorServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberValidatorService memberValidatorService;

  @Test
  @DisplayName("회원가입 시 아이디가 중복되면 예외가 발생한다.")
  void validate_ShouldThrowDuplicateMemberException_WhenUsernameExists() {
    // given
    String username = "username";
    SignUpDto signUpDto = new SignUpDto("이수찬", username, "password", "email@example.com", "01012345678");

    // when
    when(memberRepository.existsByUsername(username)).thenReturn(true);

    // then
    assertThrows(DuplicateMemberException.class, () -> memberValidatorService.validate(signUpDto));
  }

  @Test
  @DisplayName("회원가입 시 이메일이 중복되면 예외가 발생한다.")
  void validate_ShouldThrowDuplicateMemberException_WhenEmailExists() {
    // given
    String email = "email@example.com";
    SignUpDto signUpDto = new SignUpDto("이수찬", "username", "password", email, "01012345678");

    // when
    when(memberRepository.existsByEmail(email)).thenReturn(true);

    // then
    assertThrows(DuplicateMemberException.class, () -> memberValidatorService.validate(signUpDto));
  }

  @Test
  @DisplayName("회원가입 시 전화번호가 중복되면 예외가 발생한다.")
  void validate_ShouldThrowDuplicateMemberException_WhenPhoneNumberExists() {
    // given
    String phoneNumber = "01012345678";
    SignUpDto signUpDto = new SignUpDto("이수찬", "username", "password", "email@example.com", phoneNumber);

    // when
    when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);

    // then
    assertThrows(DuplicateMemberException.class, () -> memberValidatorService.validate(signUpDto));
  }

  @Test
  @DisplayName("회원가입 시 중복된 필드가 없으면 예외가 발생하지 않는다.")
  void validate_ShouldNotThrowException_WhenNoDuplicateFields() {
    // given
    SignUpDto signUpDto = new SignUpDto("이수찬", "username", "password", "email@example.com", "01012345678");

    // when
    when(memberRepository.existsByUsername(signUpDto.getUsername())).thenReturn(false);
    when(memberRepository.existsByEmail(signUpDto.getEmail())).thenReturn(false);
    when(memberRepository.existsByPhoneNumber(signUpDto.getPhoneNumber())).thenReturn(false);

    // then
    assertDoesNotThrow(() -> memberValidatorService.validate(signUpDto));
  }
}
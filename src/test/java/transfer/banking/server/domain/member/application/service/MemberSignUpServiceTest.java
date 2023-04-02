package transfer.banking.server.domain.member.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDtoCommand;
import transfer.banking.server.domain.member.adapter.in.web.dto.response.MemberInfoDtoCommand;
import transfer.banking.server.domain.member.domain.MemberDomain;

@ExtendWith(MockitoExtension.class)
class MemberSignUpServiceTest {

  @Mock
  private MemberService memberService;
  @Mock
  private MemberValidatorService memberValidatorService;
  @InjectMocks
  private MemberSignUpService memberSignUpService;

  @Test
  @DisplayName("회원가입을 한다. (성공)")
  void signUp() {
    // 입력 값 모델
    SignUpDtoCommand command = new SignUpDtoCommand("name", "username", "password",
        "email", "phoneNumber");
    MemberDomain inputMemberDomain = command.toMemberDomain();

    // 출력 값 모델
    MemberDomain outputMemberDomain = command.toMemberDomain();
    MemberInfoDtoCommand returnExpected = new MemberInfoDtoCommand(outputMemberDomain);

    when(memberService.signUp(any())).thenReturn(outputMemberDomain);

    MemberInfoDtoCommand actual = memberSignUpService.signUp(command);

    assertEquals(returnExpected.getId(), actual.getId());
    verify(memberValidatorService, times(1)).validateDuplicate(any());
    verify(memberService, times(1)).signUp(any());
  }
}
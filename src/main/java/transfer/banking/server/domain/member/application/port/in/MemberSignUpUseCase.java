package transfer.banking.server.domain.member.application.port.in;

import transfer.banking.server.domain.member.adapter.in.web.dto.request.SignUpDtoCommand;
import transfer.banking.server.domain.member.adapter.in.web.dto.response.MemberInfoDtoCommand;

/**
 * 회원가입 복합 서비스가 수행할 유스케이스 정의
 *
 * 파라미터로 Command 객체를 받아서 내부 비지니스 로직을 수행한다.
 * 응답값으로 Command 객체를 반환한다.
 */
public interface MemberSignUpUseCase {
  MemberInfoDtoCommand signUp(SignUpDtoCommand signUpDtoCommand);
}

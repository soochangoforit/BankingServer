package transfer.banking.server.domain.member.application.port.out;

import transfer.banking.server.domain.member.adapter.out.persistence.entity.Member;
import transfer.banking.server.domain.member.domain.MemberDomain;

/**
 * 멤버 저장하기를 원하는 출력 인터페이스 (포트)
 *
 * 파라미터 값으로 Entity 혹은 Primitive 타입을 받는다.
 * 응답값으로 Domain 객체를 반환한다.
 */
public interface MemberSavePort {

  MemberDomain save(Member member);

}

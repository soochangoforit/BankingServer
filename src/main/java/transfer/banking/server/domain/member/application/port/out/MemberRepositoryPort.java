package transfer.banking.server.domain.member.application.port.out;

import java.util.List;
import java.util.Optional;
import transfer.banking.server.domain.member.domain.MemberDomain;


/**
 * 순수 서비스와 외부 DB 를 연결하는 Repository 인터페이스 (어댑터 클래스)
 * 인터페이스화 함으로써 순수 서비스와 외부 DB 접근을 위한 Adapter 를 분리할 수 있다.
 * Data JPA 환경으로 변경할수도 있고, 다른 DB 환경으로 변경할 수도 있다.
 * 입력 값으로 Domain 객체를 주입 받거나, Primitive Type 을 사용한다.
 * 응답 값으로 Domain 객체를 사용 하거나, Primitive Type 을 사용한다.
 * [의문점]
 * 과연 Port interface 도 여러개를 만들어서 관리를 해야할까?
 * 다른 DB 접근법으로 교체된다고 하더라도, 결국 그 DB에서 데이터를 가져오고 가공하려면
 * 처음엔 save 가 필연적으로 필요하다.
 * 그 말은 즉, save 를 포함한 CRUD 를 모두 포함하는 Port interface 를 다른 DB 접근 기술 Adapter 에서
 * implements 해야한다는 것이다.
 * 가령, Member 가 아닌 Refresh Token 에 대해서 Redis 환경에서 저장하고 관리한다고 하면
 * 새로운 Port Interface 를 만들어서 협력해야 한다.
 * 다만, Redis Token 관리를 위해 Port Interface 를 사용함으로써 추후 Data JPA 환경으로 변경할 때
 * Port Interface 는 그대로 두고, 그 하위 구현체만 Data Jpa Adapter 로 변경하면 된다.
 * 따라서, Port Interface 는 우선적으로 1개만 만들어두고, 추후에 필요하다면 추가하면 된다.
 */
public interface MemberRepositoryPort {

  MemberDomain save(MemberDomain domain);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  Optional<MemberDomain> findById(Long memberId);

  List<MemberDomain> findByIds(List<Long> myFriendIds);

  Optional<MemberDomain> findByNameAndPhoneNumber(String friendName, String friendPhoneNumber);
}

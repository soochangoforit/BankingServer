package transfer.banking.server.domain.member.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transfer.banking.server.domain.member.application.exception.MemberNotFoundException;
import transfer.banking.server.domain.member.application.port.out.MemberRepositoryPort;
import transfer.banking.server.domain.member.domain.MemberDomain;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

  @Mock
  private MemberRepositoryPort memberRepository;

  @InjectMocks
  private MemberService memberService;

  private static final Long EXISTING_MEMBER_ID = 1L;
  private static final Long NON_EXISTING_MEMBER_ID = 2L;

  @Test
  @DisplayName("회원가입 성공")
  void signUp() {
    // Arrange
    MemberDomain inputMember = MemberDomain.builder()
        .email("email")
        .phoneNumber("phoneNumber")
        .username("username")
        .password("password")
        .name("name")
        .build();

    MemberDomain savedMember = MemberDomain.builder()
        .id(1L)
        .email("email")
        .phoneNumber("phoneNumber")
        .username("username")
        .password("password")
        .name("name")
        .build();

    when(memberRepository.save(inputMember)).thenReturn(savedMember);

    // Act
    MemberDomain result = memberService.signUp(inputMember);

    // Assert
    assertThat(result.getEmail()).isEqualTo(inputMember.getEmail());
    verify(memberRepository, times(1)).save(inputMember);
  }

  @Test
  @DisplayName("회원가입 시, 중복된 아이디가 있는지 검증 (중복된 아이디 존재)")
  void existsByUsername() {
    // Arrange
    String username = "testuser";
    when(memberRepository.existsByUsername(username)).thenReturn(true);

    // Act
    boolean result = memberService.existsByUsername(username);

    // Assert
    assertThat(result).isTrue();
    verify(memberRepository, times(1)).existsByUsername(username);
  }

  @Test
  @DisplayName("회원가입 시, 중복된 이메일이 있는지 검증 (중복된 이메일 존재)")
  void existsByEmail() {
    // Arrange
    String email = "email.com";
    when(memberRepository.existsByEmail(email)).thenReturn(true);

    // Act
    boolean result = memberService.existsByEmail(email);

    // Assert
    assertThat(result).isTrue();
    verify(memberRepository, times(1)).existsByEmail(email);
  }

  @Test
  @DisplayName("회원가입 시, 중복된 전화번호가 있는지 검증 (중복된 전화번호 존재)")
  void existsByPhoneNumber() {
    // Arrange
    String phoneNumber = "010-1234-5678";
    when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);

    // Act
    boolean result = memberService.existsByPhoneNumber(phoneNumber);

    // Assert
    assertThat(result).isTrue();
    verify(memberRepository, times(1)).existsByPhoneNumber(phoneNumber);
  }

  @Test
  @DisplayName("ID를 통해서 회원을 조회 (회원 존재)")
  void findMemberById() {
    // Arrange
    MemberDomain existingMember = MemberDomain.builder()
        .id(EXISTING_MEMBER_ID)
        .email("email")
        .phoneNumber("phoneNumber")
        .username("username")
        .password("password")
        .name("name")
        .build();

    when(memberRepository.findById(EXISTING_MEMBER_ID)).thenReturn(Optional.of(existingMember));

    // Act
    MemberDomain result = memberService.findMemberById(EXISTING_MEMBER_ID);

    // Assert
    assertNotNull(result);
    assertEquals(EXISTING_MEMBER_ID, result.getId());
    verify(memberRepository, times(1)).findById(EXISTING_MEMBER_ID);
  }

  @Test
  @DisplayName("ID를 통해서 회원을 조회 (회원 존재하지 않음)")
  void findMemberById_NotFound() {
    // Arrange
    when(memberRepository.findById(NON_EXISTING_MEMBER_ID)).thenReturn(Optional.empty());

    // Act
    Exception exception = assertThrows(MemberNotFoundException.class, () -> memberService.findMemberById(NON_EXISTING_MEMBER_ID));

    // Assert
    verify(memberRepository, times(1)).findById(NON_EXISTING_MEMBER_ID);
  }

  @Test
  @DisplayName("ID 목록을 통해서 회원 목록 조회")
  void findMemberByIds() {
    // Arrange
    List<Long> myFriendIds = Arrays.asList(1L, 2L);

    MemberDomain member1 = MemberDomain.builder()
        .id(1L)
        .email("email")
        .phoneNumber("phoneNumber")
        .username("username")
        .password("password")
        .name("name")
        .build();

    MemberDomain member2 = MemberDomain.builder()
        .id(2L)
        .email("email")
        .phoneNumber("phoneNumber")
        .username("username")
        .password("password")
        .name("name")
        .build();

    List<MemberDomain> expectedMembers = Arrays.asList(member1, member2);
    when(memberRepository.findByIds(myFriendIds)).thenReturn(expectedMembers);

    // Act
    List<MemberDomain> result = memberService.findMemberByIds(myFriendIds);

    // Assert
    assertNotNull(result);
    assertEquals(expectedMembers.size(), result.size());
    assertEquals(expectedMembers.get(0).getId(), result.get(0).getId());
    assertEquals(expectedMembers.get(1).getId(), result.get(1).getId());
    verify(memberRepository, times(1)).findByIds(myFriendIds);
  }

  @Test
  @DisplayName("이름과 전화번호를 통해서 회원을 조회 (회원 존재)")
  void findMemberByNameAndNumber() {
    // Arrange
    String FRIEND_NAME = "name";
    String FRIEND_PHONE_NUMBER = "phoneNumber";

    MemberDomain expectedMember = MemberDomain.builder()
        .id(1L)
        .email("email")
        .phoneNumber("phoneNumber")
        .username("username")
        .password("password")
        .name("name")
        .build();

    when(memberRepository.findByNameAndPhoneNumber(FRIEND_NAME, FRIEND_PHONE_NUMBER))
        .thenReturn(Optional.of(expectedMember));

    // Act
    MemberDomain result = memberService.findMemberByNameAndNumber(FRIEND_NAME, FRIEND_PHONE_NUMBER);

    // Assert
    assertNotNull(result);
    assertEquals(expectedMember.getName(), result.getName());
    assertEquals(expectedMember.getPhoneNumber(), result.getPhoneNumber());
    verify(memberRepository, times(1)).findByNameAndPhoneNumber(FRIEND_NAME, FRIEND_PHONE_NUMBER);
  }

  @Test
  @DisplayName("이름과 전화번호를 통해서 회원을 조회 (회원 존재하지 않음)")
  void findMemberByNameAndNumber_NotFound() {
    // Arrange
    String FRIEND_NAME = "name";
    String FRIEND_PHONE_NUMBER = "phoneNumber";

    when(memberRepository.findByNameAndPhoneNumber(FRIEND_NAME, FRIEND_PHONE_NUMBER))
        .thenReturn(Optional.empty());

    // Act
    Exception exception = assertThrows(MemberNotFoundException.class, () -> memberService.findMemberByNameAndNumber(FRIEND_NAME, FRIEND_PHONE_NUMBER));

    // Assert
    verify(memberRepository, times(1)).findByNameAndPhoneNumber(FRIEND_NAME, FRIEND_PHONE_NUMBER);
  }
}
package transfer.banking.server.domain.friendship.adapter.out.persistence.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import transfer.banking.server.domain.friendship.adapter.out.persistence.entity.FriendShip;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {

  @Query("select fs from FriendShip fs where (fs.memberId = :memberId and fs.friendId = :friendId) or (fs.memberId = :friendId and fs.friendId = :memberId)")
  Optional<FriendShip> findByMemberIdAndFriendId(@Param("memberId") Long memberId, @Param("friendId") Long friendId);

  @Query("select fs.friendId from FriendShip fs where fs.memberId = :memberId")
  List<Long> findFriendIdsByMemberId(@Param("memberId") Long memberId);
}

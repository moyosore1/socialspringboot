package com.moyosore.socialspring.friendrequest;

import com.moyosore.socialspring.user.AppUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

  Optional<FriendRequest> findBySenderAndReceiver(AppUser sender, AppUser receiver);
  boolean existsFriendRequestBySenderIdAndReceiverId(Long senderId, Long receiverId);
  boolean existsFriendRequestBySenderAndReceiver(AppUser sender, AppUser receiver);
  @Query("SELECT COUNT(f) FROM FriendRequest f WHERE f.sender.id = ?1 AND f.receiver.id = ?2 OR f.sender.id = ?2 AND f.receiver.id = ?1")
  Integer checkIfFriendRequestExists(Long senderId, Long receiverId);


  List<FriendRequest> findByReceiverAndActiveTrue(AppUser receiver);

}

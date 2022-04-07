package com.moyosore.socialspring.friendrequest;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestInterface extends JpaRepository<FriendRequest, Long> {

  Optional<FriendRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}

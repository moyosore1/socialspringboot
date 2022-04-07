package com.moyosore.socialspring.friends;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendListRepository extends JpaRepository<FriendList, Long> {

  Optional<FriendList> findByUserId(Long id);

}

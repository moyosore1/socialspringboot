package com.moyosore.socialspring.friends;


import com.moyosore.socialspring.user.AppUser;
import com.moyosore.socialspring.user.UserService;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendListService {
  private final FriendListRepository friendListRepository;
  private final UserService userService;

  public void saveFriendObj(FriendList friend){
    friendListRepository.save(friend);
  }

  public FriendList getFriends(Principal user){
    AppUser currentUser = userService.currentUser(user.getName());
    return friendListRepository.findByUserId(currentUser.getId()).get();
  }


}

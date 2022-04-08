package com.moyosore.socialspring.friends;


import com.moyosore.socialspring.user.AppUser;
import com.moyosore.socialspring.user.UserService;
import java.security.Principal;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendListService {

  private final FriendListRepository friendListRepository;
  private final UserService userService;

  public void saveFriendObj(FriendList userFriendList) {
    friendListRepository.save(userFriendList);
  }

  public FriendList getFriends(Principal user) {
    AppUser currentUser = userService.currentUser(user.getName());
    return friendListRepository.findByUserId(currentUser.getId()).get();
  }

  public FriendList getFriendList(AppUser user) {
    return friendListRepository.findByUserId(user.getId()).orElse(saveFriendList(user.getId()));
  }


  public FriendList getAUsersFriendList(Long userId) {
    return friendListRepository.findByUserId(userId).orElse(saveFriendList(userId));
  }


  public FriendList saveFriendList(Long userId) {
    AppUser user = userService.getUserById(userId);
    FriendList friendList = new FriendList(user);
    return friendListRepository.save(friendList);
  }


}

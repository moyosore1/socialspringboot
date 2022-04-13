package com.moyosore.socialspring.friends;


import com.moyosore.socialspring.exception.ResourceNotFoundException;
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

//  public void saveFriendObj(FriendList userFriendList) {
//    friendListRepository.save(userFriendList);
//  }

  public FriendList getFriends(Principal user) {
    AppUser currentUser = userService.currentUser(user.getName());
    Optional<FriendList> friendList = friendListRepository.findByUserId(currentUser.getId());
    System.out.println(friendList);
    return friendList.get();
  }

  public FriendList getFriendList(AppUser user) {
    return friendListRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Friend list for user with id: "+ user.getId()+" not found."));
  }


  public FriendList getAUsersFriendList(Long userId) {
    AppUser user = userService.getUserById(userId);
    return friendListRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Friend list for user with id: "+ userId+" not found."));
  }


  public FriendList saveFriendList(Long userId) {
    AppUser user = userService.getUserById(userId);
    FriendList friendList = new FriendList(user);
    return friendListRepository.save(friendList);
  }


}

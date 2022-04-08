package com.moyosore.socialspring.friendrequest;


import com.moyosore.socialspring.exception.ApiRequestException;
import com.moyosore.socialspring.friends.FriendList;
import com.moyosore.socialspring.friends.FriendListService;
import com.moyosore.socialspring.user.AppUser;
import com.moyosore.socialspring.user.UserService;
import java.security.Principal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FriendRequestService {
  private final FriendRequestRepository friendRequestRepository;
  private final FriendListService friendListService;
  private final UserService userService;


  public FriendRequest acceptRequest(Long senderId, Principal currentUser){
    AppUser sender = userService.getUserById(senderId);
    // Get receiver's friend list and add sender
    AppUser receiver = userService.currentUser(currentUser.getName());
    FriendList receiverFriendList = friendListService.getFriendList(receiver);
    receiverFriendList.addFriend(sender);

    // Get sender's friend list and add receiver
    FriendList senderFriendList = friendListService.getFriendList(sender);
    senderFriendList.addFriend(receiver);

    FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(() -> new ApiRequestException("Friend request not found."));
    friendRequest.setActive(false);
    return friendRequestRepository.save(friendRequest);
  }

  public FriendRequest sendFriendRequest(Principal currentUser, Long receiverId){
    AppUser sender = userService.currentUser(currentUser.getName());
    AppUser receiver = userService.getUserById(receiverId);
    int friendRequestCount = friendRequestRepository.checkIfFriendRequestExists(sender.getId(), receiver.getId());
    if(friendRequestCount < 0){
      FriendRequest friendRequest = new FriendRequest(sender, receiver);
      return friendRequestRepository.save(friendRequest);
    }

    throw new ApiRequestException("Sth");
  }

  public FriendRequest declineFriendRequest(Principal currentUser, Long senderId){
    AppUser receiver = userService.currentUser(currentUser.getName());
    AppUser sender = userService.getUserById(senderId);
    FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(() -> new ApiRequestException("Friend request does not exist."));
    friendRequest.setActive(false);
    return friendRequestRepository.save(friendRequest);
  }

  public List<FriendRequest> myFriendRequests(Principal currentUser){
    AppUser appUser = userService.currentUser(currentUser.getName());
    return friendRequestRepository.findByReceiverAndActiveTrue(appUser);
  }








}

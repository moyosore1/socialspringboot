package com.moyosore.socialspring.friendrequest;


import com.moyosore.socialspring.exception.ApiRequestException;
import com.moyosore.socialspring.exception.ResourceNotFoundException;
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


  public FriendRequest acceptRequest(Long friendRequestId, Principal currentUser){
    FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow(() -> new ResourceNotFoundException("Friend request resource "+ friendRequestId +" does not exist"));
    AppUser receiver = userService.currentUser(currentUser.getName());

    if(friendRequest.getActive() && friendRequest.getReceiver() == receiver){
      AppUser sender = userService.getUserById(friendRequest.getSender().getId());
      // Get receiver's friend list and add sender
      FriendList receiverFriendList = friendListService.getFriendList(receiver);
      receiverFriendList.addFriend(sender);

      // Get sender's friend list and add receiver
      FriendList senderFriendList = friendListService.getFriendList(sender);
      senderFriendList.addFriend(receiver);


      friendRequest.setActive(false);
      return friendRequestRepository.save(friendRequest);
    }
    throw new ApiRequestException("Friend request may not be active.");

  }

  public FriendRequest sendFriendRequest(Principal currentUser, Long receiverId){
    AppUser sender = userService.currentUser(currentUser.getName());
    AppUser receiver = userService.getUserById(receiverId);
    int friendRequestCount = friendRequestRepository.checkIfFriendRequestExists(sender.getId(), receiver.getId());
    System.out.println(friendRequestCount);

    if(friendRequestCount == 0){
      FriendRequest friendRequest = new FriendRequest(sender, receiver);
      friendRequest.setActive(true);
      return friendRequestRepository.save(friendRequest);
    }else {
      throw new ApiRequestException("Friend Request already exists!");
    }


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

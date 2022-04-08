package com.moyosore.socialspring.friendrequest;


import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/friend-requests")
@AllArgsConstructor
public class FriendRequestController {
  private final FriendRequestService friendRequestService;
  private final ModelMapper modelMapper;

  @PostMapping("/{receiverId}")
  public ResponseEntity<FriendRequestDTO> sendFriendRequest(@PathVariable("receiverId") Long receiverId, Principal currentUser){
    FriendRequest friendRequest = friendRequestService.sendFriendRequest(currentUser, receiverId);
    FriendRequestDTO friendRequestDTO = modelMapper.map(friendRequest, FriendRequestDTO.class);
    return new ResponseEntity<FriendRequestDTO>(friendRequestDTO, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<FriendRequestDTO>> myFriendRequests(Principal currentUser){
    List<FriendRequestDTO> friendRequestsDTO = friendRequestService.myFriendRequests(currentUser).stream().map(friendRequest -> modelMapper.map(friendRequest, FriendRequestDTO.class)).collect(
        Collectors.toList());
    return new ResponseEntity<List<FriendRequestDTO>>(friendRequestsDTO, HttpStatus.OK);
  }

  @PostMapping("/{senderId}")
  public ResponseEntity<FriendRequestDTO> declineFriendRequest(Principal currentUser, @PathVariable("senderId") Long senderId){
    FriendRequest friendRequest = friendRequestService.declineFriendRequest(currentUser, senderId);
    FriendRequestDTO friendRequestDTO = modelMapper.map(friendRequest, FriendRequestDTO.class);
    return new ResponseEntity<FriendRequestDTO>(friendRequestDTO, HttpStatus.OK);
  }





}

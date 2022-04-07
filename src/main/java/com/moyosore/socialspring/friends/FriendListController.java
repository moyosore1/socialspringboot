package com.moyosore.socialspring.friends;

import java.security.Principal;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/friendlist")
@AllArgsConstructor
public class FriendListController {
  private final FriendListService friendListService;
  private final ModelMapper modelMapper;


  @GetMapping
  public ResponseEntity<FriendListDTO> getFriendList(Principal user){
    FriendList friendList = friendListService.getFriends(user);
    FriendListDTO friendListDTO = modelMapper.map(friendList, FriendListDTO.class);
    return new ResponseEntity<FriendListDTO>(friendListDTO, HttpStatus.OK);
  }

}

package com.moyosore.socialspring.friends;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendListService {
  private final FriendListRepository friendListRepository;

  public void saveFriendObj(FriendList friend){
    friendListRepository.save(friend);
  }
}

package com.moyosore.socialspring.friendrequest;


import com.moyosore.socialspring.user.UserDTO;
import java.util.Date;
import lombok.Data;

@Data
public class FriendRequestDTO {
  private Long id;
  private UserDTO sender;
  private UserDTO receiver;
  private Boolean is_active;
  private Date dateCreated;

}

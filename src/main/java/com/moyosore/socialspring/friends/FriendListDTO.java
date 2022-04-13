package com.moyosore.socialspring.friends;

import com.moyosore.socialspring.user.UserDTO;
import java.util.List;
import lombok.Data;

@Data
public class FriendListDTO {
  private Long id;
  private UserDTO user;
  private List<UserDTO> friends;
  

}

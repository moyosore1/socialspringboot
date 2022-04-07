package com.moyosore.socialspring.post;


import com.moyosore.socialspring.user.AppUser;
import com.moyosore.socialspring.user.UserDTO;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostResponseDTO {
  private Long id;
  private String title;
  private String content;
  private UUID slug;
  private Date createdAt;
  private Date updatedAt;
  private UserDTO author;




}
